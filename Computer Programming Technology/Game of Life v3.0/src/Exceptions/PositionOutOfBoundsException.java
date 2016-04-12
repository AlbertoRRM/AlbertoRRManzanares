package Exceptions;

public class PositionOutOfBoundsException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PositionOutOfBoundsException() {
        // TODO Auto-generated constructor stub
    }

    public PositionOutOfBoundsException(String message) {
        super(message);
        // TODO Auto-generated constructor stub
    }

    public PositionOutOfBoundsException(Throwable cause) {
        super(cause);
        // TODO Auto-generated constructor stub
    }

    public PositionOutOfBoundsException(String message, Throwable cause) {
        super(message, cause);
        // TODO Auto-generated constructor stub
    }
}
