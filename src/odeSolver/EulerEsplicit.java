package odeSolver;

import java.util.Hashtable;

import Exceptions.WrongCalculationException;
import Exceptions.WrongInputException;
import MathExpr.MathExpr;
import MathToken.MathTokenSymbol;
import Parser.MathEvaluator;


/**
 * Implementation Of The Esplicit Euler Methods
 * 
 * y[k+1] = y[k] + h*f(t[k], y[k])
 *
 */
public class EulerEsplicit extends OdeSolver {

	
	/**
	 * Euler Esplicit Constructor: General Case (With Exact Solution)
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
	public EulerEsplicit (MathExpr exact, MathExpr func, double t0, double y0, double step, double tmax, MathTokenSymbol t, MathTokenSymbol y) throws WrongInputException, WrongCalculationException {
		
		super(exact, func, t0, y0, step, tmax, t, y);
		
	}

	
	/**
	 * Euler Esplicit Constructor: General Case (Without Exact Solution)
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
	public EulerEsplicit (MathExpr func, double t0, double y0, double step, double tmax, MathTokenSymbol t, MathTokenSymbol y) throws WrongInputException, WrongCalculationException {
		
		super(func, t0, y0, step, tmax, t, y);
		
	}

	
	/**
	 * Euler Esplicit Constructor: String Symbols  (With Exact Solution)
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
	public EulerEsplicit(MathExpr exact, MathExpr func, double t0, double y0, double step, double tmax, String t, String y) throws WrongInputException, WrongCalculationException {
		
		super (exact, func, t0, y0, step, tmax, t, y);
		
	}

	
	/**
	 * Euler Esplicit Constructor: String Symbols  (Without Exact Solution)
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
	public EulerEsplicit(MathExpr func, double t0, double y0, double step, double tmax, String t, String y) throws WrongInputException, WrongCalculationException {
		
		super(func, t0, y0, step, tmax, t, y);
		
	}

	
	/**
	 * Euler Esplicit Constructor: Default t0 = 0, step = 0.1, tmax = 1 (With Exact Solution)
	 * 
	 * @param exact Exact Solution
	 * @param func f(t, y(t))
	 * @param y0 Initial Value y(t0) = y0
	 * @param t Independent Symbol t
	 * @param y Dependent Symbol y(t)
	 * @throws WrongInputException Null Input
	 * @throws WrongCalculationException 
	 */
	public EulerEsplicit(MathExpr exact, MathExpr func, double y0, MathTokenSymbol t, MathTokenSymbol y) throws WrongInputException, WrongCalculationException {
		
		super(exact, func, y0, t, y);
		
	}

	
	/**
	 * Euler Esplicit Constructor: String Symbols, Default t0 = 0, step = 0.1, tmax = 1 (Without Exact Solution)
	 * 
	 * @param exact Exact Solution
	 * @param func f(t, y(t))
	 * @param y0 Initial Value y(t0) = y0
	 * @param t Independent Symbol String t
	 * @param y Dependent Symbol String y(t)
	 * @throws WrongInputException Null Input
	 * @throws WrongCalculationException 
	 */
	public EulerEsplicit(MathExpr exact, MathExpr func, double y0, String t, String y) throws WrongInputException, WrongCalculationException {
		
		super(exact, func, y0, t, y);
		
	}

	
	/**
	 * Euler Esplicit Constructor: String Symbols, Default t0 = 0, step = 0.1, tmax = 1 (Without Exact Solution)
	 * 
	 * @param func f(t, y(t))
	 * @param y0 Initial Value y(t0) = y0
	 * @param t Independent Symbol String t
	 * @param y Dependent Symbol String y(t)
	 * @throws WrongInputException Null Input
	 * @throws WrongCalculationException 
	 */
	public EulerEsplicit(MathExpr func, double y0, String t, String y) throws WrongInputException, WrongCalculationException {
		
		super(func, y0, t, y);
		
	}
	
	

	/**
	 * @return The Numerical Method Name
	 */
	@Override
	public String getMethodName() {
		
		return "Euler Esplicit";
		
	}

	@Override
	protected double[] solveODE() {
		
		Hashtable<MathTokenSymbol, Double> hashTab = new Hashtable<MathTokenSymbol, Double>();
		MathEvaluator mathEval;
		
		for (int i = 1; i < this.stepNumber; i++) {
		
			hashTab.clear();
			hashTab.put(this.t, this.timeInterval[i-1]);
			hashTab.put(this.y, this.yk[i-1]);
			
			try {
				
				mathEval = new MathEvaluator (this.func, hashTab);
				
				this.yk[i] = yk[i-1] + this.step*mathEval.getResult().getOperandDouble();
			
			} catch (WrongCalculationException | WrongInputException e) {
				
				e.printStackTrace();
			
			}			
					
		}
		
		return this.yk;
		
	}
}
