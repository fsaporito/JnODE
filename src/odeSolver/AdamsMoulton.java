package odeSolver;


import Exceptions.WrongCalculationException;
import Exceptions.WrongExpressionException;
import Exceptions.WrongInputException;
import MathToken.MathTokenSymbol;


public class AdamsMoulton extends LinearMultistep {

	/**
	 * Adams-Moulton Method - Implicit - Order s+1
	 * @param diff Differential Equation To Solve
	 * @param s Method Stage Number, from 0 to 4
	 * @throws WrongInputException
	 */
	public AdamsMoulton (DifferentialEquation diff, int s) throws WrongInputException {

		super(diff, "AdamsMoulton", "implicit", ""+((int)s+1), s);

		if (s < 0 || s > 4) {

			throw new WrongInputException ("AdamsMoulton- s Must Be Between 0 And 4!!!");

		}

	}

	/**
	 * Adams-Moulton Method - Implicit - Order s+1
	 * s = 0
	 * @param diff Differential Equation To Solve
	 * @throws WrongInputException
	 */
	public AdamsMoulton (DifferentialEquation diff) throws WrongInputException {

		this (diff, 0);

	}


	@Override
	protected double[] solveODE() {

		double y0 = this.diff.getY0();
		double step = this.diff.getStep();
		int stepNumber = this.diff.getStepNumber();
		double[] timeInterval = this.diff.getTimeInterval();

		MathTokenSymbol t = diff.getT();
		MathTokenSymbol y = diff.getY();

		double[] yk = new double[stepNumber];

		yk[0] = y0;

		if (stepNumber <= 1) {

			return yk;

		}

		try {

			int stepTmp = 1;
			String strFun = new String();

			// Boolean Values Used To Determine A Flag
			// Used To Approximate The Previous Values
			// With The Best Possible Method
			boolean Sless1 = true; // Step Is 0
			boolean Sless2 = true; // Step Is 0 1
			boolean Sless3 = true; // Step Is 0 1 2
			boolean Sless4 = true; // Step Is 0 1 2 3


			// Euler Implicit Method: One Step
			while ((stepTmp < stepNumber) && Sless1) {

				strFun = "";

				// y - step * (
				strFun = y.getValue() + " - " + yk[stepTmp-1] + " - (" + step + " * (";

				// coeff1 = f(t[i-1], y)
				strFun += diff.getFunc().substituteSymbol(t, timeInterval[stepTmp-1]);

				// ))
				strFun += "))";

				// y[i+1] = y[i-1] + h*CoeffSum
				// coeff1 = f(t[i-1], y)
				yk[stepTmp] = MathNum.AlgebraicEquationEval.evalAlgebraicZero(strFun, y, yk[stepTmp-1]);

				// Step Is 1 2 3 4: Exit For Loop
				if (this.s >= 1) {

					Sless1 = false;

				}

				// StepTmp +1
				stepTmp++;

			}

			// Trapezoid Method: Two Step
			while ( (stepTmp < stepNumber) && Sless2 ) {

				strFun = "";

				// y - step * (
				strFun = y.getValue() + " - " + yk[stepTmp-1] + " - (" + step + " * (";

				// coeff1 = (1/2) * f(t[i], y)
				strFun += "( 0.5 * " + diff.getFunc().substituteSymbol(t, timeInterval[stepTmp-1]) + " ) " + " + ";

				// coeff2 = (1/2) * f(t[i-1], y[i-1])
				strFun +=  "( 0.5 * " +
							diff.getFunc().substituteSymbol(t, timeInterval[stepTmp-1]).substituteSymbol(y, yk[stepTmp-1]) + " ) ";

				// ))
				strFun += "))";


				// y[i+1] = y[i-1] + h*CoeffSum
				// coeff1 = (1/2) * f(t[i], y)
				// coeff2 = (1/2) * f(t[i-1], y[i-1])
				yk[stepTmp] = MathNum.AlgebraicEquationEval.evalAlgebraicZero(strFun, y, yk[stepTmp-1]);

				// Step Is 2 3 4: Exit For Loop
				if (this.s >= 2) {

					Sless2 = false;

				}

				// StepTmp +1
				stepTmp++;

			}

			// Three Step
			while ( (stepTmp < stepNumber) && Sless3 ) {

				strFun = "";

				// y - step * (
				strFun = y.getValue() + " - " + yk[stepTmp-1] + " - (" + step + " * (";

				// coeff1 = (5/12) * f(t[i], y)
				strFun += "( " + ( (double) 5/12) + " * " + diff.getFunc().substituteSymbol(t, timeInterval[stepTmp-1]) + " ) "+ " + ";

				// coeff2 = (2/3) * f(t[i-1], y[i-1])
				strFun += "( " + ( (double) 2/3) + " * " +
							diff.getFunc().substituteSymbol(t, timeInterval[stepTmp-1]).substituteSymbol(y, yk[stepTmp-1]) + " ) "
							+ " + ";

				// coeff3 = (-1/12) * f(t[i-2], y[i-2])
				strFun += "( " + ( (double) -1/12) + " * " +
							diff.getFunc().substituteSymbol(t, timeInterval[stepTmp-2]).substituteSymbol(y, yk[stepTmp-2]) + " ) ";

				// ))
				strFun += "))";


				// y[i+1] = y[i-1] + h*CoeffSum
				// coeff1 = (5/12) * f(t[i], y)
				// coeff2 = (2/3) * f(t[i-1], y[i-1])
				// coeff3 = (-1/12) * f(t[i-2], y[i-2])
				yk[stepTmp] = MathNum.AlgebraicEquationEval.evalAlgebraicZero(strFun, y, yk[stepTmp-1]);

				// Step Is 3 4: Exit For Loop
				if (this.s >= 3) {

					Sless3 = false;

				}

				// StepTmp +1
				stepTmp++;

			}

			// Four Step
			while ( (stepTmp < stepNumber) && Sless4 ) {

				strFun = "";

				// y - step * (
				strFun = y.getValue() + " - " + yk[stepTmp-1] + " - (" + step + " * (";

				// coeff1 = (3/8) * f(t[i], y)
				strFun += "( " + ( (double) 3/12) + " * " + diff.getFunc().substituteSymbol(t, timeInterval[stepTmp-1])  + " ) " + " + ";

				// coeff2 = (19/24) * f(t[i-1], y[i-1])
				strFun +=  "( " + ( (double) 19/24) + " * " +
							diff.getFunc().substituteSymbol(t, timeInterval[stepTmp-1]).substituteSymbol(y, yk[stepTmp-1])  + " ) "
							+ " + ";

				// coeff3 = (-5/24) * f(t[i-2], y[i-2])
				strFun += "( " + ( (double) -5/12) + " * " +
							diff.getFunc().substituteSymbol(t, timeInterval[stepTmp-2]).substituteSymbol(y, yk[stepTmp-2])  + " ) "
							+ " + ";

				// coeff4 = (1/24) * f(t[i-3], y[i-3])
				strFun +=  "( " + ( (double) 1/24) + " * " +
							diff.getFunc().substituteSymbol(t, timeInterval[stepTmp-3]).substituteSymbol(y, yk[stepTmp-3])  + " ) ";

				// ))
				strFun += "))";


				// y[i+1] = y[i-1] + h*CoeffSum
				// coeff1 = (3/8) * f(t[i], y)
				// coeff2 = (19/24) * f(t[i-1], y[i-1])
				// coeff3 = (-5/24) * f(t[i-2], y[i-2])
				// coeff4 = (1/24) * f(t[i-3], y[i-3])
				yk[stepTmp] = MathNum.AlgebraicEquationEval.evalAlgebraicZero(strFun, y, yk[stepTmp-1]);

				// Step Is 4: Exit For Loop
				if (this.s >= 4) {

					Sless4 = false;

				}

				// StepTmp +1
				stepTmp++;

			}

			// Five Step
			while ( stepTmp < stepNumber ) {

				strFun = "";

				// y - step * (
				strFun = y.getValue() + " - " + yk[stepTmp-1] + " - (" + step + " * (";

				// coeff1 = (251/720) * f(t[i], y[i])
				strFun += "( " + ( (double) 251/720) + " * " + diff.getFunc().substituteSymbol(t, timeInterval[stepTmp-1])  + " ) " + " + ";

				// coeff2 = (646/720) * f(t[i-1], y[i-1])
				strFun +=  "( " + ( (double) 646/720) + " * " +
							diff.getFunc().substituteSymbol(t, timeInterval[stepTmp-1]).substituteSymbol(y, yk[stepTmp-1])  + " ) "
							+ " + ";

				// coeff3 = (-264/720) * f(t[i-2],y[i-2])
				strFun +=  "( " + ( (double) -264/720) + " * " +
							diff.getFunc().substituteSymbol(t, timeInterval[stepTmp-2]).substituteSymbol(y, yk[stepTmp-2])  + " ) "
							+ " + ";

				// coeff4 = (106/720) * f(t[i-3], y[i-3])
				strFun += "( " +  ( (double) 106/720) + " * " +
							diff.getFunc().substituteSymbol(t, timeInterval[stepTmp-3]).substituteSymbol(y, yk[stepTmp-3])  + " ) "
							+ " + ";

				// coeff5 = (-19/720) * f(t[i-4], y[i-4])
				strFun +=  "( " + ( (double) -19/720) + " * " +
							diff.getFunc().substituteSymbol(t, timeInterval[stepTmp-3]).substituteSymbol(y, yk[stepTmp-3])  + " ) ";

				// ))
				strFun += "))";

				// y[i+1] = y[i-1] + h*CoeffSum
				// coeff1 = (251/720) * f(t[i], y[i])
				// coeff2 = (646/720) * f(t[i-1], y[i-1])
				// coeff3 = (-264/720) * f(t[i-2], y[i-2])
				// coeff4 = (106/720) * f(t[i-3], y[i-3])
				// coeff5 = (-19/720) * f(t[i-4], y[i-4])
				yk[stepTmp] = MathNum.AlgebraicEquationEval.evalAlgebraicZero(strFun, y, yk[stepTmp-1]);

				// StepTmp +1
				stepTmp++;

			}

		} catch (WrongCalculationException | WrongExpressionException | WrongInputException e) {

			e.printStackTrace();

		}

		this.diff.setYk(yk);

		this.diff.setSolved(true);

		return yk;

	}




}
