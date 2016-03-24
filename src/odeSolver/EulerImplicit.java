package odeSolver;


import Exceptions.WrongCalculationException;
import Exceptions.WrongExpressionException;
import Exceptions.WrongInputException;
import MathNum.AlgebraicEquationEval;
import MathToken.MathTokenSymbol;

/**
 * Implementation Of The Implicit Euler Methods
 *
 * y[k+1] = y[k] + h*f(t[k+1], y[k+1])
 *
 * y[k+1] - y[k] - h*f(t[k+1], y[k+1]) = 0
 *
 */
public class EulerImplicit extends OdeSolver {


	/**
	 * Euler Implicit Constructor
	 *
	 * @param diff Differential Equation To Solve
	 * @throws WrongInputException Null Input
	 * @throws WrongCalculationException
	 */
	public EulerImplicit (DifferentialEquation diff) throws WrongInputException, WrongCalculationException {

		super(diff, "EulerImplicit","implicit", "1");

	}



	/**
	 * Solves The Differential Equation With Euler Implicit Method
	 *
	 * @return The yk Array With The Approximated Solutions
	 */
	@Override
	protected double[] solveODE() {

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
						        //diff.getFunc().substituteSymbol(t, timeInterval[i-1]) +
						        diff.getFunc().substituteSymbol(t, timeInterval[i]) +
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


