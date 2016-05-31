package es.ucm.fdi.tp.basecode.bgame.views;

import java.util.List;

import es.ucm.fdi.tp.basecode.bgame.control.Controller;
import es.ucm.fdi.tp.basecode.bgame.model.Board;
import es.ucm.fdi.tp.basecode.bgame.model.GameObserver;
import es.ucm.fdi.tp.basecode.bgame.model.Observable;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;
import es.ucm.fdi.tp.basecode.bgame.model.Game.State;

/**
 * Class implementing a game observer that outputs all the events in the game in
 * the standard console.
 * 
 * <p>
 * Clase que implementa un observador de juegos que escribe todos los eventos
 * del juego en la consola estandar.
 * 
 */
public class GenericConsoleView implements GameObserver {

	/**
	 * The list of pieces involved in the game. It is stored when the game
	 * starts and used when the state is printed.
	 */
	private List<Piece> pieces;

	public GenericConsoleView(Observable<GameObserver> g, Controller c) {
		g.addObserver(this); // register as an observer
	}

	private void printBoard(Board board) {
		System.out.println(board);
		System.out.print("Players: ");
		for (Piece p : pieces) {
			System.out.print(p + " ");
			if (board.getPieceCount(p) != null) {
				System.out.print("(" + board.getPieceCount(p) + ") ");
			}
		}
	}

	@Override
	public void onGameStart(Board board, String gameDesc, List<Piece> pieces, Piece turn) {
		this.pieces = pieces;
		System.out.println("Starting '" + gameDesc + "'");
		System.out.println();
		printBoard(board);
		System.out.println();
		System.out.println("Turn for " + turn);
	}

	@Override
	public void onGameOver(Board board, State state, Piece winner) {
		System.out.println();
		System.out.println("Game Over!!");
		System.out.println();
		printBoard(board);
		System.out.println();
		System.out.println("Game Status: " + state);
		if (state == State.Won) {
			System.out.println("Winner: " + winner);
		}
	}

	@Override
	public void onMoveStart(Board board, Piece turn) {
	}

	@Override
	public void onMoveEnd(Board board, Piece turn, boolean success) {
	}

	@Override
	public void onChangeTurn(Board board, Piece turn) {
		printBoard(board);
		System.out.println();
		System.out.println("Turn for " + turn);
	}

	@Override
	public void onError(String msg) {
		System.err.println(msg);
		System.err.flush();
	}

}
