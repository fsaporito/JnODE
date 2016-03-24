package odeSolver;

import Exceptions.WrongInputException;

public class Ralston extends RangeKutta {

	/**
	 * Ralston Method - Explicit - Order 2
	 * @param diff Differential Equation To Solve
	 * @throws WrongInputException
	 */
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

		// Range-Kutta Matrix First Row
		this.A[0][0] = 0.0;
		this.A[0][1] = 0.0;

		// Range-Kutta Matrix Second Row
		this.A[1][0] =(double) 2/3;
		this.A[1][1] = 0.0;

		// Weights Array
		this.b[0] = 0.25;
		this.b[1] = 0.75;

		// Nodes Array
		this.c[0] = 0.0;
		this.c[1] =(double) 2/3;

	}

}


