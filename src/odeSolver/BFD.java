package odeSolver;


import Exceptions.WrongCalculationException;
import Exceptions.WrongExpressionException;
import Exceptions.WrongInputException;
import MathToken.MathTokenSymbol;


public class BFD extends LinearMultistep {

	public BFD (DifferentialEquation diff, int s) throws WrongInputException {
		
		super(diff, "BFD", "implicit", ""+s, s);
		
		if (s < 1 || s > 6) {
			
			throw new WrongInputException ("BFD- s Must Be Between 1 And 6!!!");
			
		}
		
	}

	public BFD (DifferentialEquation diff) throws WrongInputException {
		
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
			String strFun = new String();
			
			// Boolean Values Used To Determine A Flag
			// Used To Approximate The Previous Values
			// With The Best Possible Method
			boolean Sless2 = true; // Step Is 1
			boolean Sless3 = true; // Step Is 1 2
			boolean Sless4 = true; // Step Is 1 2 3 		
			boolean Sless5 = true; // Step Is 1 2 3 4
			boolean Sless6 = true; // Step Is 1 2 3 4 5
			
			
			// Euler Implicit Method: One Step
			while ((stepTmp < stepNumber) && Sless2) {
				
				strFun = "";
				
				// y - step * (
				strFun = y.getValue() + " - " + yk[stepTmp-1] + " - (" + step + " * (";
				
				// f(t[i-1], y)
				strFun += diff.getFunc().substituteSymbol(t, timeInterval[stepTmp-1]);
				
				// ))
				strFun += "))";
						
				// y[i] = y[i-1] + h*f(t[i-1], y[i] = y)
				yk[stepTmp] = MathNum.AlgebraicEquationEval.evalAlgebraicZero(strFun, y, yk[stepTmp-1]);
				
				// Step Is 2 3 4 5 6 7: Exit For Loop
				if (this.s >= 2) { 
					
					Sless2 = false; 
					
				} 
				
				// StepTmp +1
				stepTmp++;
				
			}					
			
			// Two Step
			while ( (stepTmp < stepNumber) && Sless3 ) {	
				
				strFun = "";
				
				// y - (
				strFun = y.getValue() + " - ( ";
				
				// Ysum1 = (4/3)*y[i-1]
				strFun += "( " + ((double) 4/3) + " * " + yk[stepTmp-1] + " ) + ";
				
				// Ysum2 = (-1/3)*y[i-2]
				strFun += "( " + ((double) -1/3) + " * " + yk[stepTmp-2] + " ) + ";
				
				// f_ = (2/3)*h*f(t[i],y[i]=y)
				strFun += "( " + ((double) 2/3) + " * " + step + " * " + 
						  diff.getFunc().substituteSymbol(t, timeInterval[stepTmp]) + " )";
				
				// ))
				strFun += " )";				  
							
				
				// y[i] = Ysum + f_
				// f_ = (2/3)*h*f(t[i],y[i]=y)
				// Ysum1 = (4/3)*y[i-1]
				// Ysum2 = (-1/3)*y[i-2]
				yk[stepTmp] = MathNum.AlgebraicEquationEval.evalAlgebraicZero(strFun, y, yk[stepTmp-1]);
				
				// Step Is 3 4 5 6 7: Exit For Loop
				if (this.s >= 3) { 
					
					Sless3 = false; 
					
				} 
				
				// StepTmp +1
				stepTmp++;
				
			}
			
			// Three Step
			while ( (stepTmp < stepNumber) && Sless4 ) {
				
				strFun = "";
				
				// y - (
				strFun = y.getValue() + " - ( ";
				
				// Ysum1 = (18/11)*y[i-1]
				strFun += "( " + ((double) 18/11) + " * " + yk[stepTmp-1] + " ) + ";
				
				// Ysum2 = (-9/11)*y[i-2]
				strFun += "( " + ((double) -9/11) + " * " + yk[stepTmp-2] + " ) + ";
				
				// Ysum3 = (2/11)*y[i-3]
				strFun += "( " + ((double) 2/11) + " * " + yk[stepTmp-3] + " ) + ";
				
				// f_ = (6/11)*h*f(t[i], y[i]=y)
				strFun += "( " + ((double) 6/11) + " * " + step + " * " + 
						  diff.getFunc().substituteSymbol(t, timeInterval[stepTmp]) + " )";
				
				// ))
				strFun += " )";				        		
										
				
				// y[i] = Ysum + f_
				// f_ = (6/11)*h*f(t[i], y[i] = y)
				// Ysum1 = (18/11)*y[i-1]
				// Ysum2 = (-9/11)*y[i-2]
				// Ysum3 = (+2/11)*y[i-3]
				yk[stepTmp] = MathNum.AlgebraicEquationEval.evalAlgebraicZero(strFun, y, yk[stepTmp-1]);
				
				// Step Is 4 5 6 7: Exit For Loop
				if (this.s >= 4) { 
					
					Sless4 = false; 
					
				} 
				
				// StepTmp +1
				stepTmp++;
				
			}
	
			// Four Step
			while ( (stepTmp < stepNumber) && Sless5 ) {
				
				strFun = "";
				
				// y - (
				strFun = y.getValue() + " - ( ";
				
				// Ysum1 = (48/25)*y[i-1]
				strFun += "( " + ((double) 48/25) + " * " + yk[stepTmp-1] + " ) + ";
				
				// Ysum2 = (-36/25)*y[i-2]
				strFun += "( " + ((double) -36/25) + " * " + yk[stepTmp-2] + " ) + ";
				
				// Ysum3 = (16/25)*y[i-3]
				strFun += "( " + ((double) 16/25) + " * " + yk[stepTmp-3] + " ) + ";
				
				// Ysum4 = (-3/25)*y[i-4]
				strFun += "( " + ((double) -3/25) + " * " + yk[stepTmp-4] + " ) + ";
				
				// f_ = (12/25)*h*f(t[i], y[i]=y)
				strFun += "( " + ((double) 12/25) + " * " + step + " * " + 
						  diff.getFunc().substituteSymbol(t, timeInterval[stepTmp]) + " )";
				
				// ))
				strFun += " )";		
							
						
				// y[i] = Ysum + f_
				// f_ = (12/25)*h*f(t[i], y[i] = y)
				// Ysum1 = (48/25)*y[i-1]
				// Ysum2 = (-36/35)*y[i-2]
				// Ysum3 = (+16/25)*y[i-3]
				// Ysum4 = (-3/25)*y[i-4]
				yk[stepTmp] = MathNum.AlgebraicEquationEval.evalAlgebraicZero(strFun, y, yk[stepTmp-1]);
				
				// Step Is 5 6 7: Exit For Loop
				if (this.s >= 5) { 
					
					Sless5 = false; 
					
				}
				
				// StepTmp +1
				stepTmp++;
							
			}
			
			// Five Step
			while ( (stepTmp < stepNumber) && Sless6 ) {
							
				strFun = "";
				
				// y - (
				strFun = y.getValue() + " - ( ";
				
				// Ysum1 = (300/137)*y[i-1]
				strFun += "( " + ((double) 300/137) + " * " + yk[stepTmp-1] + " ) + ";
				
				// Ysum2 = (-300/137)*y[i-2]
				strFun += "( " + ((double) -300/137) + " * " + yk[stepTmp-2] + " ) + ";
				
				// Ysum3 = (200/137)*y[i-3]
				strFun += "( " + ((double) 200/137) + " * " + yk[stepTmp-3] + " ) + ";
				
				// Ysum4 = (-75/137)*y[i-4]
				strFun += "( " + ((double) -75/137) + " * " + yk[stepTmp-4] + " ) + ";
				
				// Ysum5 = (12/137)*y[i-5]
				strFun += "( " + ((double) 12/137) + " * " + yk[stepTmp-5] + " ) + ";
				
				// f_ = (60/137)*h*f(t[i], y[i]=y)
				strFun += "( " + ((double) 60/137) + " * " + step + " * " + 
						  diff.getFunc().substituteSymbol(t, timeInterval[stepTmp]) + " )";
				
				// ))
				strFun += " )";			        					        		
										
									
				// y[i] = Ysum + f_
				// f_ = (60/137)*h*f(t[i],y[i])
				// Ysum1 = (300/137)*y[i-1]
				// Ysum2 = (-300/137)*y[i-2]
				// Ysum3 = (200/137)*y[i-3]
				// Ysum4 = (-75/137)*y[i-4]
				// Ysum5 = (12/137)*y[i-5]
				yk[stepTmp] = MathNum.AlgebraicEquationEval.evalAlgebraicZero(strFun, y, yk[stepTmp-1]);
							
				// Step Is 6 7: Exit For Loop
				if (this.s >= 6) { 
								
					Sless6 = false; 
								
				}
							
				// StepTmp +1
				stepTmp++;
										
			}
	
			// Six Step
			while ( stepTmp < stepNumber ) {
				
				strFun = "";
				
				// y - (
				strFun = y.getValue() + " - ( ";
				
				// Ysum1 = (360/147)*y[i-1]
				strFun += "( " + ((double) 360/147) + " * " + yk[stepTmp-1] + " ) + ";
				
				// Ysum2 = (-450/147)*y[i-2]
				strFun += "( " + ((double) -450/147) + " * " + yk[stepTmp-2] + " ) + ";
				
				// Ysum3 = (400/147)*y[i-3]
				strFun += "( " + ((double) 400/147) + " * " + yk[stepTmp-3] + " ) + ";
				
				// Ysum4 = (-225/147)*y[i-4]
				strFun += "( " + ((double) -225/147) + " * " + yk[stepTmp-4] + " ) + ";
				
				// Ysum5 = (72/147)*y[i-5]
				strFun += "( " + ((double) 72/147) + " * " + yk[stepTmp-5] + " ) + ";
				
				// Ysum5 = (-10/147)*y[i-6]
				strFun += "( " + ((double) -10/147) + " * " + yk[stepTmp-6] + " ) + ";
				
				// f_ = (60/147)*h*f(t[i], y[i]=y)
				strFun += "( " + ((double) 60/147) + " * " + step + " * " + 
						  diff.getFunc().substituteSymbol(t, timeInterval[stepTmp]) + " )";
				
				// ))
				strFun += " )";		

				// y[i] = Ysum + f_
				// f_ = (60/147)*h*f(t[i],y[i])
				// Ysum1 = (360/147)*y[i-1]
				// Ysum2 = (-450/147)*y[i-2]
				// Ysum3 = (400/147)*y[i-3]
				// Ysum4 = (-225/147)*y[i-4]
				// Ysum5 = (72/147)*y[i-5]
				// Ysum6 = (-10/147)*y[i-6]
				yk[stepTmp] = MathNum.AlgebraicEquationEval.evalAlgebraicZero(strFun, y, yk[stepTmp-1]);
				
				// StepTmp +1
				stepTmp++;
											
			}
			
		} catch (WrongCalculationException | WrongExpressionException | WrongInputException e) {
			
			e.printStackTrace();
			
		}	
		
		this.diff.setYk(yk);
		
		this.diff.setSolved(true);
		
		return yk;
		
	}
	
	
	

}

