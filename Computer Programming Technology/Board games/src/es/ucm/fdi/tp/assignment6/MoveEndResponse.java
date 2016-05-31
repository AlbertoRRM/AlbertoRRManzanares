package es.ucm.fdi.tp.assignment6;

import es.ucm.fdi.tp.basecode.bgame.model.Board;
import es.ucm.fdi.tp.basecode.bgame.model.GameObserver;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;

public class MoveEndResponse implements Response {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Board board;
	Piece turn;
	Boolean success;

	/** 
	 * Constructor of the response sent
	 * @param board
	 * @param turn
	 * @param success
	 */
	public MoveEndResponse(Board board, Piece turn, Boolean success) {
		this.board = board;
		this.turn = turn;
		this.success = success;
	}

	@Override
	public void run(GameObserver o) {
		o.onMoveEnd(board, turn, success);
	}

}
