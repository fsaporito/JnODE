package odeSolver;

import Exceptions.WrongInputException;

public class RK4 extends RangeKutta {

	/**
	 * Fourth Range-Kutta Method - Explicit - Order 4
	 * @param diff Differential Equation To Solve
	 * @throws WrongInputException
	 */
	public RK4 (DifferentialEquation diff) throws WrongInputException {

		super (diff, "RK4", "explicit", "4", 4);

	}



	@Override
	/**
	 *   0   |  0	 0	  0	   0
	 *  1/2  | 1/2 	 0	  0	   0
	 *  1/2  |  0   1/2   0	   0
	 *   1   |  0	 0	  1	   0
	 * ------|---------------------
	 *   	 | 1/6  1/3	 1/3  1/6
	 */
	protected void tableInitialize() {

		// Range-Kutta Matrix First Row
		this.A[0][0] = 0.0;
		this.A[0][1] = 0.0;
		this.A[0][2] = 0.0;
		this.A[0][3] = 0.0;

		// Range-Kutta Matrix Second Row
		this.A[1][0] = 0.5;
		this.A[1][1] = 0.0;
		this.A[1][2] = 0.0;
		this.A[1][3] = 0.0;

		// Range-Kutta Matrix Third Row
		this.A[2][0] = 0.0;
		this.A[2][1] = 0.5;
		this.A[2][2] = 0.0;
		this.A[2][3] = 0.0;

		// Range-Kutta Matrix Fourth Row
		this.A[3][0] = 0.0;
		this.A[3][1] = 0.0;
		this.A[3][2] = 1.0;
		this.A[3][3] = 0.0;

		// Weights Array
		this.b[0] =(double) 1/6;
		this.b[1] =(double) 1/3;
		this.b[2] =(double) 1/3;
		this.b[3] =(double) 1/6;

		// Nodes Array
		this.c[0] = 0.0;
		this.c[1] = 0.5;
		this.c[2] = 0.5;
		this.c[3] = 1.0;

	}

}