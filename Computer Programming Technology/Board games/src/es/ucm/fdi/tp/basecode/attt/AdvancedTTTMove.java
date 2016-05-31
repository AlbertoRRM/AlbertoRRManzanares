package es.ucm.fdi.tp.basecode.attt;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import es.ucm.fdi.tp.basecode.bgame.model.Board;
import es.ucm.fdi.tp.basecode.bgame.model.GameError;
import es.ucm.fdi.tp.basecode.bgame.model.GameMove;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;
import es.ucm.fdi.tp.basecode.connectn.ConnectNMove;

/**
 * A class representing a move for Advance Tic-Tac-Toe. It is similar to
 * {@link ConnectNMove}, but it takes the piece-count into account. Note that it
 * inherits the fromString capabilities from ConnectNMove.
 * 
 * <p>
 * Clase que representa un movimiento de Tic-Tac-Toe avanzado. Es similar a
 * {@link ConnectNMove}, pero tiene en cuenta el contador de fichas. Observa que
 * hereda de ConnectNMove el metodo formString.
 */

public class AdvancedTTTMove extends ConnectNMove {

	private static final long serialVersionUID = 1L;

	private int srcRow;
	private int srcCol;

	private static Pattern simpleMove = Pattern.compile("([0-3]) ([0-3])");
	private static Pattern advancedMove = Pattern.compile("([0-3]) ([0-3]) > ([0-3]) ([0-3])");

	public AdvancedTTTMove() {
	}

	/**
	 * Construct a move that places a piece of type {@code p} at position (
	 * {@code row},{@code col}).
	 * 
	 * <p>
	 * Construye un movimiento que coloca una ficha de tipo {@code p} en la
	 * posicion ({@code row},{@code col}).
	 * 
	 * @param srcRow
	 *            Number of source row. -1 if not applicable.
	 *            <p>
	 *            Numero de fila origen. -1 si no applicable.
	 * @param srcCol
	 *            Number of column. -1 if not applicable.
	 *            <p>
	 *            Numero de columna origen. -1 si no applicable.
	 * @param row
	 *            Number of row.
	 *            <p>
	 *            Numero de fila.
	 * @param col
	 *            Number of column.
	 *            <p>
	 *            Numero de columna.
	 * @param p
	 *            A piece to place at position ({@code row},{@code col}).
	 *            <p>
	 *            Ficha que se debe colocar en la posicion ({@code row},
	 *            {@code col}).
	 */
	public AdvancedTTTMove(int srcRow, int srcCol, int row, int col, Piece p) {
		super(row, col, p);
		this.srcRow = srcRow;
		this.srcCol = srcCol;
	}

	@Override
	public void execute(Board board, List<Piece> pieces) {
		Piece p = getPiece();

		if (board.getPieceCount(p) == 0 && p.equals(board.getPosition(srcRow, srcCol)) && (srcRow != row || srcCol != col)
				&& board.getPosition(row, col) == null) {
			board.setPosition(srcRow, srcCol, null);
			board.setPosition(row, col, p);
		} else if (board.getPieceCount(p) > 0 && board.getPosition(row, col) == null) {
			board.setPosition(row, col, p);
			board.setPieceCount(p, board.getPieceCount(p) - 1);
		} else {
			throw new GameError("Not a valid move: " + this);
		}
	}

	/**
	 * This move can be constructed from a string of the form "row SPACE col"
	 * where row and col are integers representing a position, or
	 * "row SPACE col > row SPACE col" to indicate a piece changing position.
	 *
	 * <p>
	 * Se puede construir un movimiento desde un string de la forma
	 * "row SPACE col" donde row y col son enteros que representan una casilla.
	 * o "row SPACE col > row SPACE col" para indicar un movimiento de ficha
	 * existente.
	 */
	@Override
	public GameMove fromString(Piece p, String str) {
		Matcher m = simpleMove.matcher(str);
		if (m.find()) {
			return new AdvancedTTTMove(-1, -1, Integer.parseInt(m.group(1)), Integer.parseInt(m.group(2)), p);
		} else {
			m = advancedMove.matcher(str);
			if (m.matches()) {
				return new AdvancedTTTMove(Integer.parseInt(m.group(1)), Integer.parseInt(m.group(2)),
						Integer.parseInt(m.group(3)), Integer.parseInt(m.group(4)), p);
			} else {
				throw new GameError("Not a valid AdvancedTTT move: '" + str + "'");
			}
		}
	}

	@Override
	protected GameMove createMove(int row, int col, Piece p) {
		// we do not use the parent's fromString method,
		// so this should never be called
		throw new UnsupportedOperationException();
	}

	@Override
	public String help() {
		return "'row column', to place a piece at the corresponding position." + "\n"
				+ "  'sRow sCol dRow dCol', to move the piece at (sRow,sCol) to (dRow,dCol).";
	}

	@Override
	public String toString() {
		if (srcRow == -1) {
			return super.toString();
		} else {
			return super.toString() + " from (" + srcRow + "," + srcCol + ")";
		}
	}
}
