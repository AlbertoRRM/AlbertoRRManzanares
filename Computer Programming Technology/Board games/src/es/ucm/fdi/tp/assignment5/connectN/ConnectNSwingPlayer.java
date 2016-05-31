package es.ucm.fdi.tp.assignment5.connectN;

import java.util.List;

import es.ucm.fdi.tp.basecode.bgame.control.Player;
import es.ucm.fdi.tp.basecode.bgame.model.Board;
import es.ucm.fdi.tp.basecode.bgame.model.GameMove;
import es.ucm.fdi.tp.basecode.bgame.model.GameRules;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;
import es.ucm.fdi.tp.basecode.connectn.ConnectNMove;

@SuppressWarnings("serial")
public class ConnectNSwingPlayer extends Player {

	private int row, col;

	@Override
	public GameMove requestMove(Piece p, Board board, List<Piece> pieces, GameRules rules) {
		return new ConnectNMove(row, col, p);
	}

	/**
	 * Set the given positions to assign them to our move attributes.
	 * 
	 * @param row
	 *            is the given row.
	 * @param col
	 *            is the given column.
	 */
	public void setMove(int row, int col) {
		this.row = row;
		this.col = col;
	}

}