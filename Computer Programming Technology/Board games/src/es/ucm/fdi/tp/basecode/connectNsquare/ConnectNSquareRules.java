package es.ucm.fdi.tp.basecode.connectNsquare;

import java.util.ArrayList;
import java.util.List;

import es.ucm.fdi.tp.basecode.bgame.model.Board;
import es.ucm.fdi.tp.basecode.bgame.model.FiniteRectBoard;
import es.ucm.fdi.tp.basecode.bgame.model.GameError;
import es.ucm.fdi.tp.basecode.bgame.model.GameMove;
import es.ucm.fdi.tp.basecode.bgame.model.GameRules;
import es.ucm.fdi.tp.basecode.bgame.model.Pair;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;
import es.ucm.fdi.tp.basecode.bgame.model.Game.State;

/**
 * Rules for ConnectN game.
 * <ul>
 * <li>The game is played on an NxN board (with N>=3).</li>
 * <li>The number of players is between 2 and 4.</li>
 * <li>The player turn in the given order, each placing a piece on an empty
 * cell. The winner is the one who construct a line (horizontal, vertical or
 * diagonal) with N consecutive pieces of the same type.</li>
 * </ul>
 * 
 * <p>
 * Reglas del juego ConnectN.
 * <ul>
 * <li>El juego se juega en un tablero NxN (con N>=3).</li>
 * <li>El numero de jugadores esta entre 2 y 4.</li>
 * <li>Los jugadores juegan en el orden proporcionado, cada uno colocando una
 * ficha en una casilla vacia. El ganador es el que consigua construir una linea
 * (horizontal, vertical o diagonal) de N fichas consecutivas del mismo tipo.
 * </li>
 * </ul>
 *
 */
public class ConnectNSquareRules implements GameRules {

	// This object is returned by gameOver to indicate that the game is not
	// over. Just to avoid creating it multiple times, etc.
	//
	protected final Pair<State, Piece> gameInPlayResult = new Pair<State, Piece>(State.InPlay, null);

	private int dim;

	// Constructor 
	
	public ConnectNSquareRules(int dim) {
		if (dim < 3) {
			throw new GameError("Dimension must be at least 3: " + dim);
		} else {
			this.dim = dim;
		}
	}

	@Override
	public String gameDesc() {
		return "ConnectN " + dim + "x" + dim;
	}

	@Override
	public Board createBoard(List<Piece> pieces) {
		return new FiniteRectBoard(dim, dim);
	}

	@Override
	public Piece initialPlayer(Board board, List<Piece> playersPieces) {
		return playersPieces.get(0);
	}

	@Override
	public int minPlayers() {
		return 2;
	}

	@Override
	public int maxPlayers() {
		return 4;
	}

	@Override
	public Pair<State, Piece> updateState(Board board, List<Piece> playersPieces, Piece lastPlayer) {
		Piece p;

		// check rows & cols
		for (int i = 0; i < dim - 1; i++) {
			for (int j = 0; j < dim - 1; j++) {
				// row i
				p = board.getPosition(i, j);
				if (p != null) {
					if (board.getPosition(i, j + 1) == p) {
						if (board.getPosition(i + 1, j + 1) == p) {
							if (board.getPosition(i + 1, j) == p)
								return new Pair<State, Piece>(State.Won, p);
						}
					}
				}
			}
		}

		if (board.isFull()) {
			return new Pair<State, Piece>(State.Draw, null);
		}

		return gameInPlayResult;
	}

	@Override
	public Piece nextPlayer(Board board, List<Piece> playersPieces, Piece lastPlayer) {
		List<Piece> pieces = playersPieces;
		int i = pieces.indexOf(lastPlayer);
		return pieces.get((i + 1) % pieces.size());
	}

	@Override
	public List<GameMove> validMoves(Board board, List<Piece> playersPieces, Piece turn) {
		List<GameMove> moves = new ArrayList<GameMove>();

		for (int i = 0; i < board.getRows(); i++) {
			for (int j = 0; j < board.getCols(); j++) {
				if (board.getPosition(i, j) == null) {
					moves.add(new ConnectNSquareMove(i, j, turn));
				}
			}
		}
		return moves;
	}

	@Override
	public double evaluate(Board board, List<Piece> pieces, Piece turn, Piece p) {
		// TODO Auto-generated method stub
		return 0;
	}

}
