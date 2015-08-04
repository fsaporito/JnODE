package Test;


import java.util.ArrayList;

import odeSolver.DifferentialEquation;
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
		
		DifferentialEquation diff = new DifferentialEquation (exactExpr, functionExpr, t0, y0, step, tmax, new MathTokenSymbol ("t"), new MathTokenSymbol ("y"));
		
		System.out.println (diff.toString());
		
		OdeSolver ee = new EulerEsplicit (diff.clone());
		ee.solve();
		ee.errors();
		ode.add(ee);
		
		OdeSolver ei = new EulerImplicit (diff.clone());
		ei.solve();
		ei.errors();
		ode.add(ei);
		
		
		for (int i = 0; i < ode.size(); i++) {
		
			System.out.println (ode.get(i).getMethodName() + ":");
			
			System.out.println ("");
			
			for (int j = 0; j < ode.get(i).getDiff().getYk().length; j++) {
				
				System.out.println ("[" + j + "] " + ode.get(i).getDiff().getYk()[j]);
				
			}
			
			System.out.println ("");
			
			System.out.println ("Error Perc Avg: " + ode.get(i).getDiff().getErrorsPercAvg());
			System.out.println ("");
		
			System.out.println ("Error Perc Var: " + ode.get(i).getDiff().getErrorsPercVar());
			System.out.println ("");
			
			System.out.println ("Error Perc SD: " + ode.get(i).getDiff().getErrorsPercSd());
			System.out.println ("\n\n");
			
		}	
			
	}

}
