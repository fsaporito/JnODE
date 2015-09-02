package odeSolver;

import Exceptions.WrongInputException;

public class MidPointExplicit extends RangeKutta {

	public MidPointExplicit (DifferentialEquation diff) throws WrongInputException {
		
		super(diff, "MidPointExplitic", "explicit", "2", 2);
		
	}

	

	@Override
	/**
	 *  0   |  0	0
	 * 1/2  | 1/2	0
	 * ----	|--------
	 *   	|  0	1
	 */
	protected void tableInitialize() {
		
		// Range-Kutta Matrix First Row
		this.A[0][0] = 0.0;
		this.A[0][1] = 0.0;
		
		// Range-Kutta Matrix Second Row
		this.A[1][0] =(double) 1/2;
		this.A[1][1] = 0.0;
		
		// Weights Array
		this.b[0] = 0.0;		
		this.b[1] = 1.0;
		
		// Nodes Array
		this.c[0] = 0.0;		
		this.c[1] =(double) 1/2;

	}

}
