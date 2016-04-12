package Exceptions;

public class MissingCellException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MissingCellException() {
        // TODO Auto-generated constructor stub
    }

    public MissingCellException(String message) {
        super(message);
        // TODO Auto-generated constructor stub
    }

    public MissingCellException(Throwable cause) {
        super(cause);
        // TODO Auto-generated constructor stub
    }

    public MissingCellException(String message, Throwable cause) {
        super(message, cause);
        // TODO Auto-generated constructor stub
    }
}
