package MathNum;

import Exceptions.WrongCalculationException;
import Exceptions.WrongExpressionException;
import Exceptions.WrongInputException;
import MathExpr.MathExpr;
import MathToken.MathTokenSymbol;
import MathToken.Operators;
import Parser.MathParser;

public class AlgebraicEquationEval {

	/**
	 * Solve the algebraic equation
	 * firstMember = secondMember
	 * with String parameters in regard to symbol as a String
	 *
	 * @param firstMember First member of the equation
	 * @param secondMember Second member of the equation
	 * @param symbol Variable to find
	 * @param seed Starting point for the approximated solution
	 * @return The approximated solution value
	 * @throws WrongInputException
	 * @throws WrongExpressionException
	 * @throws WrongCalculationException
	 */
	public static double evalAlgebraic (String firstMember, String secondMember, String symbol, double seed) throws WrongInputException, WrongExpressionException, WrongCalculationException {

		String strFun = firstMember + " - ( " + secondMember + " )";

		MathTokenSymbol sym = new MathTokenSymbol (symbol);

		MathParser parser = new MathParser (strFun, "infix");
		MathExpr expr = parser.getMathExpr();

		return AlgebraicEquationEval.evalAlgebraicZero (expr, sym, seed);

	}


	/**
	 * Solve the algebraic equation
	 * firstMember = secondMember
	 * with mathExpr parameters in regard to symbol as MathTokenSymbol
	 *
	 * @param firstMember First member of the equation
	 * @param secondMember Second member of the equation
	 * @param symbol Variable to find
	 * @param seed Starting point for the approximated solution
	 * @return The approximated solution value
	 * @throws WrongInputException
	 * @throws WrongExpressionException
	 * @throws WrongCalculationException
	 */
	public static double evalAlgebraic (MathExpr firstMember, MathExpr secondMember, MathTokenSymbol symbol, double seed) throws WrongExpressionException, WrongCalculationException, WrongInputException {

		MathExpr expr = new MathExpr (Operators.minus_b(), firstMember, secondMember);

		return AlgebraicEquationEval.evalAlgebraicZero (expr, symbol, seed);

	}


	/**
	 * Solve the algebraic equation
	 * mathExpr = 0
	 * in regard to symbol as a String
	 *
	 * @param mathExpr Expression to analyze
	 * @param symbol Variable to find
	 * @param seed Starting point for the approximated solution
	 * @return The approximated solution value
	 * @throws WrongInputException
	 * @throws WrongExpressionException
	 * @throws WrongCalculationException
	 */
	public static double evalAlgebraicZero (String mathExpr, String symbol, double seed) throws WrongInputException, WrongExpressionException, WrongCalculationException {

		MathTokenSymbol sym = new MathTokenSymbol (symbol);

		return AlgebraicEquationEval.evalAlgebraicZero (mathExpr, sym, seed);

	}

	/**
	 * Solve the algebraic equation
	 * mathExpr = 0
	 * in regard to symbol as a mathTokenSymbol
	 *
	 * @param mathExpr Expression to analyze
	 * @param symbol Variable to find
	 * @param seed Starting point for the approximated solution
	 * @return The approximated solution value
	 * @throws WrongInputException
	 * @throws WrongExpressionException
	 * @throws WrongCalculationException
	 */
	public static double evalAlgebraicZero (String mathExpr, MathTokenSymbol symbol, double seed) throws WrongInputException, WrongExpressionException, WrongCalculationException {

		MathParser parser = new MathParser (mathExpr, "infix");
		MathExpr expr = parser.getMathExpr();

		return AlgebraicEquationEval.evalAlgebraicZero (expr, symbol, seed);

	}


	/**
	 * Solve the algebraic equation
	 * mathExpr = 0
	 * in regard to symbol as a mathTokenSymbol
	 *
	 * @param mathExpr Expression to analyze
	 * @param symbol Variable to find
	 * @param seed Starting point for the approximated solution
	 * @return The approximated solution value
	 * @throws WrongInputException
	 * @throws WrongExpressionException
	 * @throws WrongCalculationException
	 */
	public static double evalAlgebraicZero (MathExpr mathExpr, MathTokenSymbol symbol, double seed) throws WrongInputException, WrongCalculationException, WrongExpressionException {

		return MathNum.Roots.newton(mathExpr, symbol, seed);

	}


}
