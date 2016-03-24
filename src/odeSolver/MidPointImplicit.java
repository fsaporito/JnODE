package odeSolver;

import Exceptions.WrongCalculationException;
import Exceptions.WrongExpressionException;
import Exceptions.WrongInputException;
import MathNum.AlgebraicEquationEval;
import MathToken.MathTokenSymbol;

public class MidPointImplicit extends RangeKutta {

	/**
	 * Midpoint Implicit Method - Implicit - Order 2
	 * @param diff Differential Equation To Solve
	 * @throws WrongInputException
	 */
	public MidPointImplicit (DifferentialEquation diff) throws WrongInputException {

		super(diff, "MidPointImplitic", "implicit", "2", 2);

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


	@Override
	protected double[] solveODE () {

		int stepNumber = diff.getStepNumber();
		double[] yk = new double[stepNumber];
		yk[0] = diff.getY0();
		double[] timeInterval = diff.getTimeInterval();
		MathTokenSymbol t = diff.getT();
		MathTokenSymbol y = diff.getY();

		for (int i = 1; i < stepNumber; i++) {

			try {

				String strFun = y.getValue() +
						        " - " +
						        yk[i-1] +
						        " - (" +
						        diff.getStep() +
						        " * (" +
						        diff.getFunc().substituteSymbol(t, timeInterval[i-1]) +
						        "))";

				yk[i] = AlgebraicEquationEval.evalAlgebraicZero(strFun, y, yk[i-1]);

			} catch (WrongCalculationException | WrongInputException | WrongExpressionException e) {

				e.printStackTrace();

			}

		}

		diff.setYk (yk);
		diff.setSolved(true);

		return yk;

	}

}

