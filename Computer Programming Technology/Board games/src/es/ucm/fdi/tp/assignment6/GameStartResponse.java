package es.ucm.fdi.tp.assignment6;

import java.util.List;

import es.ucm.fdi.tp.basecode.bgame.model.Board;
import es.ucm.fdi.tp.basecode.bgame.model.GameObserver;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;

public class GameStartResponse implements Response {	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Board board;
	String gameDesc;
	List<Piece> pieces;
	Piece turn;
	
	/**
	 * Constructor of the response sent
	 * @param board
	 * @param gameDesc
	 * @param pieces
	 * @param turn
	 */
	public GameStartResponse(Board board, String gameDesc, List<Piece> pieces, Piece turn) {
		this.board = board;
		this.gameDesc = gameDesc;
		this.pieces = pieces;
		this.turn = turn;
	}

	public void run(GameObserver o) {
		o.onGameStart(board, gameDesc, pieces, turn);
	}
	
}
