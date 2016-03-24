package odeSolver;

import Exceptions.WrongInputException;

public class Heun extends RangeKutta {

	/**
	 * Heun Method - Explicit Order 2
	 * @param diff Differential Equation To Solve
	 * @throws WrongInputException
	 */
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

		// Range-Kutta Matrix First Row
		this.A[0][0] = 0.0;
		this.A[0][1] = 0.0;

		// Range-Kutta Matrix Second Row
		this.A[1][0] = 1.0;
		this.A[1][1] = 0.0;

		// Weights Array
		this.b[0] = 0.5;
		this.b[1] = 0.5;

		// Nodes Array
		this.c[0] = 0.0;
		this.c[1] = 1.0;

	}

}

