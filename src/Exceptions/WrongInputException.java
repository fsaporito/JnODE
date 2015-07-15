package Exceptions;

public class WrongInputException extends Exception {


	private static final long serialVersionUID = 1L;

	public WrongInputException() {
		// TODO Auto-generated constructor stub
	}

	public WrongInputException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public WrongInputException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public WrongInputException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public WrongInputException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

}
