package MathNum;

import java.util.ArrayList;
import java.util.Hashtable;

import Exceptions.WrongCalculationException;
import Exceptions.WrongExpressionException;
import Exceptions.WrongInputException;
import MathToken.MathTokenSymbol;
import MatrixMathExpr.ArrayExprC;
import MatrixMathExpr.MatOp;
import MatrixMathExpr.Matrix;

public class NewtonVector {

	static double precision = 0.0000000001;

	/**
	 * Newton Method
	 *
	 * @param func Function To Analyze
	 * @param symbols Symbol List
	 * @param y0 Starting Point
	 * @return The Root
	 * @throws WrongInputException
	 * @throws WrongCalculationException
	 * @throws WrongExpressionException
	 */

	public static double[] newtonVector (ArrayExprC func, ArrayList<MathTokenSymbol> symbols, double[] y0) throws WrongInputException, WrongCalculationException, WrongExpressionException {

		if (func == null) {

			throw new WrongInputException ("newtonVector() - Null Input Function!!!");

		}

		if (symbols == null) {

			throw new WrongInputException ("newtonVector() - Null Input Symbols!!!");

		}

		if (symbols.isEmpty()) {

			throw new WrongInputException ("newtonVector() - Empty Input Symbols!!!");

		}

		if (y0 == null) {

			throw new WrongInputException ("newtonVector() - Null Starting Vector Point!!!");

		}

		if (y0.length > symbols.size()) {

			throw new WrongInputException ("newtonVector() - Not Enough Symbols!!!");

		}

		if (y0.length < symbols.size()) {

			throw new WrongInputException ("newtonVector() - Not Enough Dimensions For The Starting Point Vector!!!");

		}

		// Jacobian
		Matrix jacobian = Jacobian.JacobianMatrix(func.matrixElementsList(), symbols);

		// Jacobian Inverse
		Matrix jacInv = jacobian.invert(); //Jacobian.inverse();

		// HashTable Creation
		Hashtable<MathTokenSymbol, Double> hashTab = new Hashtable<MathTokenSymbol, Double>();

		for (int i = 0; i < symbols.size(); i++) {

			hashTab.put(symbols.get(i), (Double) y0[i]);

		}

		// Y0 = f(y0)
		double[] Y0 = func.evalSymbolic(hashTab).getOperandDouble();

		// Check If y0 Is The Root
		boolean flag = true;
		for (int i = 0; (i < Y0.length && flag); i++) {

			if (Math.abs(Y0[i]) > precision) {

				flag = false;

			}

		}

		if (!flag) {

			return Y0;

		}

		double[] yold = y0;

		double[] Yold = Y0;

		while (flag) {

			hashTab.clear();

			for (int i = 0; i < symbols.size(); i++) {

				hashTab.put(symbols.get(i), (Double) yold[i]);

			}

			Yold = func.evalSymbolic(hashTab).getOperandDouble();

			y0 = MatOp.minusMM(new ArrayExprC(yold, yold.length) ,(MatOp.multMM(new ArrayExprC(Yold, Yold.length), jacInv))).getOperandDouble();

			hashTab.clear();

			for (int i = 0; i < symbols.size(); i++) {

				hashTab.put(symbols.get(i), (Double) yold[i]);

			}

			Y0 = func.evalSymbolic(y0).getOperandDouble();

			yold = y0;

		}

		return y0;


	}


}
