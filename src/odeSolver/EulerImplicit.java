package odeSolver;



import Exceptions.WrongCalculationException;
import Exceptions.WrongExpressionException;
import Exceptions.WrongInputException;
import MathExpr.MathExpr;
import MathToken.MathTokenSymbol;
import Parser.MathParser;


/**
 * Implementation Of The Implicit Euler Methods
 * 
 * y[k+1] = y[k] + h*f(t[k], y[k+1])
 * 
 * y[k+1] - y[k] - h*f(t[k], y[k+1]) = 0
 *
 */
public class EulerImplicit extends OdeSolver {

	
	/**
	 * Euler Implicit Constructor: General Case (With Exact Solution)
	 * 
	 * @param exact Exact Solution
	 * @param func f(t, y(t))
	 * @param t0 Initial Time
	 * @param y0 Initial Value y(t0) = y0
	 * @param step difference between each moment
	 * @param tmax max time, end of calculation
	 * @param t Independent Symbol t
	 * @param y Dependent Symbol y(t)
	 * @throws WrongInputException Null Input
	 * @throws WrongCalculationException 
	 */
	public EulerImplicit (MathExpr exact, MathExpr func, double t0, double y0, double step, double tmax, MathTokenSymbol t, MathTokenSymbol y) throws WrongInputException, WrongCalculationException {
		
		super(exact, func, t0, y0, step, tmax, t, y);
		
		this.methodName = "Euler Implicit";
		this.methodOrder = 1;
		this.methodType = "implicit";
		
	}

	
	/**
	 * Euler Implicit Constructor: General Case (Without Exact Solution)
	 * 
	 * @param func f(t, y(t))
	 * @param t0 Initial Time
	 * @param y0 Initial Value y(t0) = y0
	 * @param step difference between each moment
	 * @param tmax max time, end of calculation
	 * @param t Independent Symbol t
	 * @param y Dependent Symbol y(t)
	 * @throws WrongInputException Null Input
	 * @throws WrongCalculationException 
	 */
	public EulerImplicit (MathExpr func, double t0, double y0, double step, double tmax, MathTokenSymbol t, MathTokenSymbol y) throws WrongInputException, WrongCalculationException {
		
		super (func, t0, y0, step, tmax, t, y);
		
		this.methodName = "Euler Implicit";
		this.methodOrder = 1;
		this.methodType = "implicit";
		
	}

	
	/**
	 * Euler Implicit Constructor: String Symbols  (With Exact Solution)
	 * 
	 * @param exact Exact Solution
	 * @param func f(t, y(t))
	 * @param t0 Initial Time
	 * @param y0 Initial Value y(t0) = y0
	 * @param step difference between each moment
	 * @param tmax max time, end of calculation
	 * @param t Independent Symbol String t
	 * @param y Dependent Symbol String y(t)
	 * @throws WrongInputException Null Input
	 * @throws WrongCalculationException 
	 */
	public EulerImplicit(MathExpr exact, MathExpr func, double t0, double y0, double step, double tmax, String t, String y) throws WrongInputException, WrongCalculationException {
		
		super (exact, func, t0, y0, step, tmax, t, y);
		
		this.methodName = "Euler Implicit";
		this.methodOrder = 1;
		this.methodType = "implicit";
		
	}

	
	/**
	 * Euler Implicit Constructor: String Symbols  (Without Exact Solution)
	 * 
	 * @param func f(t, y(t))
	 * @param t0 Initial Time
	 * @param y0 Initial Value y(t0) = y0
	 * @param step difference between each moment
	 * @param tmax max time, end of calculation
	 * @param t Independent Symbol String t
	 * @param y Dependent Symbol String y(t)
	 * @throws WrongInputException Null Input
	 * @throws WrongCalculationException 
	 */
	public EulerImplicit(MathExpr func, double t0, double y0, double step, double tmax, String t, String y) throws WrongInputException, WrongCalculationException {
		
		super (func, t0, y0, step, tmax, t, y);
		
		this.methodName = "Euler Implicit";
		this.methodOrder = 1;
		this.methodType = "implicit";
		
	}

	
	/**
	 * Euler Implicit Constructor: Default t0 = 0, step = 0.1, tmax = 1 (With Exact Solution)
	 * 
	 * @param exact Exact Solution
	 * @param func f(t, y(t))
	 * @param y0 Initial Value y(t0) = y0
	 * @param t Independent Symbol t
	 * @param y Dependent Symbol y(t)
	 * @throws WrongInputException Null Input
	 * @throws WrongCalculationException 
	 */
	public EulerImplicit(MathExpr exact, MathExpr func, double y0, MathTokenSymbol t, MathTokenSymbol y) throws WrongInputException, WrongCalculationException {
		
		super(exact, func, y0, t, y);
		
		this.methodName = "Euler Implicit";
		this.methodOrder = 1;
		this.methodType = "implicit";
		
	}

	
	/**
	 * Euler Implicit Constructor: String Symbols, Default t0 = 0, step = 0.1, tmax = 1 (Without Exact Solution)
	 * 
	 * @param exact Exact Solution
	 * @param func f(t, y(t))
	 * @param y0 Initial Value y(t0) = y0
	 * @param t Independent Symbol String t
	 * @param y Dependent Symbol String y(t)
	 * @throws WrongInputException Null Input
	 * @throws WrongCalculationException 
	 */
	public EulerImplicit(MathExpr exact, MathExpr func, double y0, String t, String y) throws WrongInputException, WrongCalculationException {
		
		super(exact, func, y0, t, y);
		
		this.methodName = "Euler Implicit";
		this.methodOrder = 1;
		this.methodType = "implicit";
		
	}

	
	/**
	 * Euler Implicit Constructor: String Symbols, Default t0 = 0, step = 0.1, tmax = 1 (Without Exact Solution)
	 * 
	 * @param func f(t, y(t))
	 * @param y0 Initial Value y(t0) = y0
	 * @param t Independent Symbol String t
	 * @param y Dependent Symbol String y(t)
	 * @throws WrongInputException Null Input
	 * @throws WrongCalculationException 
	 */
	public EulerImplicit(MathExpr func, double y0, String t, String y) throws WrongInputException, WrongCalculationException {
		
		super(func, y0, t, y);
		
		this.methodName = "Euler Implicit";
		this.methodOrder = 1;
		this.methodType = "implicit";
		
	}
	
	
		
	/** 
	 * Solves The Differential Equation With Euler Implicit Method
	 * 
	 * @return The yk Array With The Approximated Solutions
	 */
	@Override
	protected double[] solveODE() {
		
		for (int i = 1; i < this.stepNumber; i++) {
		
			try {
				
				String strFun = this.y.getValue() + " - " + this.yk[i-1] + " - (" + this.step + " * (" + this.func.deSym(this.func, this.t, this.timeInterval[i-1]) + "))"; 
				
				MathParser parser = new MathParser (strFun, "infix");
				
				MathExpr exprFunc = parser.getMathExpr();
				
				this.yk[i] = MathNum.Roots.newton(exprFunc, this.y, this.yk[i-1], tol);				
			
			} catch (WrongCalculationException | WrongInputException | WrongExpressionException e) {
				
				e.printStackTrace();
			
			}			
					
		}
		
		return this.yk;
		
	}


}

