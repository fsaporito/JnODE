package odeSolver;

import java.util.Hashtable;

import Exceptions.WrongCalculationException;
import Exceptions.WrongInputException;
import MathToken.MathTokenSymbol;
import Parser.MathEvaluator;

public class AdamsBashforth extends LinearMultistep {

	public AdamsBashforth (DifferentialEquation diff, int s) throws WrongInputException {
		
		super(diff, "AdamsBashforth", "explicit", ""+s, s);
		
		if (s < 1 || s > 5) {
			
			throw new WrongInputException ("AdamsBashforth- s Must Be Between 0 And 5!!!");
			
		}
		
	}

	public AdamsBashforth (DifferentialEquation diff) throws WrongInputException {
		
		this (diff, 1);
		
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
			Hashtable<MathTokenSymbol, Double> hashTab = new Hashtable<MathTokenSymbol, Double>();
			
			MathEvaluator mathEval1; // MathEvaluator For First Function
			double coeff1 = 0.0; // Coefficient Of The First Function
			
			MathEvaluator mathEval2; // MathEvaluator For Second Function
			double coeff2 = 0.0; // Coefficient Of The Secpmd Function
			
			MathEvaluator mathEval3; // MathEvaluator For Third Function
			double coeff3 = 0.0; // Coefficient Of The Third Function
			
			MathEvaluator mathEval4; // MathEvaluator For Fourth Function
			double coeff4 = 0.0; // Coefficient Of The Fourth Function
			
			MathEvaluator mathEval5; // MathEvaluator For Fifth Function
			double coeff5 = 0.0; // Coefficient Of The Fifth Function
			
			// Boolean Values Used To Determine A Flag
			// Used To Approximate The Previous Values
			// With The Best Possible Method
			boolean Sless2 = true; // Step Is 1
			boolean Sless3 = true; // Step Is 1 2
			boolean Sless4 = true; // Step Is 1 2 3			
			boolean Sless5 = true; // Step Is 1 2 3 4
			
			
			// Euler Explicit Method: One Step
			while ((stepTmp < stepNumber) && Sless2) {
				
				// First Step
				// 1
				hashTab.clear();
				hashTab.put(t, timeInterval[stepTmp-1]);
				hashTab.put(y, yk[stepTmp-1]);						
				mathEval1 = new MathEvaluator (diff.getFunc(), hashTab);
				coeff1 = mathEval1.getResult().getOperandDouble();	
						
				// y[i+1] = y[i-1] + h*f(t[i],y[i])
				yk[stepTmp] = yk[stepTmp-1] + step*coeff1;	
				
				// Step Is 2 3 4 5: Exit For Loop
				if (this.s >= 2) { 
					
					Sless2 = false; 
					
				} 
				
				// StepTmp +1
				stepTmp++;
				
			}					
			
			// Two Step
			while ( (stepTmp < stepNumber) && Sless3 ) {	
				
				// First Step
				// -1/2
				hashTab.clear();
				hashTab.put(t, timeInterval[stepTmp-2]);
				hashTab.put(y, yk[stepTmp-2]);											
				mathEval1 = new MathEvaluator (diff.getFunc(), hashTab);
				coeff1 = (-0.5) * mathEval1.getResult().getOperandDouble();	
				
				// Second Step
				// 3/2
				hashTab.clear();
				hashTab.put(t, timeInterval[stepTmp-1]);
				hashTab.put(y, yk[stepTmp-1]);											
				mathEval2 = new MathEvaluator (diff.getFunc(), hashTab);
				coeff2 = ((double) 3/2) * mathEval2.getResult().getOperandDouble();	
						
				// y[i+1] = y[i-1] + h*CoeffSum
				// coeff1 = (-1/2) * f(t[i-1],y[i-1])
				// coeff2 = (3/2) * f(t[i],y[i])
				yk[stepTmp] = yk[stepTmp-1] + step*(coeff1 + coeff2);
				
				// Step Is 3 4 5: Exit For Loop
				if (this.s >= 3) { 
					
					Sless3 = false; 
					
				} 
				
				// StepTmp +1
				stepTmp++;
				
			}
			
			// Three Step
			while ( (stepTmp < stepNumber) && Sless4 ) {
				
				// First Step
				// 5/12
				hashTab.clear();
				hashTab.put(t, timeInterval[stepTmp-3]);
				hashTab.put(y, yk[stepTmp-3]);											
				mathEval1 = new MathEvaluator (diff.getFunc(), hashTab);
				coeff1 = ((double) 5/12) * mathEval1.getResult().getOperandDouble();	
					
				// Second Step
				// -4/3
				hashTab.clear();
				hashTab.put(t, timeInterval[stepTmp-2]);
				hashTab.put(y, yk[stepTmp-2]);											
				mathEval2 = new MathEvaluator (diff.getFunc(), hashTab);
				coeff2 = ((double) -4/3) * mathEval2.getResult().getOperandDouble();	
					
				// Third Step
				// 23/12
				hashTab.clear();
				hashTab.put(t, timeInterval[stepTmp-1]);
				hashTab.put(y, yk[stepTmp-1]);											
				mathEval3 = new MathEvaluator (diff.getFunc(), hashTab);
				coeff3 = ((double) 23/12) * mathEval3.getResult().getOperandDouble();	
					
				// y[i+1] = y[i-1] + h*CoeffSum
				// coeff1 = (5/12) * f(t[i-2],y[i-2])
				// coeff2 = (-4/3) * f(t[i-1],y[i-1])
				// coeff3 = (23/12) * f(t[i],y[i])
				yk[stepTmp] = yk[stepTmp-1] + step*(coeff1 + coeff2 + coeff3);	
				
				// Step Is 4 5: Exit For Loop
				if (this.s >= 4) { 
					
					Sless4 = false; 
					
				} 
				
				// StepTmp +1
				stepTmp++;
				
			}
	
			// Four Step
			while ( (stepTmp < stepNumber) && Sless5 ) {
				
				// First Step
				// -3/8
				hashTab.clear();
				hashTab.put(t, timeInterval[stepTmp-4]);
				hashTab.put(y, yk[stepTmp-4]);											
				mathEval1 = new MathEvaluator (diff.getFunc(), hashTab);
				coeff1 = ((double) -3/8) * mathEval1.getResult().getOperandDouble();	
					
				// Second Step
				// 37/24
				hashTab.clear();
				hashTab.put(t, timeInterval[stepTmp-3]);
				hashTab.put(y, yk[stepTmp-3]);											
				mathEval2 = new MathEvaluator (diff.getFunc(), hashTab);
				coeff2 = ((double) 37/24) * mathEval2.getResult().getOperandDouble();	
				
				// Third Step
				// -59/24
				hashTab.clear();
				hashTab.put(t, timeInterval[stepTmp-2]);
				hashTab.put(y, yk[stepTmp-2]);											
				mathEval3 = new MathEvaluator (diff.getFunc(), hashTab);
				coeff3 = ((double) -59/24) * mathEval3.getResult().getOperandDouble();	
				
				// Fourth Step
				// 55/24
				hashTab.clear();
				hashTab.put(t, timeInterval[stepTmp-1]);
				hashTab.put(y, yk[stepTmp-1]);											
				mathEval4 = new MathEvaluator (diff.getFunc(), hashTab);
				coeff4 = ((double) 55/24) * mathEval4.getResult().getOperandDouble();	
						
				// y[i+1] = y[i-1] + h*CoeffSum
				// coeff1 = (-3/8) * f(t[i-3],y[i-3])
				// coeff2 = (37/24) * f(t[i-2],y[i-2])
				// coeff3 = (-59/24) * f(t[i-1],y[i-1])
				// coeff4 = (55/24) * f(t[i],y[i])
				yk[stepTmp] = yk[stepTmp-1] + step*(coeff1 + coeff2 + coeff3 + coeff4);	
				
				// Step Is 5: Exit For Loop
				if (this.s >= 5) { 
					
					Sless5 = false; 
					
				}
				
				// StepTmp +1
				stepTmp++;
							
			}
	
			// Five Step
			while ( stepTmp < stepNumber ) {
				
				// First Step
				// 251/720
				hashTab.clear();
				hashTab.put(t, timeInterval[stepTmp-5]);
				hashTab.put(y, yk[stepTmp-5]);											
				mathEval1 = new MathEvaluator (diff.getFunc(), hashTab);
				coeff1 = ((double) 251/720) * mathEval1.getResult().getOperandDouble();	
					
				// Second Step
				// -637/360
				hashTab.clear();
				hashTab.put(t, timeInterval[stepTmp-4]);
				hashTab.put(y, yk[stepTmp-4]);											
				mathEval2 = new MathEvaluator (diff.getFunc(), hashTab);
				coeff2 = ((double) -637/360) * mathEval2.getResult().getOperandDouble();	
					
				// Third Step
				// 109/30
				hashTab.clear();
				hashTab.put(t, timeInterval[stepTmp-3]);
				hashTab.put(y, yk[stepTmp-3]);											
				mathEval3 = new MathEvaluator (diff.getFunc(), hashTab);
				coeff3 = ((double) 109/30) * mathEval3.getResult().getOperandDouble();	
				
				// Fourth Step
				// -1387/360
				hashTab.clear();
				hashTab.put(t, timeInterval[stepTmp-2]);
				hashTab.put(y, yk[stepTmp-2]);											
				mathEval4 = new MathEvaluator (diff.getFunc(), hashTab);
				coeff4 = ((double) -1387/360) * mathEval4.getResult().getOperandDouble();	
					
				// Fifth Step
				// 1901/720
				hashTab.clear();
				hashTab.put(t, timeInterval[stepTmp-1]);
				hashTab.put(y, yk[stepTmp-1]);											
				mathEval5 = new MathEvaluator (diff.getFunc(), hashTab);
				coeff5 = ((double) 1901/720) * mathEval5.getResult().getOperandDouble();	

				// y[i+1] = y[i-1] + h*CoeffSum
				// coeff1 = (251/720) * f(t[i-4],y[i-4])
				// coeff2 = (-637/360) * f(t[i-3],y[i-3])
				// coeff3 = (109/30) * f(t[i-2],y[i-2])
				// coeff4 = (-1387/360) * f(t[i-1],y[i-1])
				// coeff5 = (1901/720) * f(t[i],y[i])
				yk[stepTmp] = yk[stepTmp-1] + step*(coeff1 + coeff2 + coeff3 + coeff4 + coeff5);	
				
				// StepTmp +1
				stepTmp++;
											
			}
			
		} catch (WrongCalculationException | WrongInputException e) {
			
			e.printStackTrace();
			
		}	
		
		this.diff.setYk(yk);
		
		this.diff.setSolved(true);
		
		return yk;
		
	}
	
	
	

}
