package es.ucm.fdi.tp.basecode.bgame.model;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * An abstract board that implements the piece-count mechanism of the interface
 * {@link Board}.
 *
 * <p>
 * Tablero abstracto que implementa un mecanismo contador de fichas del interfaz
 * {@link Board} usando un {@link HashMap}.
 *
 */
public abstract class BasicBoard implements Board {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * A {@link Map} to store the piece-count.
	 * <p>
	 * {@link Map} para almacenar los contadores de fichas.
	 */
	private Map<Piece, Integer> pieceCount;

	/**
	 * The default constructor. Should be called by subclasses to initialize the
	 * piece-count table.
	 * 
	 * <p>
	 * Constructor por defecto. Debe ser llamado desde las subclases para
	 * inicializar la tabla de contadores de fichas.
	 */
	public BasicBoard() {
		this.pieceCount = new HashMap<Piece, Integer>();
	}

	/**
	 * Copies the content of {@link BasicBoard#pieceCount} to the board
	 * referenced by the parameter {@code board}. This is useful for
	 * implementing {@link Board#copy()} in a way that a class first calls
	 * {@code copyTo} of the super class, and then copies its own data.
	 * 
	 * <p>
	 * Copia el contenido de {@link BasicBoard#pieceCount} al tablero
	 * referenciado por el parametro {@code board}. Es utili para implementar
	 * {@link Board#copy()} de forma que una subclase de esta clase primero
	 * llame a {@code copyTo} de la superclase y despues copie sus propios
	 * datos.
	 * 
	 * @param board
	 *            The board to which we should copy the data.
	 *            <p>
	 *            Tablero al que se deben copiar los datos.
	 */
	protected void copyTo(BasicBoard board) {
		board.pieceCount = new HashMap<Piece, Integer>(pieceCount);
	}

	@Override
	public void setPieceCount(Piece p, Integer n) {
		if (n == null)
			pieceCount.remove(p); // if the pieces count is null we remove it!
		else
			pieceCount.put(p, n); // otherwise we update the table
	}

	@Override
	public Integer getPieceCount(Piece p) {
		return pieceCount.get(p); // if there is no piece-count the HashMap will
									// return null
	}

}
