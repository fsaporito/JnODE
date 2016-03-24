package MathNum;

import java.util.ArrayList;

import Exceptions.WrongCalculationException;
import Exceptions.WrongExpressionException;
import Exceptions.WrongInputException;
import MathExpr.MathExpr;
import MathToken.MathTokenSymbol;
import MatrixMathExpr.Matrix;

public class Jacobian {
	
	// Return The Jacobian Matrix Of The Function
	public static Matrix JacobianMatrix (ArrayList<MathExpr> function, ArrayList<MathTokenSymbol> symbols) throws WrongInputException, WrongExpressionException, WrongCalculationException {
		
		if (function == null) {
			
			throw new WrongInputException ("JacobianMatrix() - Null Input Function!!!");
			
		}
		
		if (function.isEmpty()) {
			
			throw new WrongInputException ("JacobianMatrix() - Empty Input Function !!!");
			
		}
		
		for (int i = 0; i < function.size(); i++) {
			
			if (function.get(i) == null) {
			
				throw new WrongInputException ("JacobianMatrix() - Null Input Function Element: " + i + " !!!");
				
			}
			
		}
		
		if (symbols == null) {
			
			throw new WrongInputException ("JacobianMatrix() - Null Input Symbols!!!");
			
		}
		
		if (symbols.isEmpty()) {
			
			throw new WrongInputException ("JacobianMatrix() - Empty Input Symbols!!!");
			
		}
		
		for (int i = 0; i < symbols.size(); i++) {
			
			if (symbols.get(i) == null) {
			
				throw new WrongInputException ("JacobianMatrix() - Null Input Symbols Element: " + i + " !!!");
				
			}
			
		}
		
		ArrayList<MathExpr> jacobianEl = new ArrayList<MathExpr>(function.size());
		
		for (int i = 0; i < function.size(); i++) {
			
			for (int j = 0; j < symbols.size(); j++) {
			
				jacobianEl.add(function.get(i).derive(symbols.get(j)));
				
			}
						
		}
		
		// Row Number: function component number
		// Column Number: symbols number
		Matrix Jacobian = new Matrix (jacobianEl, function.size(), symbols.size());
		
		return Jacobian;
		
	}
	
	
	// Return The Jacobian Determinant Of The Function
	public static MathExpr JacobiaDet (ArrayList<MathExpr> function, ArrayList<MathTokenSymbol> symbols) throws WrongExpressionException, WrongCalculationException, WrongInputException {
		
		return Jacobian.JacobianMatrix(function,symbols).det();
					
	}
	
	// Return The Jacobian Determinant Of The Jacobian Matrix
	public static MathExpr JacobiaDet (Matrix Jacobian) throws WrongInputException, WrongExpressionException, WrongCalculationException {
		
		if (Jacobian == null) {
			
			throw new WrongInputException ("JacobianDet() - Null Input!!!");
			
		}
		
		if (Jacobian.getDet() == null) {
			
			return Jacobian.det();
			
		} else {		
		
			return Jacobian.getDet();
			
		}				
				
	}

}
