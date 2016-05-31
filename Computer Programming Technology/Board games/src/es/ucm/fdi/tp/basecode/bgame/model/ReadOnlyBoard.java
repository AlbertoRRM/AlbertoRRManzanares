package es.ucm.fdi.tp.basecode.bgame.model;

/**
 * A read only version of a {@link Board}. It basically encapsulates a board
 * inside and throws an {@link UnsopportedOperationException} on methods that
 * attempt to modify the board. Calls that do not modify the board (including
 * {@link ReadOnlyBoard#copy()}) are delegated to the encapsulated board.
 * 
 * <p>
 * Una version de solo lectura de {@link Board}. Encapsula un tablero y lanza
 * una excepción {@link UnsopportedOperationException} en los metodos que
 * intentan modificar el tablero. Las llamadas que no modifican el tablero
 * (incluida {@link ReadOnlyBoard#copy()} se delegan al tablero interno.
 */

public class ReadOnlyBoard implements Board {

	private static final long serialVersionUID = 1L;

	/**
	 * The original encapsulated board.
	 * 
	 * <p>
	 * El tablero de origen
	 */
	private Board board;

	/**
	 * Creates a read only version of {@code board}.
	 * 
	 * <p>
	 * Crea una version de solo lectura de {@code board}.
	 * 
	 * @param board
	 */
	public ReadOnlyBoard(Board board) {
		this.board = board;
	}

	/**
	 * Throws {@link UnsupportedOperationException} to indicated that modifying
	 * the board is not supported.
	 * 
	 * <p>
	 * Lanza {@link UnsupportedOperationException} para indicar que la
	 * modificacion del tablero no esta permitida.
	 */
	private void error() {
		throw new UnsupportedOperationException("Cannot modify a readonly board");
	}

	@Override
	public Integer getPieceCount(Piece p) {
		return board.getPieceCount(p);
	}

	@Override
	public Board copy() {
		return board.copy();
	}

	@Override
	public int getRows() {
		return board.getRows();
	}

	@Override
	public int getCols() {
		return board.getCols();
	}

	@Override
	public Piece getPosition(int row, int col) {
		return board.getPosition(row, col);
	}

	@Override
	public boolean isFull() {
		return board.isFull();
	}

	@Override
	public boolean isEmpty() {
		return board.isEmpty();
	}

	@Override
	public String toString() {
		return board.toString();
	}

	/**
	 * No supported in a read only board. It throws
	 * {@link UnsupportedOperationException}
	 * 
	 * <p>
	 * No permitido en un tablero de solo lectura. Lanza
	 * {@link UnsupportedOperationException}
	 */
	@Override
	public void setPosition(int row, int col, Piece p) {
		error();
	}

	/**
	 * No supported in a read only board. It throws
	 * {@link UnsupportedOperationException}
	 * 
	 * <p>
	 * No permitido en un tablero de solo lectura. Lanza
	 * {@link UnsupportedOperationException}
	 */
	@Override
	public void setPieceCount(Piece p, Integer n) {
		error();
	}
}
