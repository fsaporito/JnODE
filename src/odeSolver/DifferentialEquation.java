package odeSolver;

import Exceptions.WrongCalculationException;
import Exceptions.WrongInputException;
import MathExpr.MathExpr;
import MathToken.MathTokenSymbol;

public class DifferentialEquation {

	/** Right hand equation's member */
	private MathExpr func;
	
	/** Exact Solution (Null If None) */
	private MathExpr exprExact;
	
	/** Initial Time */	
	private double t0;
	
	/** Initial Value */	
	private double y0;
	
	/** Difference Between Each Time Value*/
	private double step;

	/** Maximum Time*/
	private double tmax;
	
	/** Number Of Time Points*/
	private int stepNumber;
	
	/** Time Interval */
	private double[] timeInterval;
	
	/** Independent symbol: t */
	private MathTokenSymbol t;
	
	/** Dependent symbol: y(t) */
	private MathTokenSymbol y;
	
	/** Approximated Y Solution Interval */
	private double[] yk;
	
	
	/** Tolerance */
	private double tol;
	
	
	/** Method Name */
	private String methodName;
	
	/** Method Type */
	private String methodType;
	
	
	/** True If Already Solved	 */
	private boolean solved;
	
	/** True If Errors Already Calculated */
	private boolean err;
	
	/** True If The Differential Equations Has An Exact Solution */
	private boolean hasExact;
	
	
	/** Errors Percentage Between Exact And Approximated Solution */
	private double[] errorsPerc;
		
	/** Errors Percentage Average */
	private double errorsPercAvg;	
	
	/** Errors Percentage Variance */
	private double errorsPercVar;
	
	/** Errors Percentage Standard Deviation */
	private double errorsPercSd;
	
	
	
	/**
	 * General Case (With Exact Solution)
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
	public DifferentialEquation (MathExpr exact, MathExpr func, double t0, double y0, double step, double tmax, MathTokenSymbol t, MathTokenSymbol y) throws WrongInputException, WrongCalculationException {
		
		if (func == null) {
			
			throw new WrongInputException ("OdeSolver- Null Function");
			
		}
		
		if (t == null) {
			
			throw new WrongInputException ("OdeSolver- Null Input Symbol t");
			
		}

		if (y == null) {
	
			throw new WrongInputException ("OdeSolver- Null Input Symbol y(t)");
	
		}
		
		if (t0 >= tmax) {
			
			throw new WrongInputException ("OdeSolver- t0 (=" + t0 + ") Must Be Less Than maxTime (=" + tmax + ")");
			
		}
		
		if (step <= 0) {
			
			throw new WrongInputException ("OdeSolver- Step (=" + step + ") Must Be A Positive Number");
			
		}
		
		
		// Fields Initialization
		this.methodName = "Not Solved Yet";
		this.methodType = "Not Solved Yet";
		this.func = func;		
		this.t0 = t0;		
		this.y0 = y0;		
		this.step = step;		
		this.tmax = tmax;	
		this.tol = 0.0001;
		this.t = t;		
		this.y = y;	
		
		// Status Fields Initialization
		this.solved = false;
		this.err = false;
		
		
		
		// Time Interval Calculation
		this.stepNumber =((int) ( ( tmax - t0) / step)) + 1;		
		this.timeInterval = new double[this.stepNumber];
		
		for (int i = 0; i < this.stepNumber; i++) { // Computing Time Points
			
			this.timeInterval[i] = t0 + i*step;
			
		}	
		
		
		// Approximated Y Solution Interval Initialization
		this.yk = new double[this.stepNumber];		
		this.yk[0] = y0; // First y Point
		
		for (int i = 1; i < this.stepNumber; i++) { // Setting To Zero Yks
			
			this.yk[i] = 0;
			
		}
		
		
		if (exact == null) {
			
			this.exprExact = null;
			
			this.hasExact = false;
			
		} else {

			this.exprExact = exact;
			
			this.hasExact = true;
			
			
		}
		
		
	}
	
	
	
	/**
	 * General Case (Without Exact Solution)
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
	public DifferentialEquation (MathExpr func, double t0, double y0, double step, double tmax, MathTokenSymbol t, MathTokenSymbol y) throws WrongInputException, WrongCalculationException {
	
		this (null, func, t0, y0, step, tmax,t, y);
		
	}
	

	
	/**
	 * String Symbols  (With Exact Solution)
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
	public DifferentialEquation (MathExpr exact, MathExpr func, double t0, double y0, double step, double tmax, String t, String y) throws WrongInputException, WrongCalculationException {
	
		this (exact, func, t0, y0, step, tmax, new MathTokenSymbol (t), new MathTokenSymbol (y));
		
	}
	
		
	/**
	 * String Symbols  (Without Exact Solution)
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
	public DifferentialEquation (MathExpr func, double t0, double y0, double step, double tmax, String t, String y) throws WrongInputException, WrongCalculationException {
	
		this (null, func, t0, y0, step, tmax, new MathTokenSymbol (t), new MathTokenSymbol (y));
		
	}
	

	/**
	 * Default t0 = 0, step = 0.1, tmax = 1 (With Exact Solution)
	 * 
	 * @param exact Exact Solution
	 * @param func f(t, y(t))
	 * @param y0 Initial Value y(t0) = y0
	 * @param t Independent Symbol t
	 * @param y Dependent Symbol y(t)
	 * @throws WrongInputException Null Input
	 * @throws WrongCalculationException 
	 */
	public DifferentialEquation (MathExpr exact, MathExpr func, double y0, MathTokenSymbol t, MathTokenSymbol y) throws WrongInputException, WrongCalculationException {
	
		this (exact, func, 0, y0, 0.1, 1, t, y);
		
	}
		
	
	/**
	 * String Symbols, Default t0 = 0, step = 0.1, tmax = 1 (Without Exact Solution)
	 * 
	 * @param exact Exact Solution
	 * @param func f(t, y(t))
	 * @param y0 Initial Value y(t0) = y0
	 * @param t Independent Symbol String t
	 * @param y Dependent Symbol String y(t)
	 * @throws WrongInputException Null Input
	 * @throws WrongCalculationException 
	 */
	public DifferentialEquation (MathExpr exact, MathExpr func, double y0, String t, String y) throws WrongInputException, WrongCalculationException {
	
		this (exact, func, 0, y0, 0.1, 1, new MathTokenSymbol (t), new MathTokenSymbol (y));
		
	}
	
	
	/**
	 * String Symbols, Default t0 = 0, step = 0.1, tmax = 1 (Without Exact Solution)
	 * 
	 * @param func f(t, y(t))
	 * @param y0 Initial Value y(t0) = y0
	 * @param t Independent Symbol String t
	 * @param y Dependent Symbol String y(t)
	 * @throws WrongInputException Null Input
	 * @throws WrongCalculationException 
	 */
	public DifferentialEquation (MathExpr func, double y0, String t, String y) throws WrongInputException, WrongCalculationException {
	
		this (null, func, 0, y0, 0.1, 1, new MathTokenSymbol (t), new MathTokenSymbol (y));
		
	}



	/**
	 * @return the exprExact
	 */
	public MathExpr getExprExact() {
		return exprExact;
	}



	/**
	 * @param exprExact the exprExact to set
	 */
	public void setExprExact(MathExpr exprExact) {
		this.exprExact = exprExact;
	}



	/**
	 * @return the methodName
	 */
	public String getMethodName() {
		return methodName;
	}



	/**
	 * @param methodName the methodName to set
	 */
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}



	/**
	 * @return the methodType
	 */
	public String getMethodType() {
		return methodType;
	}



	/**
	 * @param methodType the methodType to set
	 */
	public void setMethodType(String methodType) {
		this.methodType = methodType;
	}



	/**
	 * @return the solved
	 */
	public boolean isSolved() {
		return solved;
	}



	/**
	 * @param solved the solved to set
	 */
	public void setSolved(boolean solved) {
		this.solved = solved;
	}



	/**
	 * @return the err
	 */
	public boolean isErr() {
		return err;
	}



	/**
	 * @param err the err to set
	 */
	public void setErr(boolean err) {
		this.err = err;
	}



	/**
	 * @return the hasExact
	 */
	public boolean isHasExact() {
		return hasExact;
	}



	/**
	 * @param hasExact the hasExact to set
	 */
	public void setHasExact(boolean hasExact) {
		this.hasExact = hasExact;
	}



	/**
	 * @return the errorsPerc
	 */
	public double[] getErrorsPerc() {
		return errorsPerc;
	}



	/**
	 * @param errorsPerc the errorsPerc to set
	 */
	public void setErrorsPerc(double[] errorsPerc) {
		this.errorsPerc = errorsPerc;
	}



	/**
	 * @return the errorPercAvg
	 */
	public double getErrorsPercAvg() {
		return errorsPercAvg;
	}



	/**
	 * @param errorPercAvg the errorPercAvg to set
	 */
	public void setErrorsPercAvg(double errorPercAvg) {
		this.errorsPercAvg = errorPercAvg;
	}



	/**
	 * @return the errorPercVar
	 */
	public double getErrorsPercVar() {
		return errorsPercVar;
	}



	/**
	 * @param errorPercVar the errorPercVar to set
	 */
	public void setErrorsPercVar(double errorPercVar) {
		this.errorsPercVar = errorPercVar;
	}



	/**
	 * @return the errorPercSd
	 */
	public double getErrorsPercSd() {
		return errorsPercSd;
	}



	/**
	 * @param errorPercSd the errorPercSd to set
	 */
	public void setErrorsPercSd(double errorPercSd) {
		this.errorsPercSd = errorPercSd;
	}



	/**
	 * @return the func
	 */
	public MathExpr getFunc() {
		return func;
	}



	/**
	 * @return the t0
	 */
	public double getT0() {
		return t0;
	}



	/**
	 * @return the y0
	 */
	public double getY0() {
		return y0;
	}



	/**
	 * @return the step
	 */
	public double getStep() {
		return step;
	}



	/**
	 * @return the tmax
	 */
	public double getTmax() {
		return tmax;
	}



	/**
	 * @return the stepNumber
	 */
	public int getStepNumber() {
		return stepNumber;
	}



	/**
	 * @return the timeInterval
	 */
	public double[] getTimeInterval() {
		return timeInterval;
	}



	/**
	 * @return the t
	 */
	public MathTokenSymbol getT() {
		return t;
	}



	/**
	 * @return the y
	 */
	public MathTokenSymbol getY() {
		return y;
	}



	/**
	 * @return the yk
	 */
	public double[] getYk() {
		return yk;
	}

	
	
	/**
	 * @param yk the yk to set
	 */
	public void setYk(double[] yk) {
		this.yk = yk;
	}


	/**
	 * @return the tol
	 */
	public double getTol() {
		return tol;
	}
	
	
	/** 
	 * Compute The Errors Done By Solving Numerically The Differential Equation
	 * 
	 * @param exactSolution
	 * @throws WrongInputException 
	 * @throws WrongCalculationException 
	 */
	public double[] errors (MathExpr exactSolution) throws WrongInputException, WrongCalculationException {
		
		if (exactSolution == null) {
			
			throw new WrongInputException ("DifferentialEquation.errors()- Null ExactSolution");
			
		}
		
		
		if (!this.isSolved()) {			
			
			throw new WrongInputException ("DifferentialEquation.errors()- Differential Equation Has Not Been Solved Numerically");
			
		}
		
		
		// Exact Solution
		double[] exactk = new double[this.getStepNumber()]; // Exact Y Solution Interval
		for (int i = 0; i < this.getStepNumber(); i++) { // Computing The Exact Solution Values
									
			exactk[i] = exactSolution.evalSymbolic(this.timeInterval[i]).getOperandDouble();
									
		}
				

				
		// Errors Computation
		double[] errorsPerc = new double[this.getStepNumber()];
		for (int i = 0; i < this.getStepNumber(); i++) {
						
			errorsPerc[i] = Math.abs((exactk[i] - this.yk[i])/exactk[i]) * 100;
						
		}
		
		this.setErrorsPerc(errorsPerc);			
				
			
				
		// Errors Percentage Average
		this.setErrorsPercAvg (MathNum.Stat.avg ( this.getErrorsPerc() ) );
				
				
		
		// Errors Percentage Variance
		this.setErrorsPercVar ( MathNum.Stat.var ( this.getErrorsPerc(), this.getErrorsPercAvg() ) );
				
						
		// Errors Percentage Standard Deviation
		this.setErrorsPercSd ( MathNum.Stat.sd(this.getErrorsPercVar()) );
				
				
		// Errors Flag Set To True
		this.setErr (true);
		
		
		return null;
		
	}
	
	
	/**
	 * Clone The Differential Equation
	 */
	public DifferentialEquation clone () {
		
		// Instantiate New DifferentialEquation Object
		DifferentialEquation newDiff = null;
		
		try {
			
			// Instantiate New DifferentialEquation Object
			newDiff = new DifferentialEquation (this.exprExact, this.func, this.t0, this.y0, this.step, this.tmax, this.t, this.y);
		
			if (this.solved) { // Already Solved, Copy The Solution
				
				newDiff.setSolved(true); // Set Solved
				
				double[] newYk = new double[yk.length];
				
				for (int i = 0; i < yk.length; i++) {
					
					newYk[i] = yk[i];
					
				}
				
				newDiff.setYk(newYk); // Set Approximate Solution
				
				if (this.err) { // Already Computed The Errors
					
					newDiff.setErr(true); // Set Err
					newDiff.setErrorsPercAvg (this.errorsPercAvg); // Clone Average
					newDiff.setErrorsPercVar (this.errorsPercVar); // Clone Variance
					newDiff.setErrorsPercSd (this.errorsPercSd); // Clone Standard Deviation
					
					double[] newErrorsPerc = new double[this.stepNumber];
					
					for (int i = 0; i < this.stepNumber; i++) {
						
						newErrorsPerc[i] = this.errorsPerc[i];
						
					}
					
					newDiff.setYk(newErrorsPerc);
				
				}
				
			}
			
		} catch (WrongInputException | WrongCalculationException e) {
			
			e.printStackTrace();
		}
		
		return newDiff;
		
	}



	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "DifferentialEquation \nfunc=" + func + "\nexprExact="
				+ exprExact + "\nt0=" + t0 + "\ny0=" + y0 + "\nstep=" + step
				+ "\ntmax=" + tmax + "\nstepNumber=" + stepNumber + "\nt=" + t
				+ "\ny=" + y + "\ntol=" + tol + "\nmethodName=" + methodName
				+ "\nmethodType=" + methodType + "\nsolved=" + solved
				+ "\nerr=" + err + "\nhasExact=" + hasExact
				+ "\nerrorsPercAvg=" + errorsPercAvg + "\nerrorsPercVar="
				+ errorsPercVar + "\nerrorsPercSd=" + errorsPercSd + "\n";
	}
	
	
	
	
	

}
