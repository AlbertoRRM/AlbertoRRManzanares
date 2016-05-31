package es.ucm.fdi.tp.assignment5.attt;

import java.util.List;

import es.ucm.fdi.tp.basecode.attt.AdvancedTTTMove;
import es.ucm.fdi.tp.basecode.bgame.control.Player;
import es.ucm.fdi.tp.basecode.bgame.model.Board;
import es.ucm.fdi.tp.basecode.bgame.model.GameMove;
import es.ucm.fdi.tp.basecode.bgame.model.GameRules;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;

@SuppressWarnings("serial")
public class ATTTSwingPlayer extends Player {

	/**
	 * Initial row of the selected piece.
	 */
	private int init_row;

	/**
	 * Initial column of the selected piece.
	 */
	private int init_col;

	/**
	 * Destination row.
	 */
	private int row;

	/**
	 * Destination column.
	 */
	private int col;

	@Override
	public GameMove requestMove(Piece p, Board board, List<Piece> pieces, GameRules rules) {
		return new AdvancedTTTMove(init_row, init_col, row, col, p);
	}

	/**
	 * Set the given positions to assign them to our move attributes.
	 * 
	 * @param init_row
	 *            is the initial row.
	 * @param init_col
	 *            is the initial column.
	 * @param row
	 *            is the destination row.
	 * @param col
	 *            is the destination column.
	 */
	public void setMove(int init_row, int init_col, int row, int col) {
		this.init_row = init_row;
		this.init_col = init_col;
		this.row = row;
		this.col = col;
	}
}
