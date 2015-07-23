package odeSolver;

import Exceptions.WrongCalculationException;
import Exceptions.WrongExpressionException;
import Exceptions.WrongInputException;
import MathExpr.MathExpr;
import MathToken.MathTokenSymbol;
import Parser.MathParser;

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
	
	/** Exact Solution */
	protected MathExpr exprExact;
	
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
	
	/** Exact Y Solution Interval */
	protected double[] exactk;
		
	
	/** Indipendent symbol: t */
	protected MathTokenSymbol t;
	
	/** Dependent symbol: y(t) */
	protected MathTokenSymbol y;
	
	
	/** Errors Between Exact And Approximated Solution */
	protected double[] errors;
	
	/** Errors Percentage Between Exact And Approximated Solution */
	protected double[] errorsPerc;
	
	/** Errors Average */
	protected double errorAvg;
	
	/** Errors Percentage Average */
	protected double errorPercAvg;
	
	/** Errors Variance */
	protected double errorVar;
	
	/** Errors Percentage Variance */
	protected double errorPercVar;
	
	/** Errors Standard Deviation */
	protected double errorSd;
	
	/** Errors Percentage Standard Deviation */
	protected double errorPercSd;
	
	
	/** True If Already Solved	 */
	protected boolean solved;
	
	/** True If Errors Already Calculated */
	protected boolean err;
	
	
	/**
	 * Abstract Constructor: General Case (With Exact Solution)
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
	public OdeSolver (MathExpr exact, MathExpr func, double t0, double y0, double step, double tmax, MathTokenSymbol t, MathTokenSymbol y) throws WrongInputException, WrongCalculationException {
		
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
		this.func = func;		
		this.t0 = t0;		
		this.y0 = y0;		
		this.step = step;		
		this.tmax = tmax;		
		this.t = t;		
		this.y = y;	
		
		// Status Fields Initialisation
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
			
			this.solve();
			
		} else {
			
			this.errorComp(exact);
			
		}
		
	}
	
	
	/**
	 * Abstract Constructor: General Case (Without Exact Solution)
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
	public OdeSolver (MathExpr func, double t0, double y0, double step, double tmax, MathTokenSymbol t, MathTokenSymbol y) throws WrongInputException, WrongCalculationException {
	
		this (null, func, t0, y0, step, tmax,t, y);
		
	}
	

	/**
	 * Abstract Constructor: String Symbols  (With Exact Solution)
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
	public OdeSolver (MathExpr exact, MathExpr func, double t0, double y0, double step, double tmax, String t, String y) throws WrongInputException, WrongCalculationException {
	
		this (exact, func, t0, y0, step, tmax, new MathTokenSymbol (t), new MathTokenSymbol (y));
		
	}
	
		
	/**
	 * Abstract Constructor: String Symbols  (Without Exact Solution)
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
	public OdeSolver (MathExpr func, double t0, double y0, double step, double tmax, String t, String y) throws WrongInputException, WrongCalculationException {
	
		this (null, func, t0, y0, step, tmax, new MathTokenSymbol (t), new MathTokenSymbol (y));
		
	}
	

	/**
	 * Abstract Constructor: Default t0 = 0, step = 0.1, tmax = 1 (With Exact Solution)
	 * 
	 * @param exact Exact Solution
	 * @param func f(t, y(t))
	 * @param y0 Initial Value y(t0) = y0
	 * @param t Independent Symbol t
	 * @param y Dependent Symbol y(t)
	 * @throws WrongInputException Null Input
	 * @throws WrongCalculationException 
	 */
	public OdeSolver (MathExpr exact, MathExpr func, double y0, MathTokenSymbol t, MathTokenSymbol y) throws WrongInputException, WrongCalculationException {
	
		this (exact, func, 0, y0, 0.1, 1, t, y);
		
	}
		
	
	/**
	 * Abstract Constructor: String Symbols, Default t0 = 0, step = 0.1, tmax = 1 (Without Exact Solution)
	 * 
	 * @param exact Exact Solution
	 * @param func f(t, y(t))
	 * @param y0 Initial Value y(t0) = y0
	 * @param t Independent Symbol String t
	 * @param y Dependent Symbol String y(t)
	 * @throws WrongInputException Null Input
	 * @throws WrongCalculationException 
	 */
	public OdeSolver (MathExpr exact, MathExpr func, double y0, String t, String y) throws WrongInputException, WrongCalculationException {
	
		this (exact, func, 0, y0, 0.1, 1, new MathTokenSymbol (t), new MathTokenSymbol (y));
		
	}
	
	
	/**
	 * Abstract Constructor: String Symbols, Default t0 = 0, step = 0.1, tmax = 1 (Without Exact Solution)
	 * 
	 * @param func f(t, y(t))
	 * @param y0 Initial Value y(t0) = y0
	 * @param t Independent Symbol String t
	 * @param y Dependent Symbol String y(t)
	 * @throws WrongInputException Null Input
	 * @throws WrongCalculationException 
	 */
	public OdeSolver (MathExpr func, double y0, String t, String y) throws WrongInputException, WrongCalculationException {
	
		this (null, func, 0, y0, 0.1, 1, new MathTokenSymbol (t), new MathTokenSymbol (y));
		
	}
	
	
	
	
	/**
	 * @return the function
	 */
	public MathExpr getFunc() {
		
		return this.func;
	
	}

	
	/**
	 * @return the exprExact
	 * @throws WrongCalculationException 
	 */
	public MathExpr getExprExact() throws WrongCalculationException {
		
		if (!this.err) {
			
			throw new WrongCalculationException ("OdeSolver- No Exact Expression Given!!!");
			
		}
		
		return this.exprExact;
		
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
	 * @return the yk
	 */
	public double[] getYk() {
		
		return this.yk;
		
	}
	

	/**
	 * @return the exactk
	 * @throws WrongCalculationException 
	 */
	public double[] getExactk() throws WrongCalculationException {
		
		if (!this.err) {
			
			throw new WrongCalculationException ("OdeSolver- No Exact Expression Given!!!");
			
		}
		
		return this.exactk;
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
	 * @return the errors
	 * @throws WrongCalculationException 
	 */
	public double[] getErrors() throws WrongCalculationException {
		
		if (!this.err) {
			
			throw new WrongCalculationException ("OdeSolver- Errors Not Calculated!!!");
			
		}
		
		return this.errors;
	
	}
	

	/**
	 * @return the errorsPerc
	 * @throws WrongCalculationException 
	 */
	public double[] getErrorsPerc() throws WrongCalculationException {
		
		if (!this.err) {
			
			throw new WrongCalculationException ("OdeSolver- Errors Not Calculated!!!");
			
		}
		
		return this.errorsPerc;
	
	}
	

	/**
	 * @return the errorAvg
	 * @throws WrongCalculationException 
	 */
	public double getErrorAvg() throws WrongCalculationException {
		
		if (!this.err) {
			
			throw new WrongCalculationException ("OdeSolver- Errors Not Calculated!!!");
			
		}
		
		return this.errorAvg;
	
	}
	
	

	/**
	 * @return the errorPercAvg
	 * @throws WrongCalculationException 
	 */
	public double getErrorPercAvg() throws WrongCalculationException {
		
		if (!this.err) {
			
			throw new WrongCalculationException ("OdeSolver- Errors Not Calculated!!!");
			
		}
		
		return errorPercAvg;
		
	}
	
	
	/**
	 * @return the errorVar
	 * @throws WrongCalculationException 
	 */
	public double getErrorVar() throws WrongCalculationException {
		
		if (!this.err) {
			
			throw new WrongCalculationException ("OdeSolver- Errors Not Calculated!!!");
			
		}
		
		return this.errorVar;
	
	}
	

	/**
	 * @return the errorPercVar
	 * @throws WrongCalculationException 
	 */
	public double getErrorPercVar() throws WrongCalculationException {
		
		if (!this.err) {
			
			throw new WrongCalculationException ("OdeSolver- Errors Not Calculated!!!");
			
		}
		
		return this.errorPercVar;
	
	}
	

	/**
	 * @return the errorSd
	 * @throws WrongCalculationException 
	 */
	public double getErrorSd() throws WrongCalculationException {
		
		if (!this.err) {
			
			throw new WrongCalculationException ("OdeSolver- Errors Not Calculated!!!");
			
		}
		
		return this.errorSd;
	
	}


	/**
	 * @return the errorPercSd
	 * @throws WrongCalculationException 
	 */
	public double getErrorPercSd() throws WrongCalculationException {
		
		if (!this.err) {
			
			throw new WrongCalculationException ("OdeSolver- Errors Not Calculated!!!");
			
		}
		
		return this.errorPercSd;
	
	}
	
	
	/**
	 * @return True If Already Solved
	 */
	public boolean isSolved() {
		
		return this.solved;
		
	}
	
	
	/**
	 * @return TRUE If Errors Have Already Been Computed
	 */
	public boolean isErr() {
		
		return this.err;
	
	}
	
	
	/**
	 * @return The Numerical Method Name
	 */
	public abstract String getMethodName();



	/** 
	 * Returns The Solution
	 * 
	 * @return The yk Array With The Approximated Solutions
	 */
	public double[] solve () {
		
		double[] solution = new double[this.stepNumber];
		
		if (this.solved) {
			
			solution = this.yk;
			
		} else {
			
			solution = this.solveODE();
			
			this.solved = true;
			
		}
		
		return solution;
		
		
	}
	
	
	/** 
	 * Abstract Methods For Solving The Differential Equation
	 * 
	 * @return The yk Array With The Approximated Solutions
	 */
	protected abstract double[] solveODE();
	
	
	
	/**
	 * Computes Errors And Errors Percentage (String Input)
	 * 
	 * Errors: Difference Between Exact And Approximated Solution
	 * ErrorsPerc: Errors In Percentage
	 * ErrorAvg: Average Error
	 * ErrorAvgPerc: Average Percentage Error
	 * ErrorVar: Error Variance
	 * ErrorVarPerc: Error Percentage Variance
	 * 
	 * @param exactSolution String Containing The Exact Solution Expression
	 * @throws WrongInputException
	 * @throws WrongCalculationException
	 */
	public void errorComp (String exactSolution) throws WrongInputException, WrongExpressionException, WrongCalculationException {
		
		MathParser parser = new MathParser (exactSolution, "infix");
		
		this.errorComp(parser.getMathExpr());		
		
	}
		
	
	/**
	 * Computes Errors And Errors Percentage (MathExpr Input)
	 * 
	 * Errors: Difference Between Exact And Approximated Solution
	 * ErrorsPerc: Errors In Percentage
	 * ErrorAvg: Average Error
	 * ErrorAvgPerc: Average Percentage Error
	 * ErrorVar: Error Variance
	 * ErrorVarPerc: Error Percentage Variance
	 * 
	 * @param exact MathExpression Containing The Exact Solution
	 * @throws WrongInputException
	 * @throws WrongCalculationException
	 */
	public void errorComp (MathExpr exact) throws WrongInputException, WrongCalculationException {
		
		if (exact == null) {
			
			throw new WrongInputException ("ErrorComp- Null Exact Solution");
			
		}
		
		this.exprExact = exact;
		
		
		
		// Exact Solution
		this.exactk = new double[this.stepNumber];
		for (int i = 0; i < this.stepNumber; i++) { // Computing The Exact Solution Values
							
			this.exactk[i] = this.exprExact.evalSymbolic(this.timeInterval[i]).getOperandDouble();
							
		}
		
		// Numerically Solving The ODE
		this.solve();
		
		// Errors Computation
		this.errors = new double[this.stepNumber];	
		this.errorsPerc = new double[this.stepNumber];
		for (int i = 0; i < this.stepNumber; i++) {
				
				this.errors[i] = Math.abs(this.exactk[i] - this.yk[i]);
				this.errorsPerc[i] = Math.abs((this.exactk[i] - this.yk[i])/this.exactk[i]) * 100;
				
		}
		
		
		// Errors Average
		this.errorAvg = MathNum.Stat.avg(this.errors);		
		
		// Errors Percentage Average
		this.errorPercAvg = MathNum.Stat.avg(this.errorsPerc);
		
		
		// Errors Variance
		this.errorVar = MathNum.Stat.var(this.errors, this.errorAvg);		
		
		// Errors Percentage Variance
		this.errorPercVar = MathNum.Stat.var(this.errorsPerc, this.errorPercAvg);
		
		
		// Errors Standard Deviation
		this.errorSd = MathNum.Stat.sd(this.errorVar);		
				
		// Errors Percentage Standard Deviation
		this.errorPercSd = MathNum.Stat.sd(this.errorPercVar);
		
		
		// Errors Flag Set To True
		this.err = true;
		
	}
	
	
	
}
