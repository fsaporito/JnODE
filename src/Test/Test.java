package Test;


import java.util.ArrayList;

import odeSolver.DifferentialEquation;
import odeSolver.EulerExplicit;
import odeSolver.EulerExplicitRK;
import odeSolver.EulerImplicit;
import odeSolver.Heun;
import odeSolver.Kutta3;
import odeSolver.MidPointExplicit;
import odeSolver.OdeSolver;
import odeSolver.RK4;
import odeSolver.Ralston;
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
		
		// Euler Esplicit
		OdeSolver ee = new EulerExplicit (diff.clone());
		ee.solve();
		ee.errors();
		ode.add(ee);
		
		// Euler Esplicit (Range-Kutta Form)
		OdeSolver eeRK = new EulerExplicitRK (diff.clone());
		eeRK.solve();
		eeRK.errors();
		ode.add(eeRK);
		
		// Euler Implicit
		OdeSolver ei = new EulerImplicit (diff.clone());
		ei.solve();
		ei.errors();
		ode.add(ei);
		
		// MidPoint Explicit
		OdeSolver mpe = new MidPointExplicit (diff.clone());
		mpe.solve();
		mpe.errors();
		//ode.add(mpe);
		
		// Heun
		OdeSolver heun = new Heun (diff.clone());
		heun.solve();
		heun.errors();
		ode.add(heun);

		// Ralston
		OdeSolver ralston = new Ralston (diff.clone());
		ralston.solve();
		ralston.errors();
		ode.add(ralston);	
		
		
		// Kutta3
		OdeSolver kutta3 = new Kutta3 (diff.clone());
		kutta3.solve();
		kutta3.errors();
		ode.add(kutta3);

		// RK4
		OdeSolver rk4 = new RK4 (diff.clone());
		rk4.solve();
		rk4.errors();
		ode.add(rk4);
		
		System.out.println ("\n");
		
		for (int i = 0; i < ode.size(); i++) {
		
			System.out.println (ode.get(i).getMethodName() + "\n\n  Type: " + ode.get(i).getMethodType() + "\n  Order: " + ode.get(i).getMethodOrder());
			
			System.out.println ("");
			
			for (int j = 0; j < ode.get(i).getDiff().getYk().length; j++) {
				
				System.out.println ("y[" + j + "] = " + ode.get(i).getDiff().getYk()[j]);
				
			}
			
			System.out.println ("	- Error Perc Avg: " + ode.get(i).getDiff().getErrorsPercAvg());		
			System.out.println ("	- Error Perc Var: " + ode.get(i).getDiff().getErrorsPercVar());			
			System.out.println ("	- Error Perc SD: " + ode.get(i).getDiff().getErrorsPercSd());
			System.out.println ("\n\n");
			
		}	
		
			
	}

}
