package MathNum;

import Exceptions.WrongCalculationException;
import Exceptions.WrongExpressionException;
import Exceptions.WrongInputException;
import MathExpr.MathExpr;
import MathToken.MathTokenSymbol;

public class Roots {

	static double precision = 0.0000000001;
	
	/**
	 * Bisection Method 
	 * 
	 * @param func Function To Analyze
	 * @param y Symbol
	 * @param a First Point
	 * @param b Last Point
	 * @return The Root
	 * @throws WrongInputException
	 * @throws WrongCalculationException
	 * @throws WrongExpressionException 
	 */
	public static double bisection (MathExpr func, MathTokenSymbol y, double a, double b) throws WrongInputException, WrongCalculationException, WrongExpressionException {
		
		if (a == b) {
			
			throw new WrongInputException ("bisection: [" + a + ", " + b + "] is not an intervall!!!");
			
		}		
		
		// A = f(a)
		double A = func.evalSymbolic(a).getOperandDouble();
		
		// Check If a Is The Root		
		if (Math.abs(A) < precision) {
			
			return a;
			
		}
		
		
		// B = f(b)
		double B = func.evalSymbolic(b).getOperandDouble();
		
		// Check If b Is The Root		
		if (Math.abs(B) < precision) {
			
			return b;
			
		}
		
		if (Math.signum(A) == Math.signum(B)) {
			
			throw new WrongInputException ("bisection: Same function sign in a and b!!!");
			
		}
		
		
		// Medium Point Between a And b
		double m = (a + b) / 2;
		
		// M = f(m)
		double M = func.evalSymbolic(m).getOperandDouble();
		
		
		// Main Loop
		while (Math.abs(M) >= precision) {
			
			if (Math.signum(M) == Math.signum(A)) {
				
				a = m; // m Is The New a
				
			} else {
				
				b = m; // m Is The New b
				
			}
			
			// Update Medium Point Between a And b
			m = (a + b) / 2;
			
			// Update M = f(m)
			M = func.evalSymbolic(m).getOperandDouble();
		
		}	
		
		return m;
		
		
	}
	
	
	/**
	 * Secant Method 
	 * 
	 * @param func Function To Analyze
	 * @param y Symbol
	 * @param y0 First Point
	 * @return The Root
	 * @throws WrongInputException
	 * @throws WrongCalculationException
	 * @throws WrongExpressionException 
	 */
	public static double secant (MathExpr func, MathTokenSymbol y, double y0) throws WrongInputException, WrongCalculationException, WrongExpressionException {
		
		// Y0 = f(y0)
		double Y0 = func.evalSymbolic(y0).getOperandDouble();
		
		// Check If y0 Is The Root		
		if (Math.abs(Y0) < precision) {
			
			return y0;
			
		}
		
		double a = y0; // Starting Point
		
		double yold = y0 + precision; // First Point
		
		double A = func.evalSymbolic(a).getOperandDouble();
		
		double Yold = func.evalSymbolic(yold).getOperandDouble();
		
		while (Math.abs(Y0) > precision) {
			
			Yold = func.evalSymbolic(yold).getOperandDouble();
			
			y0 = yold - ( Yold * (( a - yold) / (A - Yold)));
			
			Y0 = func.evalSymbolic(y0).getOperandDouble();
			
			yold = y0;
			
		}
		
		
		
		return y0;
		
		
	}
	
	
	
	/**
	 * Newton Method 
	 * 
	 * @param func Function To Analyze
	 * @param y Symbol
	 * @param y0 First Point
	 * @return The Root
	 * @throws WrongInputException
	 * @throws WrongCalculationException
	 * @throws WrongExpressionException 
	 */
	public static double newton (MathExpr func, MathTokenSymbol y, double y0) throws WrongInputException, WrongCalculationException, WrongExpressionException {
		
		// Derive func
		MathExpr Dfunc = func.derive(y);
		
		// Y0 = f(y0)
		double Y0 = func.evalSymbolic(y0).getOperandDouble();
		
		// Check If y0 Is The Root		
		if (Math.abs(Y0) < precision) {
			
			return y0;
			
		}
		
		double yold = y0;
		
		double Yold = 0.0;
		
		double DYold = 0.0;
		
		while (Math.abs(Y0) > precision) {
			
			Yold = func.evalSymbolic(yold).getOperandDouble();
			
			DYold = Dfunc.evalSymbolic(yold).getOperandDouble();
			
			y0 = yold - ( Yold / DYold);
			
			Y0 = func.evalSymbolic(y0).getOperandDouble();
			
			yold = y0;
			
		}
		
		
		
		return y0;
		
		
	}
	

}
