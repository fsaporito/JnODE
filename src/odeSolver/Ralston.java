package odeSolver;

import Exceptions.WrongInputException;

public class Ralston extends RangeKutta {

	public Ralston (DifferentialEquation diff) throws WrongInputException {
		
		super (diff, "Ralston", "explicit", "2", 2);
		
	}

	

	@Override
	/**
	 *   0   |  0	0
	 *  2/3  | 2/3 	0
	 * ------|----------
	 *   	 | 1/4  3/4
	 */
	protected void tableInitialize() {
		
		this.A[0][0] = 0;
		this.A[0][1] = 0;
		this.A[1][0] = 2/3;
		this.A[1][1] = 0;
		
		this.b[0] = 1/4;		
		this.b[1] = 3/4;
		
		this.c[0] = 0;		
		this.c[1] = 2/3;

	}

}


