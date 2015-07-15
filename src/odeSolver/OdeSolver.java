package odeSolver;

import Exceptions.WrongInputException;
import MathExpr.MathExpr;
import MathToken.MathTokenSymbol;

/**
 * General Structure For Ode Solver:
 * 
 * Solve First Order Differential Equations:
 * 
 * y(t)' = f(y(t, y(t))
 * y(0) = y0
 * 
 * @author sapo93
 *
 */
public abstract class OdeSolver {

	
	/** Right hand equation's member */
	protected MathExpr func;
	
	/** Initial Time */	
	protected double t0;
	
	/** Initial Value */	
	protected double y0;
	
	/** Difference Between Each Time Value*/
	protected double step;

	/** Maximum Time*/
	protected double tmax;
	
	/** Number Of Time Points*/
	protected int stepNumber;
	
	/** Time Interval */
	protected double[] timeInterval;
	
	/** Approximated Y Solution Interval */
	protected double[] yk;
	
	/** Indipendent symbol: t */
	protected MathTokenSymbol t;
	
	/** Dependent symbol: y(t) */
	protected MathTokenSymbol y;
	
	
	
	/**
	 * Abstract Constructor: General Case
	 * 
	 * @param func f(t, y(t))
	 * @param t0 Initial Time
	 * @param y0 Initial Value y(t0) = y0
	 * @param step difference between each moment
	 * @param tmax max time, end of calculation
	 * @param t Independent Symbol t
	 * @param y Dependent Symbol y(t)
	 * @throws WrongInputException Null Input
	 */
	public OdeSolver (MathExpr func, double t0, double y0, double step, double tmax, MathTokenSymbol t, MathTokenSymbol y) throws WrongInputException {
		
		if (func == null) {
			
			throw new WrongInputException ("OdeSolver- Null Function");
			
		}
		
		if (t == null) {
			
			throw new WrongInputException ("OdeSolver- Null Input Symbol t");
			
		}

		if (y == null) {
	
			throw new WrongInputException ("OdeSolver- Null Input Symbol y(t)");
	
		}
		
		if (t0 <= tmax) {
			
			throw new WrongInputException ("OdeSolver- t0 (=" + t0 + ") Must Be Less Than maxTime (=" + tmax + ")");
			
		}
		
		if (step <= 0) {
			
			throw new WrongInputException ("OdeSolver- Step (=" + step + ") Must Be A Positive Number");
			
		}
		
		// Fields Initialization
		this.func = func;		
		this.t0 = t0;		
		this.y0 = y0;		
		this.step = step;		
		this.tmax = tmax;		
		this.t = t;		
		this.y = y;		
		
		
		// Time Interval Calculation
		this.stepNumber =(int) ( ( tmax - t0) / step);		
		this.timeInterval = new double[this.stepNumber];		
		this.timeInterval[0] = t0; // First Time Point
		
		for (int i = 1; i < this.stepNumber; i++) { // Computing Time Points
			
			this.timeInterval[i] = t0 + i*step;
			
		}	
		
		
		// Approximated Y Solution Interval Initialization
		this.yk = new double[this.stepNumber];		
		this.yk[0] = y0; // First y Point
		
		for (int i = 1; i < this.stepNumber; i++) { // Setting To Zero Yks
			
			this.yk[i] = 0;
			
		}
		
	}

	

	/**
	 * Abstract Constructor: String Symbols 
	 * 
	 * @param func f(t, y(t))
	 * @param t0 Initial Time
	 * @param y0 Initial Value y(t0) = y0
	 * @param step difference between each moment
	 * @param tmax max time, end of calculation
	 * @param t Independent Symbol String t
	 * @param y Dependent Symbol String y(t)
	 * @throws WrongInputException Null Input
	 */
	public OdeSolver (MathExpr func, double t0, double y0, double step, double tmax, String t, String y) throws WrongInputException {
	
		this (func, t0, y0, step, tmax, new MathTokenSymbol (t), new MathTokenSymbol (y));
		
	}
	
	
	
	/**
	 * Abstract Constructor: Default t0 = 0
	 * 
	 * @param func f(t, y(t))
	 * @param y0 Initial Value y(t0) = y0
	 * @param step difference between each moment
	 * @param tmax max time, end of calculation
	 * @param t Independent Symbol t
	 * @param y Dependent Symbol y(t)
	 * @throws WrongInputException Null Input
	 */
	public OdeSolver (MathExpr func, double y0, double step, double tmax, MathTokenSymbol t, MathTokenSymbol y) throws WrongInputException {
	
		this (func, 0, y0, step, tmax, t, y);
		
	}
	
	
	
	/**
	 * Abstract Constructor: String Symbols, Default t0 = 0
	 * 
	 * @param func f(t, y(t))
	 * @param y0 Initial Value y(t0) = y0
	 * @param step difference between each moment
	 * @param tmax max time, end of calculation
	 * @param t Independent Symbol String t
	 * @param y Dependent Symbol String y(t)
	 * @throws WrongInputException Null Input
	 */
	public OdeSolver (MathExpr func, double y0, double step, double tmax, String t, String y) throws WrongInputException {
	
		this (func, 0, y0, step, tmax, new MathTokenSymbol (t), new MathTokenSymbol (y));
		
	}
	
	
	
	/**
	 * Abstract Constructor: Default t0 = 0, step = 0.1
	 * 
	 * @param func f(t, y(t))
	 * @param y0 Initial Value y(t0) = y0
	 * @param tmax max time, end of calculation
	 * @param t Independent Symbol t
	 * @param y Dependent Symbol y(t)
	 * @throws WrongInputException Null Input
	 */
	public OdeSolver (MathExpr func, double y0, double tmax, MathTokenSymbol t, MathTokenSymbol y) throws WrongInputException {
	
		this (func, 0, y0, 0.1, tmax, t, y);
		
	}
	
	
	
	/**
	 * Abstract Constructor: String Symbols, Default t0 = 0, step = 0.1
	 * 
	 * @param func f(t, y(t))
	 * @param y0 Initial Value y(t0) = y0
	 * @param tmax max time, end of calculation
	 * @param t Independent Symbol String t
	 * @param y Dependent Symbol String y(t)
	 * @throws WrongInputException Null Input
	 */
	public OdeSolver (MathExpr func, double y0, double tmax, String t, String y) throws WrongInputException {
	
		this (func, 0, y0, 0.1, tmax, new MathTokenSymbol (t), new MathTokenSymbol (y));
		
	}
	
	
	
	/**
	 * Abstract Constructor: Default t0 = 0, step = 0.1, tmax = 1
	 * 
	 * @param func f(t, y(t))
	 * @param y0 Initial Value y(t0) = y0
	 * @param t Independent Symbol t
	 * @param y Dependent Symbol y(t)
	 * @throws WrongInputException Null Input
	 */
	public OdeSolver (MathExpr func, double y0, MathTokenSymbol t, MathTokenSymbol y) throws WrongInputException {
	
		this (func, 0, y0, 0.1, 1, t, y);
		
	}
	
	
	
	/**
	 * Abstract Constructor: String Symbols, Default t0 = 0, step = 0.1, tmax = 1
	 * 
	 * @param func f(t, y(t))
	 * @param y0 Initial Value y(t0) = y0
	 * @param t Independent Symbol String t
	 * @param y Dependent Symbol String y(t)
	 * @throws WrongInputException Null Input
	 */
	public OdeSolver (MathExpr func, double y0, String t, String y) throws WrongInputException {
	
		this (func, 0, y0, 0.1, 1, new MathTokenSymbol (t), new MathTokenSymbol (y));
		
	}
	
	

	/**
	 * @return the function
	 */
	public MathExpr getFunc() {
		
		return this.func;
	
	}



	/**
	 * @return the t0
	 */
	public double getT0() {
		
		return this.t0;
	
	}



	/**
	 * @return the y0
	 */
	public double getY0() {
		
		return this.y0;
	
	}



	/**
	 * @return the step
	 */
	public double getStep() {
		
		return this.step;
	
	}



	/**
	 * @return the tmax
	 */
	public double getTmax() {
		
		return this.tmax;
	
	}



	/**
	 * @return the stepNumber
	 */
	public int getStepNumber() {
		
		return this.stepNumber;
	}



	/**
	 * @return the timeInterval
	 */
	public double[] getTimeInterval() {
		
		return this.timeInterval;
	
	}
	
	
	/**
	 * @return the approximated solution yk
	 */
	public double[] getyk() {
		
		return this.yk;
	
	}



	/**
	 * @return the t
	 */
	public MathTokenSymbol getT() {
		
		return this.t;
	
	}



	/**
	 * @return the y
	 */
	public MathTokenSymbol getY() {
		
		return this.y;
	
	}
	
	
	
	/** 
	 * Abstract Methods For Solving The Differential Equation
	 * 
	 * @return The yk Array With The Approximated Solutions
	 */
	public abstract double[] solve ();
	
	
	
	
	
	
	
}
