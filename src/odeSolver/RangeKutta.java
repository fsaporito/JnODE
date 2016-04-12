package odeSolver;

import java.util.Hashtable;

import Exceptions.WrongCalculationException;
import Exceptions.WrongExpressionException;
import Exceptions.WrongInputException;
import MathExpr.MathExpr;
import MathToken.MathTokenSymbol;
import MatrixMathExpr.ArrayExprC;
import Parser.MathEvaluator;

public abstract class RangeKutta extends GLM {

	/** Range-Kutta Matrix */
	protected double[][] A;

	/** Weight Array */
	protected double[] b;

	/** Nodes Array */
	protected double[] c;

	public RangeKutta (DifferentialEquation diff, String methodName, String methodType, String methodOrder, int r) throws WrongInputException {

		super(diff, methodName, methodType, methodOrder, r, 0);


		// Range-Kutta Matrix Definition
		this.A = new double[r][r];

		for (int i = 0; i < r; i++) {

			for (int j = 0; j < r; j++) {

				this.A[i][j] = 0.0;

			}

		}


		// Weights Array Definition
		this.b = new double[r];

		for (int i = 0; i < r; i++) {

			this.b[i] = 0.0;

		}


		// Nodes Array Definition
		this.c = new double[r];


		for (int i = 0; i < r; i++) {

			this.c[i] = 0.0;

		}


		// Fields Initialization
		this.tableInitialize();


	}


	public RangeKutta (DifferentialEquation diff, String methodName, String methodType, String methodOrder) throws WrongInputException {

		this (diff, methodName, methodType, methodOrder, 1);

	}


	protected abstract void tableInitialize ();



	@Override
	protected double[] solveODE() {

		int stepNumber = diff.getStepNumber();
		double step = diff.getStep();
		double[] yk = new double[stepNumber];
		yk[0] = diff.getY0();
		double[] timeInterval = diff.getTimeInterval();
		MathTokenSymbol t = diff.getT();
		MathTokenSymbol y = diff.getY();

		Hashtable<MathTokenSymbol, Double> hashTab = new Hashtable<MathTokenSymbol, Double>();

		double[] k = new double[this.r];

		for (int i = 0; i < this.r; i++) {

			k[i] = 0;

		}

		// sum (bi*ki)
		double kb_sum;

		// sum (ai*ki)
		double ka_sum;

		try {

			// Explicit Method
			if (this.getMethodType().equals("explicit")) {

				// y[n+1] = y[n] + SUM (i=1..r) {b[i]*k[i]}
				// k[0] = f (t[n], y[n]) # c[0] = 0, a[0][0] = 0
				// k[1] = f (t[n] + c[1]*h, y[n] + h*(a[1][0]*k[0])
				// k[2] = f (t[n] + c[2]*h, y[n] + h*(a[2][0]*k[0] + a[2][1]*k[1])
				// k[i] = f (t[n] + c[i]*h, y[n] + h*(a[i][0]*k[0] + a[i][1]*k[1] + .. + a[i][i-1]*k[i-1])

				for (int i = 1; i < stepNumber; i++) {

					ka_sum = 0;
					kb_sum = 0;

					hashTab.clear();
					hashTab.put(t, timeInterval[i-1]);
					hashTab.put(y, yk[i-1]);

					// k0 = f (tn, yn) -> Euler Esplicit
					k[0] = (new MathEvaluator (this.diff.getFunc(), hashTab)).getResult().getOperandDouble();

					kb_sum = this.b[0]*k[0];


					// Ks Loop Calculator
					// Starts from 1 because c[0] = 0
					for (int j = 1; j < this.r; j++) {

						hashTab.clear();

						// tn + h*c[j]
						hashTab.put(t, timeInterval[i-1] + step*this.c[j]);

						// ka_sum Computing
						for (int l = 0; l <= j; l++) {

							ka_sum += this.A[j][l]*k[l];

						}

						hashTab.put(y, yk[i-1] + step*ka_sum);

						k[j] = (new MathEvaluator (this.diff.getFunc(), hashTab)).getResult().getOperandDouble();

					}

					// kb_sum Computing
					for (int j = 1; j < r; j++) {

						kb_sum += this.b[j]*k[j];

					}

					// Yk[i] Computation
					yk[i] = yk[i-1] + step*kb_sum;

				}


				// Implicit Method
				if (this.getMethodType().equals("implicit")) {

					// y[n+1] = y[n] + h*SUM (i=1..r) {b[i]*k[i]}
					// k[i] = f (t[n] + c[i]*h, y[n] + h*(a[i][0]*k[0] + a[i][1]*k[1] + .. + a[i][i-1]*k[r])
					// k[i] = f(Z[i], y[n] + h*SUM (j=0...r) {A[i]*k[j])
					// It Has To Be Solved Numerically In Respect To k = k[i]

					// Z[i] Definition
					double[] Z = new double[r];

					// Function Array With The Evaluation t -> Z[i]
					MathExpr[] funcEl = new MathExpr[this.r];
					ArrayExprC funcArrC = null;

					for (int i = 1; i < stepNumber; i++) {

						// KB Sum Initialization
						kb_sum = 0;

						// Z[j] = t[i-1] + c[j]
						for (int j = 1; j < r; j++) {

							Z[j] = timeInterval[i-1] + this.c[j];

							funcEl[j] = this.diff.getFunc().evalSymbolic(Z[j], t);

						}

						funcArrC = new ArrayExprC (funcEl, this.r);

						// kb_sum Computing
						for (int j = 1; j < r; j++) {

							kb_sum += this.b[j]*k[j];

						}

						// Yk[i] Computation
						yk[i] = yk[i-1] + step*kb_sum;

					}

				}

			}

		} catch (WrongCalculationException | WrongExpressionException | WrongInputException e) {

			e.printStackTrace();

		}

		this.diff.setSolved(true);

		this.diff.setYk(yk);

		this.diff.setMethodName(this.methodName);

		this.diff.setMethodType(this.methodType);

		return yk;

	}


	public String tableToString () {

		String s = new String();


		s += "Method Name: " + this.getMethodName() + "\n";
		s += "Method Type: " + this.getMethodType() + "\n";
		s += "Method Order: " + this.getMethodOrder() + "\n";
		s += "Method r: " + this.r + "\n";

		s += "\n";


		for (int i = 0; i < this.r; i++) {

			for (int j = 0; j < this.r; j++)

			s += "A[" + i + "][" + j + "] = " + A[i][j] + "\n";

		}

		s += "\n";

		for (int i = 0; i < this.b.length; i++) {

			s += "b[" + i + "] = " + b[i] + "\n";

		}

		s += "\n";

		for (int i = 0; i < this.c.length; i++) {

			s += "c[" + i + "] = " + c[i] + "\n";

		}

		return s;

	}



}
