package odeSolver;

import Exceptions.WrongInputException;

public class Kutta3 extends RangeKutta {

	public Kutta3 (DifferentialEquation diff) throws WrongInputException {
		
		super (diff, "Kutta3", "explicit", "3", 3);
		
	}

	

	@Override
	/**
	 *   0   |  0	 0	  0
	 *  1/2  | 1/2 	 0	  0
	 *   1   | -1	 2	  0
	 * ------|----------------
	 *   	 | 1/6  2/3	 1/6
	 */
	protected void tableInitialize() {
		
		this.A[0][0] = 0;
		this.A[0][1] = 0;
		this.A[0][2] = 0;
		this.A[1][0] = 1/2;
		this.A[1][1] = 0;
		this.A[1][2] = 0;
		this.A[2][0] = -1;
		this.A[2][1] = 2;
		this.A[2][2] = 0;
		
		this.b[0] = 1/6;		
		this.b[1] = 2/3;
		this.b[2] = 1/6;
		
		this.c[0] = 0;		
		this.c[1] = 1/2;
		this.c[2] = 1;

	}

}