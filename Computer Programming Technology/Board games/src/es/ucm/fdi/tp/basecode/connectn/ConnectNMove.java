package es.ucm.fdi.tp.basecode.connectn;

import java.util.List;

import es.ucm.fdi.tp.basecode.bgame.model.Board;
import es.ucm.fdi.tp.basecode.bgame.model.GameError;
import es.ucm.fdi.tp.basecode.bgame.model.GameMove;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;

/**
 * A Class representing a move for ConnectN.
 * 
 * <p>
 * Clase para representar un movimiento del juego conecta-n.
 * 
 */
public class ConnectNMove extends GameMove {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The row where to place the piece return by {@link GameMove#getPiece()}.
	 * <p>
	 * Fila en la que se coloca la ficha devuelta por
	 * {@link GameMove#getPiece()}.
	 */
	protected int row;

	/**
	 * The column where to place the piece return by {@link GameMove#getPiece()}
	 * .
	 * <p>
	 * Columna en la que se coloca la ficha devuelta por
	 * {@link GameMove#getPiece()}.
	 */
	protected int col;

	/**
	 * This constructor should be used ONLY to get an instance of
	 * {@link ConnectNMove} to generate game moves from strings by calling
	 * {@link #fromString(String)}
	 * 
	 * <p>
	 * Solo se debe usar este constructor para obtener objetos de
	 * {@link ConnectNMove} para generar movimientos a partir de strings usando
	 * el metodo {@link #fromString(String)}
	 * 
	 */

	public ConnectNMove() {
	}

	/**
	 * Constructs a move for placing a piece of the type referenced by {@code p}
	 * at position ({@code row},{@code col}).
	 * 
	 * <p>
	 * Construye un movimiento para colocar una ficha del tipo referenciado por
	 * {@code p} en la posicion ({@code row},{@code col}).
	 * 
	 * @param row
	 *            Number of row.
	 *            <p>
	 *            Numero de fila.
	 * @param col
	 *            Number of column.
	 *            <p>
	 *            Numero de columna.
	 * @param p
	 *            A piece to be place at ({@code row},{@code col}).
	 *            <p>
	 *            Ficha a colocar en ({@code row},{@code col}).
	 */
	public ConnectNMove(int row, int col, Piece p) {
		super(p);
		this.row = row;
		this.col = col;
	}

	@Override
	public void execute(Board board, List<Piece> pieces) {
		if (board.getPosition(row, col) == null) {
			board.setPosition(row, col, getPiece());
		} else {
			throw new GameError("position (" + row + "," + col + ") is already occupied!");
		}
	}

	/**
	 * This move can be constructed from a string of the form "row SPACE col"
	 * where row and col are integers representing a position.
	 * 
	 * <p>
	 * Se puede construir un movimiento desde un string de la forma
	 * "row SPACE col" donde row y col son enteros que representan una casilla.
	 */
	@Override
	public GameMove fromString(Piece p, String str) {
		String[] words = str.split(" ");
		if (words.length != 2) {
			return null;
		}

		try {
			int row, col;
			row = Integer.parseInt(words[0]);
			col = Integer.parseInt(words[1]);
			return createMove(row, col, p);
		} catch (NumberFormatException e) {
			return null;
		}

	}

	/**
	 * Creates a move that is called from {@link #fromString(Piece, String)}.
	 * Separating it from that method allows us to use this class for other
	 * similar games by overriding this method.
	 * 
	 * <p>
	 * Crea un nuevo movimiento con la misma ficha utilizada en el movimiento
	 * actual. Llamado desde {@link #fromString(Piece, String)}; se separa este
	 * metodo del anterior para permitir utilizar esta clase para otros juegos
	 * similares sobrescribiendo este metodo.
	 * 
	 * @param row
	 *            Row of the move being created.
	 *            <p>
	 *            Fila del nuevo movimiento.
	 * 
	 * @param col
	 *            Column of the move being created.
	 *            <p>
	 *            Columna del nuevo movimiento.
	 */
	protected GameMove createMove(int row, int col, Piece p) {
		return new ConnectNMove(row, col, p);
	}

	@Override
	public String help() {
		return "'row column', to place a piece at the corresponding position.";
	}

	@Override
	public String toString() {
		if (getPiece() == null) {
			return help();
		} else {
			return "Place a piece '" + getPiece() + "' at (" + row + "," + col + ")";
		}
	}
}
