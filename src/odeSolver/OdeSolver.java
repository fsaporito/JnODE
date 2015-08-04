package odeSolver;


import Exceptions.WrongCalculationException;
import Exceptions.WrongInputException;
import MathExpr.MathExpr;

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
	
	
	protected String methodName;
	protected String methodType;
	protected String methodOrder;
	
	protected DifferentialEquation diff;
	
	
	public OdeSolver (DifferentialEquation diff, String methodName, String methodType, String methodOrder) throws WrongInputException {
		
		if (diff == null) {
			
			throw new WrongInputException ("odeSolver()- Null Differential Equation");
			
		}
		
		// Differential Equation Instantiation
		this.diff = diff;		

		// Solver Name Instantiation
		if (methodName == null || methodName == "") {
			
			this.methodName = "ODE Generical Method";
			
		} else {
			
			this.methodName = methodName;
			
		}
		
		// Solver Type Instantiation
		if (methodType == null || methodType == "") {
			
			this.methodType = "Type Not Specified";
			
		} else {
			
			this.methodType = methodType;
			
		}

		
		// Solver Order Instantiation
		if (methodOrder == null || methodOrder == "") {
	
			this.methodOrder = "Order Not Specified";
	
		} else {
	
			this.methodOrder = methodOrder;
	
		}


		
	}
	
	
	


	/**
	 * @return the methodName
	 */
	public String getMethodName() {
		return methodName;
	}





	/**
	 * @return the methodType
	 */
	public String getMethodType() {
		return methodType;
	}





	/**
	 * @return the methodOrder
	 */
	public String getMethodOrder() {
		return methodOrder;
	}





	/**
	 * @return the diff
	 */
	public DifferentialEquation getDiff() {
		return diff;
	}





	public double[] solve () {
		
		if (this.diff.isSolved()) {
			
			return this.diff.getYk();
			
		}
		
		return this.solveODE();
		
	}
	
	
	/** 
	 * Abstract Methods For Solving The Differential Equation
	 * 
	 * @return The yk Array With The Approximated Solutions
	 */
	protected abstract double[] solveODE();
	
	

	/** 
	 * Compute The Errors Done By Solving Numerically The Differential Equation
	 * 
	 * @param exactSolution
	 * @throws WrongInputException 
	 * @throws WrongCalculationException 
	 */
	public double[] errors (MathExpr exactSolution) throws WrongInputException, WrongCalculationException {
		
		if (exactSolution == null) {
			
			throw new WrongInputException ("OdeSolver.errors()- Null ExactSolution");
			
		}
		
		this.diff.setExprExact(exactSolution);
		
		this.diff.setHasExact(true);
		
		return this.errors();
		
	}
		
		
	/** 
	 * Compute The Errors Done By Solving Numerically The Differential Equation
	 * 
	 * @throws WrongInputException 
	 * @throws WrongCalculationException 
	 */
	public double[] errors () throws WrongInputException, WrongCalculationException {
			
		if (!this.diff.isHasExact() || this.diff.getExprExact() == null) {
				
			throw new WrongInputException ("errors()- Null ExactSolution");
				
		}	
		
		double[] yk; // Approximated Solution
		
		if (!diff.isSolved()) {			
			
			yk = this.solveODE();
			
		} else {
			
			yk = this.diff.getYk();
			
		}
		
		// Exact Solution
		double[] exactk = new double[this.diff.getStepNumber()];
		for (int i = 0; i < this.diff.getStepNumber(); i++) { // Computing The Exact Solution Values
									
			exactk[i] = this.diff.getExprExact().evalSymbolic(this.diff.getTimeInterval()[i]).getOperandDouble();
									
		}
				

				
		// Errors Computation
		double[] errorsPerc = new double[this.diff.getStepNumber()];
		for (int i = 0; i < this.diff.getStepNumber(); i++) {
						
			errorsPerc[i] = Math.abs((exactk[i] - yk[i])/exactk[i]) * 100;
						
		}
		
		this.diff.setErrorsPerc(errorsPerc);			
				
			
				
		// Errors Percentage Average
		this.diff.setErrorsPercAvg (MathNum.Stat.avg ( this.diff.getErrorsPerc() ) );
				
				
		
		// Errors Percentage Variance
		this.diff.setErrorsPercVar ( MathNum.Stat.var ( this.diff.getErrorsPerc(), this.diff.getErrorsPercAvg() ) );
				
						
		// Errors Percentage Standard Deviation
		this.diff.setErrorsPercSd ( MathNum.Stat.sd(this.diff.getErrorsPercVar()) );
				
				
		// Errors Flag Set To True
		this.diff.setErr (true);		
		
		
		return errorsPerc;
		
	}
	
}
