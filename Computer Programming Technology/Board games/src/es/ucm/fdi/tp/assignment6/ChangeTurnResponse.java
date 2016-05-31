package es.ucm.fdi.tp.assignment6;

import es.ucm.fdi.tp.basecode.bgame.model.Board;
import es.ucm.fdi.tp.basecode.bgame.model.GameObserver;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;

@SuppressWarnings("serial")
public class ChangeTurnResponse implements Response {

	Board board;
	Piece turn;

	/**
	 * Constructor of the response sent
	 * 
	 * @param board
	 *            is the current board.
	 * @param turn
	 *            is the current piece.
	 */
	public ChangeTurnResponse(Board board, Piece turn) {
		this.board = board;
		this.turn = turn;
	}

	@Override
	public void run(GameObserver o) {
		o.onChangeTurn(board, turn);
	}

}
