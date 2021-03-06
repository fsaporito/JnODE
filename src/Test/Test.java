package Test;


import java.util.ArrayList;

import odeSolver.AdamsBashforth;
import odeSolver.AdamsMoulton;
import odeSolver.BDF;
import odeSolver.DifferentialEquation;
import odeSolver.EulerExplicit;
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
import MathNum.Jacobian;
import MathToken.MathTokenOperand;
import MathToken.MathTokenOperator;
import MathToken.MathTokenSymbol;
import MathToken.Operators;
import Parser.MathParser;


public class Test {


	public static void main(String[] args) throws WrongInputException, WrongExpressionException, WrongCalculationException {

		//Test.test1(false);
		//Test.test2(true);

		MathTokenSymbol xs = new MathTokenSymbol("x");
		MathTokenSymbol ys = new MathTokenSymbol("y");

		MathExpr x = new MathExpr(xs);
		MathExpr y = new MathExpr(ys);

		MathTokenOperator plus = Operators.plus();
		MathTokenOperator mult = Operators.mult();
		MathTokenOperator pow = Operators.pow();

		MathExpr expr1 = new MathExpr (plus, x, (new MathExpr (mult, x, y)));
		MathExpr expr2 = new MathExpr (pow, x, (new MathExpr (new MathTokenOperand("2"))));

		System.out.println (expr1.toString());
		System.out.println (expr2.toString());

		ArrayList<MathExpr> function = new ArrayList<MathExpr>();
		function.add(expr1);
		function.add(expr2);

		ArrayList<MathTokenSymbol> symbols = new ArrayList<MathTokenSymbol>();
		symbols.add(xs);
		symbols.add(ys);

		System.out.println ("\n");

		Jacobian.JacobianMatrix(function, symbols);

	}


	public static void test1 (boolean debug) throws WrongInputException, WrongExpressionException, WrongCalculationException {

		String function = "y";
		MathParser parserFun = new MathParser (function, "infix");
		MathExpr functionExpr = parserFun.getMathExpr();

		String exact = "e^t";
		MathParser parserExact = new MathParser (exact, "infix");
		MathExpr exactExpr = parserExact.getMathExpr();

		double t0 = 0;
		double tmax = 1;
		double step = 0.1;

		Test.test (functionExpr, exactExpr, t0, tmax, step, debug);

	}


	public static void test2 (boolean debug) throws WrongInputException, WrongExpressionException, WrongCalculationException {

		String function = "siny";
		MathParser parserFun = new MathParser (function, "infix");
		MathExpr functionExpr = parserFun.getMathExpr();

		String exact = "cosy";
		MathParser parserExact = new MathParser (exact, "infix");
		MathExpr exactExpr = parserExact.getMathExpr();

		double t0 = 0;
		double tmax = 1;
		double step = 0.1;

		Test.test (functionExpr, exactExpr, t0, tmax, step, debug);

	}


	public static void test (MathExpr functionExpr, MathExpr exactExpr, double t0, double tmax, double step, boolean debug) throws WrongCalculationException, WrongInputException, WrongExpressionException {

		double y0 = exactExpr.evalSymbolic(t0).getOperandDouble();

		DifferentialEquation diff = new DifferentialEquation (exactExpr, functionExpr, t0, y0, step, tmax, new MathTokenSymbol ("t"), new MathTokenSymbol ("y"));

		ArrayList<OdeSolver> ode = new ArrayList<OdeSolver>();

		System.out.println (diff.toString());

		// Euler Explicit
		OdeSolver ee = new EulerExplicit (diff.clone());
		ee.solve();
		ee.errors();
		ode.add(ee);

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

		// AdamsBashforth S = 1
		OdeSolver ab1 = new AdamsBashforth (diff.clone(), 1);
		ab1.solve();
		ab1.errors();
		ode.add(ab1);

		// AdamsBashforth S = 2
		OdeSolver ab2 = new AdamsBashforth (diff.clone(), 2);
		ab2.solve();
		ab2.errors();
		ode.add(ab2);

		// AdamsBashforth S = 3
		OdeSolver ab3 = new AdamsBashforth (diff.clone(), 3);
		ab3.solve();
		ab3.errors();
		ode.add(ab3);

		// AdamsBashforth S = 4
		OdeSolver ab4 = new AdamsBashforth (diff.clone(), 4);
		ab4.solve();
		ab4.errors();
		ode.add(ab4);

		// AdamsBashforth S = 5
		OdeSolver ab5 = new AdamsBashforth (diff.clone(), 5);
		ab5.solve();
		ab5.errors();
		ode.add(ab5);

		// AdamsMoulton S = 0
		OdeSolver am0 = new AdamsMoulton (diff.clone(), 0);
		am0.solve();
		am0.errors();
		ode.add(am0);

		// AdamsMoulton S = 1
		OdeSolver am1 = new AdamsMoulton (diff.clone(), 1);
		am1.solve();
		am1.errors();
		ode.add(am1);

		// AdamsMoulton S = 2
		OdeSolver am2 = new AdamsMoulton (diff.clone(), 2);
		am2.solve();
		am2.errors();
		ode.add(am2);

		// AdamsMoulton S = 3
		OdeSolver am3 = new AdamsMoulton (diff.clone(), 3);
		am3.solve();
		am3.errors();
		ode.add(am3);

		// AdamsMoulton S = 4
		OdeSolver am4 = new AdamsMoulton (diff.clone(), 4);
		am4.solve();
		am4.errors();
		ode.add(am4);

		// BFD S = 1
		OdeSolver bfd1 = new BDF (diff.clone(), 1);
		bfd1.solve();
		bfd1.errors();
		ode.add(bfd1);

		// BFD S = 2
		OdeSolver bfd2 = new BDF (diff.clone(), 2);
		bfd2.solve();
		bfd2.errors();
		ode.add(bfd2);

		// BFD S = 3
		OdeSolver bfd3 = new BDF (diff.clone(), 3);
		bfd3.solve();
		bfd3.errors();
		ode.add(bfd3);

		// BFD S = 4
		OdeSolver bfd4 = new BDF (diff.clone(), 4);
		bfd4.solve();
		bfd4.errors();
		ode.add(bfd4);

		// BFD S = 5
		OdeSolver bfd5 = new BDF (diff.clone(), 5);
		bfd5.solve();
		bfd5.errors();
		ode.add(bfd5);

		// BFD S = 6
		OdeSolver bfd6 = new BDF (diff.clone(), 6);
		bfd6.solve();
		bfd6.errors();
		ode.add(bfd6);

		for (int i = 0; i < ode.size(); i++) {

			System.out.println (ode.get(i).getMethodName() + "  [Type: " + ode.get(i).getMethodType() + "  Order: " + ode.get(i).getMethodOrder() + "]");

			System.out.println ("	- Error Perc Avg: " + ode.get(i).getDiff().getErrorsPercAvg());

			if (debug) {

				for (int j = 0; j < ode.get(i).getDiff().getYk().length; j++) {

					System.out.println ("	  y[" + j + "] = " + ode.get(i).getDiff().getYk()[j]);

				}

			}

		}

		System.out.println ("\n\n");

	}

}
