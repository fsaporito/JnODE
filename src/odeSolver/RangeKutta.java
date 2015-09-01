package odeSolver;

import java.util.Hashtable;

import Exceptions.WrongCalculationException;
import Exceptions.WrongInputException;
import MathToken.MathTokenSymbol;
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
		this.A = new double[r+1][r+1];
		
		for (int i = 0; i < r+1; i++) {
			
			for (int j = 0; j < r+1; j++) {
				
				this.A[i][j] = 0;
				
			}
			
		}
		
		
		// Weights Array Definition
		this.b = new double[r];
		
		for (int i = 0; i < r; i++) {
				
			this.b[i] = 0;
				
		}
		
		
		// Nodes Array Definition
		this.c = new double[r];
		
		for (int i = 0; i < r; i++) {
				
			this.c[i] = 0;
				
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
		
		if (this.getMethodType().equals("explicit")) {
			
			// y[n+1] = y[n] + SUM (i=1..r) {b[i]*k[i]}
			// k[0] = f (t[n], y[n]) # c[0] = 0, a[0][0] = 0
			// k[1] = f (t[n] + c[1]*h, y[n] + h*(a[1][0]*k[0]) 
			// k[2] = f (t[n] + c[2]*h, y[n] + h*(a[2][0]*k[0] + a[2][1]*k[1]) 
			// k[i] = f (t[n] + c[i]*h, y[n] + h*(a[i][0]*k[0] + a[i][1]*k[1] + .. + a[i][i-1]*k[i-1]) 
			
			try {
								
				// sum (bi*ki)
				double kb_sum;
				
				// sum (ai*ki)
				double ka_sum;
				
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
				
					yk[i] = yk[i-1] + step*kb_sum;
				
				}
				
			} catch (WrongCalculationException | WrongInputException e) {
				
				e.printStackTrace();
			
			}
			
		}
		
		this.diff.setSolved(true);
		
		this.diff.setYk(yk);
		
		this.diff.setMethodName(this.methodName);
		
		this.diff.setMethodType(this.methodType);
		
		return yk;
		
	}

}
