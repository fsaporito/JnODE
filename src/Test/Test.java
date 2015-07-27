package Test;


import java.util.ArrayList;

import odeSolver.EulerEsplicit;
import odeSolver.EulerImplicit;
import odeSolver.OdeSolver;
import Exceptions.WrongCalculationException;
import Exceptions.WrongExpressionException;
import Exceptions.WrongInputException;
import MathExpr.MathExpr;
import MathToken.MathTokenSymbol;
import Parser.MathParser;


public class Test {


	public static void main(String[] args) throws WrongInputException, WrongExpressionException, WrongCalculationException {
		
		
		
		String function = "y";
		MathParser parserFun = new MathParser (function, "infix");
		MathExpr functionExpr = parserFun.getMathExpr();
		
		String exact = "e^t";
		MathParser parserExact = new MathParser (exact, "infix");
		MathExpr exactExpr = parserExact.getMathExpr();
		
		double t0 = 0;
		double y0 = exactExpr.evalSymbolic(t0).getOperandDouble();
		double tmax = 1;
		double step = 0.1;
		
		ArrayList<OdeSolver> ode = new ArrayList<OdeSolver>();
		
		OdeSolver ee = new EulerEsplicit (exactExpr, functionExpr, t0, y0, step, tmax, new MathTokenSymbol ("t"), new MathTokenSymbol ("y"));
		ode.add(ee);
		
		OdeSolver ei = new EulerImplicit (exactExpr, functionExpr, t0, y0, step, tmax, new MathTokenSymbol ("t"), new MathTokenSymbol ("y"));
		ode.add(ei);
		
		
		for (int i = 0; i < ode.size(); i++) {
		
			System.out.println (ode.get(i).getMethodName() + ":");
			
			System.out.println ("Error Avg: " + ei.getErrorAvg());
			System.out.println ("Error Perc Avg: " + ei.getErrorPercAvg());
			System.out.println ("");
		
			System.out.println ("Error Var: " + ei.getErrorVar());
			System.out.println ("Error Perc Var: " + ei.getErrorPercVar());
			System.out.println ("");
		
			System.out.println ("Error SD: " + ei.getErrorSd());
			System.out.println ("Error Perc SD: " + ei.getErrorPercSd());
			System.out.println ("\n\n");
			
		}
		
	
			
	}

}
