package Exceptions;

public class OccupiedPositionException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public OccupiedPositionException() {
        // TODO Auto-generated constructor stub
    }

    public OccupiedPositionException(String message) {
        super(message);
        // TODO Auto-generated constructor stub
    }

    public OccupiedPositionException(Throwable cause) {
        super(cause);
        // TODO Auto-generated constructor stub
    }

    public OccupiedPositionException(String message, Throwable cause) {
        super(message, cause);
        // TODO Auto-generated constructor stub
    }
}
