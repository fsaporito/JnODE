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
				strFun += diff.getFunc().deSym(t, timeInterval[stepTmp-1]);
				
				// ))
				strFun += "))";				        		
						
				// y[i+1] = y[i-1] + h*f(t[i-1], y)
				yk[stepTmp] = MathNum.AlgebraicEquationEval.evalAlgebraicZero(strFun, y, yk[stepTmp-1]);
				
				// Step Is 2 3 4 5 6: Exit For Loop
				if (this.s >= 2) { 
					
					Sless2 = false; 
					
				} 
				
				// StepTmp +1
				stepTmp++;
				
			}					
			
			// Two Step
			while ( (stepTmp < stepNumber) && Sless3 ) {	
				
				strFun = "";
				
				// y - step * (
				strFun = y.getValue() + " - " + yk[stepTmp-1] + " - (" + step + " * (";
				
				// coeff1 = y[n-1]
				strFun += "( 0.5 * " + diff.getFunc().deSym(t, timeInterval[stepTmp-1]) + " ) " + " + ";
				
				// coeff2 = (1/2) * f(t[i-1], y[i-1])
				strFun +=  "( 0.5 * " + 
							diff.getFunc().deSym(t, timeInterval[stepTmp-1]).deSym(y, yk[stepTmp-1]) + " ) ";
				
				// ))
				strFun += "))";				        		
							
				
				// y[i+1] = y[i-1] + h*CoeffSum
				// coeff1 = (1/2) * f(t[i], y)
				// coeff2 = (1/2) * f(t[i-1], y[i-1])
				yk[stepTmp] = MathNum.AlgebraicEquationEval.evalAlgebraicZero(strFun, y, yk[stepTmp-1]);
				
				// Step Is 3 4 5 6: Exit For Loop
				if (this.s >= 3) { 
					
					Sless3 = false; 
					
				} 
				
				// StepTmp +1
				stepTmp++;
				
			}
			
			// Three Step
			while ( (stepTmp < stepNumber) && Sless4 ) {
				
				strFun = "";
				
				// y - step * (
				strFun = y.getValue() + " - " + yk[stepTmp-1] + " - (" + step + " * (";
				
				// coeff1 = (5/12) * f(t[i], y)
				strFun += "( " + ( (double) 5/12) + " * " + diff.getFunc().deSym(t, timeInterval[stepTmp-1]) + " ) "+ " + ";
				
				// coeff2 = (2/3) * f(t[i-1], y[i-1])
				strFun += "( " + ( (double) 2/3) + " * " +
							diff.getFunc().deSym(t, timeInterval[stepTmp-1]).deSym(y, yk[stepTmp-1]) + " ) "
							+ " + ";
				
				// coeff3 = (-1/12) * f(t[i-2], y[i-2])
				strFun += "( " + ( (double) -1/12) + " * " +
							diff.getFunc().deSym(t, timeInterval[stepTmp-2]).deSym(y, yk[stepTmp-2]) + " ) ";
				
				// ))
				strFun += "))";				        		
										
				
				// y[i+1] = y[i-1] + h*CoeffSum
				// coeff1 = (5/12) * f(t[i], y)
				// coeff2 = (2/3) * f(t[i-1], y[i-1])
				// coeff3 = (-1/12) * f(t[i-2], y[i-2])
				yk[stepTmp] = MathNum.AlgebraicEquationEval.evalAlgebraicZero(strFun, y, yk[stepTmp-1]);
				
				// Step Is 4 5 6: Exit For Loop
				if (this.s >= 4) { 
					
					Sless4 = false; 
					
				} 
				
				// StepTmp +1
				stepTmp++;
				
			}
	
			// Four Step
			while ( (stepTmp < stepNumber) && Sless5 ) {
				
				strFun = "";
				
				// y - step * (
				strFun = y.getValue() + " - " + yk[stepTmp-1] + " - (" + step + " * (";
				
				// coeff1 = (3/8) * f(t[i], y)
				strFun += "( " + ( (double) 3/12) + " * " + diff.getFunc().deSym(t, timeInterval[stepTmp-1])  + " ) " + " + ";
				
				// coeff2 = (19/24) * f(t[i-1], y[i-1])
				strFun +=  "( " + ( (double) 19/24) + " * " +
							diff.getFunc().deSym(t, timeInterval[stepTmp-1]).deSym(y, yk[stepTmp-1])  + " ) "
							+ " + ";
				
				// coeff3 = (-5/24) * f(t[i-2], y[i-2])
				strFun += "( " + ( (double) -5/12) + " * " +
							diff.getFunc().deSym(t, timeInterval[stepTmp-2]).deSym(y, yk[stepTmp-2])  + " ) "
							+ " + ";
				
				// coeff4 = (1/24) * f(t[i-3], y[i-3])
				strFun +=  "( " + ( (double) 1/24) + " * " +
							diff.getFunc().deSym(t, timeInterval[stepTmp-3]).deSym(y, yk[stepTmp-3])  + " ) ";
				
				// ))
				strFun += "))";				        		
							
						
				// y[i+1] = y[i-1] + h*CoeffSum
				// coeff1 = (3/8) * f(t[i], y)
				// coeff2 = (19/24) * f(t[i-1], y[i-1])
				// coeff3 = (-5/24) * f(t[i-2], y[i-2])
				// coeff4 = (1/24) * f(t[i-3], y[i-3])
				yk[stepTmp] = MathNum.AlgebraicEquationEval.evalAlgebraicZero(strFun, y, yk[stepTmp-1]);
				
				// Step Is 5 6: Exit For Loop
				if (this.s >= 5) { 
					
					Sless5 = false; 
					
				}
				
				// StepTmp +1
				stepTmp++;
							
			}
			
			// Five Step
			while ( (stepTmp < stepNumber) && Sless6 ) {
							
				strFun = "";
							
				// y - step * (
				strFun = y.getValue() + " - " + yk[stepTmp-1] + " - (" + step + " * (";
							
				// coeff1 = (3/8) * f(t[i], y)
				strFun += "( " + ( (double) 3/12) + " * " + diff.getFunc().deSym(t, timeInterval[stepTmp-1])  + " ) " + " + ";
					
				// coeff2 = (19/24) * f(t[i-1], y[i-1])
				strFun +=  "( " + ( (double) 19/24) + " * " +
							diff.getFunc().deSym(t, timeInterval[stepTmp-1]).deSym(y, yk[stepTmp-1])  + " ) "
							+ " + ";
							
				// coeff3 = (-5/24) * f(t[i-2], y[i-2])
				strFun += "( " + ( (double) -5/12) + " * " +
							diff.getFunc().deSym(t, timeInterval[stepTmp-2]).deSym(y, yk[stepTmp-2])  + " ) "
							+ " + ";
							
				// coeff4 = (1/24) * f(t[i-3], y[i-3])
				strFun +=  "( " + ( (double) 1/24) + " * " +
							diff.getFunc().deSym(t, timeInterval[stepTmp-3]).deSym(y, yk[stepTmp-3])  + " ) ";
							
				// ))
				strFun += "))";				        		
										
									
				// y[i+1] = y[i-1] + h*CoeffSum
				// coeff1 = (3/8) * f(t[i], y)
				// coeff2 = (19/24) * f(t[i-1], y[i-1])
				// coeff3 = (-5/24) * f(t[i-2], y[i-2])
				// coeff4 = (1/24) * f(t[i-3], y[i-3])
				yk[stepTmp] = MathNum.AlgebraicEquationEval.evalAlgebraicZero(strFun, y, yk[stepTmp-1]);
							
				// Step Is 6: Exit For Loop
				if (this.s >= 6) { 
								
					Sless6 = false; 
								
				}
							
				// StepTmp +1
				stepTmp++;
										
			}
	
			// Six Step
			while ( stepTmp < stepNumber ) {
				
				strFun = "";
				
				// y - step * (
				strFun = y.getValue() + " - " + yk[stepTmp-1] + " - (" + step + " * (";
				
				// coeff1 = (251/720) * f(t[i], y[i])
				strFun += "( " + ( (double) 251/720) + " * " + diff.getFunc().deSym(t, timeInterval[stepTmp-1])  + " ) " + " + ";
				
				// coeff2 = (646/720) * f(t[i-1], y[i-1])
				strFun +=  "( " + ( (double) 646/720) + " * " +
							diff.getFunc().deSym(t, timeInterval[stepTmp-1]).deSym(y, yk[stepTmp-1])  + " ) "
							+ " + ";
				
				// coeff3 = (-264/720) * f(t[i-2],y[i-2])
				strFun +=  "( " + ( (double) -264/720) + " * " +
							diff.getFunc().deSym(t, timeInterval[stepTmp-2]).deSym(y, yk[stepTmp-2])  + " ) "
							+ " + ";
				
				// coeff4 = (106/720) * f(t[i-3], y[i-3])
				strFun += "( " +  ( (double) 106/720) + " * " +
							diff.getFunc().deSym(t, timeInterval[stepTmp-3]).deSym(y, yk[stepTmp-3])  + " ) "
							+ " + ";
				
				// coeff5 = (-19/720) * f(t[i-4], y[i-4])
				strFun +=  "( " + ( (double) -19/720) + " * " +
							diff.getFunc().deSym(t, timeInterval[stepTmp-3]).deSym(y, yk[stepTmp-3])  + " ) ";
				
				// ))
				strFun += "))";	

				// y[i+1] = y[i-1] + h*CoeffSum				
				// coeff1 = (251/720) * f(t[i], y[i])
				// coeff2 = (646/720) * f(t[i-1], y[i-1])
				// coeff3 = (-264/720) * f(t[i-2], y[i-2])
				// coeff4 = (106/720) * f(t[i-3], y[i-3])
				// coeff5 = (-19/720) * f(t[i-4], y[i-4])
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

