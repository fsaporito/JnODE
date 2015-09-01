package odeSolver;

import Exceptions.WrongInputException;

public class EulerEsplicitRK extends RangeKutta {

	public EulerEsplicitRK(DifferentialEquation diff) throws WrongInputException {
		
		super (diff, "EulerEsplicitRK", "explicit", "1", 1);
		
	}

	@Override
	/**
	 * 0 | 0
	 * --|--
	 *   | 1
	 */
	protected void tableInitialize() {
		
		System.out.println ("Initializing Table...");
		
		this.A[0][0] = 0;
		
		this.b[0] = 1;
		
		this.c[0] = 0;
		
		

	}

}
