package es.ucm.fdi.tp.assignment5.ataxx;

import java.util.List;

import es.ucm.fdi.tp.assignment4.AtaxxMove;
import es.ucm.fdi.tp.basecode.bgame.control.Player;
import es.ucm.fdi.tp.basecode.bgame.model.Board;
import es.ucm.fdi.tp.basecode.bgame.model.GameMove;
import es.ucm.fdi.tp.basecode.bgame.model.GameRules;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;

@SuppressWarnings("serial")
public class AtaxxSwingPlayer extends Player {

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
	private int final_row;

	/**
	 * Destination column.
	 */
	private int final_col;

	@Override
	public GameMove requestMove(Piece p, Board board, List<Piece> pieces, GameRules rules) {
		return new AtaxxMove(init_row, init_col, final_row, final_col, p);
	}

	/**
	 * Set the given positions to assign them to our move attributes.
	 * 
	 * @param init_row
	 *            is the initial row.
	 * @param init_col
	 *            is the initial column.
	 * @param final_row
	 *            is the destination row.
	 * @param final_col
	 *            is the destination column.
	 */
	public void setMove(int init_row, int init_col, int final_row, int final_col) {
		this.init_row = init_row;
		this.init_col = init_col;
		this.final_row = final_row;
		this.final_col = final_col;
	}

}
