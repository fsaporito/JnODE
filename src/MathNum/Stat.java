package MathNum;

import Exceptions.WrongInputException;

public class Stat {

	
	/**
	 * This Methods Computes The Average Between The Input Values
	 * 
	 * @param valArr Input Values
	 * @return The Average
	 * @throws WrongInputException
	 */
	public static double avg (double[] valArr) throws WrongInputException {
		
		double avg = 0;
		
		if (valArr == null) { // Null Input
			
			throw new WrongInputException ("Statistic - avg(): Null Input!!!");
			
		}
		
		if (valArr.length == 0) { // Empty Input
			
			throw new WrongInputException ("Statistic - avg(): Empty Input!!!");
			
		}
		
		
		// Values Summation
		for (int i = 0; i < valArr.length; i++) {
			
			avg += valArr[i];			
			
		}
		
		
		// Avg Normalisation
		avg /= valArr.length;
		
		
		return avg;
		
	}
	
	
	
	/**
	 * This Methods Computes The Variance Between The Input Values
	 * 
	 * @param valArr Input Values
	 * @return The Variance
	 * @throws WrongInputException
	 */
	public static double var (double[] valArr) throws WrongInputException {
		
		return MathNum.Stat.var(valArr, MathNum.Stat.avg(valArr));
		
	}
	
	
	
	/**
	 * This Methods Computes The Variance Between The Input Values
	 * 
	 * @param valArr Input Values
	 * @param avg Values Average
	 * @return The Variance
	 * @throws WrongInputException
	 */
	public static double var (double[] valArr, double avg) throws WrongInputException {
		
		double var = 0;

		if (valArr == null) { // Null Input
			
			throw new WrongInputException ("Statistic - avg(): Null Input!!!");
			
		}
		
		if (valArr.length == 0) { // Empty Input
			
			throw new WrongInputException ("Statistic - avg(): Empty Input!!!");
			
		}
		
		
		// Variance Computation
		for (int i = 0; i < valArr.length; i++) {
			
			var += Math.pow((valArr[i] - avg), 2);			
			
		}		
		
		return var;
		
	}
	
	
	
	/**
	 * This Methods Computes The Standard Deviation Between The Input Values
	 * 
	 * @param valArr Input Values
	 * @return The Standard Deviation
	 * @throws WrongInputException
	 */
	public static double sd (double[] valArr) throws WrongInputException {
		
		return MathNum.Stat.sd(MathNum.Stat.var(valArr));
		
	}
	
	
	
	/**
	 * This Methods Computes The Standard Deviation Given The Variance
	 * 
	 * @param var Variance
	 * @return The Standard Deviation
	 * @throws WrongInputException
	 */
	public static double sd (double var) throws WrongInputException {
		
		double sd = Math.sqrt(var);
		
		return sd;
		
	}
	
	

}
