package es.ucm.fdi.tp.assignment6;

import es.ucm.fdi.tp.basecode.bgame.model.Board;
import es.ucm.fdi.tp.basecode.bgame.model.Game.State;
import es.ucm.fdi.tp.basecode.bgame.model.GameObserver;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;

public class GameOverResponse implements Response {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Board board;
	State state;
	Piece winner;

	/**
	 * Constructor of the response sent
	 * 
	 * @param board
	 *            is the current board.
	 * @param state
	 *            is the state of the game.
	 * @param winner
	 *            is the winner of the game.
	 */
	public GameOverResponse(Board board, State state, Piece winner) {
		this.board = board;
		this.state = state;
		this.winner = winner;
	}

	@Override
	public void run(GameObserver o) {
		o.onGameOver(board, state, winner);
	}

}
