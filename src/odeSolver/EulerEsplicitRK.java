package odeSolver;

import Exceptions.WrongInputException;

public class EulerEsplicitRK extends RangeKutta {

	public EulerEsplicitRK (DifferentialEquation diff) throws WrongInputException {
		
		super (diff, "EulerEsplicitRK", "explicit", "1", 1);
		
	}

	@Override
	/**
	 * 0 | 0
	 * --|--
	 *   | 1
	 */
	protected void tableInitialize() {
		
		// Range-Kutta Matrix First Row
		this.A[0][0] = 0;
		
		// Weights Array
		this.b[0] = 1;
		
		// Nodes Array
		this.c[0] = 0;
		
		

	}

}
