package odeSolver;

import java.util.Hashtable;

import Exceptions.WrongCalculationException;
import Exceptions.WrongInputException;
import MathToken.MathTokenSymbol;
import Parser.MathEvaluator;


/**
 * Implementation Of The Esplicit Euler Methods
 * 
 * y[k+1] = y[k] + h*f(t[k], y[k])
 *
 */
public class EulerExplicit extends OdeSolver {

	
	/**
	 * Euler Explicit Constructor
	 * 
	 * @param diff Differential Equation To Solve
	 * @throws WrongInputException Null Input
	 * @throws WrongCalculationException 
	 */
	public EulerExplicit (DifferentialEquation diff) throws WrongInputException, WrongCalculationException {
		
		super(diff, "EulerExplicit", "explicit", "1");
		
	}
	
	
		
	/** 
	 * Solves The Differential Equation With Euler Implicit Method
	 * 
	 * @return The yk Array With The Approximated Solutions
	 */
	@Override
	protected double[] solveODE() {
		
		int stepNumber = diff.getStepNumber();
		double[] yk = new double[stepNumber];
		yk[0] = diff.getY0();
		double[] timeInterval = diff.getTimeInterval();
		MathTokenSymbol t = diff.getT();
		MathTokenSymbol y = diff.getY();
			
		Hashtable<MathTokenSymbol, Double> hashTab = new Hashtable<MathTokenSymbol, Double>();
		MathEvaluator mathEval;
			
		for (int i = 1; i < stepNumber; i++) {
			
			hashTab.clear();
			hashTab.put(t, timeInterval[i-1]);
			hashTab.put(y, yk[i-1]);
				
			try {
					
				mathEval = new MathEvaluator (diff.getFunc(), hashTab);
					
				yk[i] = yk[i-1] + diff.getStep()*mathEval.getResult().getOperandDouble();
				
			} catch (WrongCalculationException | WrongInputException e) {
					
				e.printStackTrace();
				
			}			
						
		}
		
		diff.setYk (yk);
		diff.setSolved(true);
		
		return yk;
		
	}



	


}
