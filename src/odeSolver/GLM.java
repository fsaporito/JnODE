package odeSolver;

import Exceptions.WrongInputException;

public abstract class GLM extends OdeSolver {

	protected int r;
	protected int s;
	
	public GLM(DifferentialEquation diff, String methodName, String methodType, String methodOrder, int r, int s) throws WrongInputException {
		
		super(diff, methodName, methodType, methodOrder);
		
		if (r >= 0) {
			
			this.r = r;
			
		} else {
			
			throw new WrongInputException ("GLM- r must be non zero!!!");
			
		}
		
		if (s >= 0) {
			
			this.s = s;
			
		} else {
			
			throw new WrongInputException ("GLM- s must be non zero!!!");
			
		}
		
		
		if (r == 0 && s == 0) {
			
			throw new WrongInputException ("GLM- At least one between r and s must be not zero!!!");
			
		}
		
		
	}
	
	
	public GLM(DifferentialEquation diff, String methodName, String methodType, String methodOrder) throws WrongInputException {
		
		this (diff, methodName, methodType, methodOrder, 1, 1);
		
	}

	@Override
	protected double[] solveODE() {
		// TODO Auto-generated method stub
		return null;
	}

}
