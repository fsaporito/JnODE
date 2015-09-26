package MathNum;

import Exceptions.WrongCalculationException;
import Exceptions.WrongExpressionException;
import Exceptions.WrongInputException;
import MathExpr.MathExpr;
import MathToken.MathTokenSymbol;
import MathToken.Operators;
import Parser.MathParser;

public class AlgebraicEquationEval {

	
	public static double evalAlgebraic (String firstMember, String secondMember, String symbol, double seed) throws WrongInputException, WrongExpressionException, WrongCalculationException {
		
		String strFun = firstMember + " - ( " + secondMember + " )";
		
		MathTokenSymbol sym = new MathTokenSymbol (symbol);
		
		MathParser parser = new MathParser (strFun, "infix");		
		MathExpr expr = parser.getMathExpr();
		
		return AlgebraicEquationEval.evalAlgebraicZero (expr, sym, seed);
				
	}
	
	
	public static double evalAlgebraic (MathExpr firstMember, MathExpr secondMember, MathTokenSymbol symbol, double seed) throws WrongExpressionException, WrongCalculationException, WrongInputException {
		
		MathExpr expr = new MathExpr (Operators.minus_b(), firstMember, secondMember);
				
		return AlgebraicEquationEval.evalAlgebraicZero (expr, symbol, seed);
		
	}
	
	
	public static double evalAlgebraicZero (String mathExpr, String symbol, double seed) throws WrongInputException, WrongExpressionException, WrongCalculationException {
		
		MathTokenSymbol sym = new MathTokenSymbol (symbol);
		
		return AlgebraicEquationEval.evalAlgebraicZero (mathExpr, sym, seed);
				
	}
	
	public static double evalAlgebraicZero (String mathExpr, MathTokenSymbol symbol, double seed) throws WrongInputException, WrongExpressionException, WrongCalculationException {
		
		MathParser parser = new MathParser (mathExpr, "infix");		
		MathExpr expr = parser.getMathExpr();
		
		return AlgebraicEquationEval.evalAlgebraicZero (expr, symbol, seed);
				
	}
	
	
	public static double evalAlgebraicZero (MathExpr mathExpr, MathTokenSymbol symbol, double seed) throws WrongInputException, WrongCalculationException, WrongExpressionException {
		
		return MathNum.Roots.newton(mathExpr, symbol, seed);
		
	}
	
	
}
