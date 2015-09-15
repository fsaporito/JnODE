package odeSolver;

import Exceptions.WrongInputException;

public abstract class LinearMultistep extends GLM {

	public LinearMultistep(DifferentialEquation diff, String methodName, String methodType, String methodOrder, int s) throws WrongInputException {
		
		super(diff, methodName, methodType, methodOrder, 0, s);
		
	}

	public LinearMultistep(DifferentialEquation diff, String methodName, String methodType, String methodOrder) throws WrongInputException {
		
		super(diff, methodName, methodType, methodOrder);
		
	}

}
