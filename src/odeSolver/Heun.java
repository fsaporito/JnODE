package odeSolver;

import Exceptions.WrongInputException;

public class Heun extends RangeKutta {

	public Heun (DifferentialEquation diff) throws WrongInputException {
		
		super (diff, "Heun", "explicit", "2", 2);
		
	}

	

	@Override
	/**
	 *  0   |  0	0
	 *  1   |  1	0
	 * -----|----------
	 *   	| 1/2  1/2
	 */
	protected void tableInitialize() {
		
		this.A[0][0] = 0;
		this.A[0][1] = 0;
		this.A[1][0] = 1;
		this.A[1][1] = 0;
		
		this.b[0] = 1/2;		
		this.b[1] = 1/2;
		
		this.c[0] = 0;		
		this.c[1] = 1;

	}

}

