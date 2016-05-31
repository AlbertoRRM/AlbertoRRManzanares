package es.ucm.fdi.tp.basecode.connectn;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
public class ConnectNRules implements GameRules {

	// This object is returned by gameOver to indicate that the game is not
	// over. Just to avoid creating it multiple times, etc.
	//
	protected final Pair<State, Piece> gameInPlayResult = new Pair<State, Piece>(State.InPlay, null);

	protected int dim;

	public ConnectNRules(int dim) {
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
		int j;
		Piece p;

		// check rows & cols
		for (int i = 0; i < dim; i++) {
			// row i
			p = board.getPosition(i, 0);
			if (p != null) {
				j = 1;
				while (j < dim && p.equals(board.getPosition(i, j)))
					j++;
				if (j == dim)
					return new Pair<State, Piece>(State.Won, p);
			}

			// col i
			p = board.getPosition(0, i);
			if (p != null) {
				j = 1;
				while (j < dim && p.equals(board.getPosition(j, i)))
					j++;
				if (j == dim)
					return new Pair<State, Piece>(State.Won, p);
			}
		}

		// diagonal 1 - left-up to right-bottom
		p = board.getPosition(0, 0);
		if (p != null) {
			j = 1;
			while (j < dim && p.equals( board.getPosition(j, j) )) {
				j++;
			}
			if (j == dim) {
				return new Pair<State, Piece>(State.Won, p);
			}
		}

		// diagonal 2 - left-bottom to right-up
		p = board.getPosition(dim - 1, 0);
		if (p != null) {
			j = 1;
			while (j < dim && p.equals( board.getPosition(dim - j - 1, j)) ) {
				j++;
			}
			if (j == dim) {
				return new Pair<State, Piece>(State.Won, p);
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
	public double evaluate(Board board, List<Piece> playersPieces, Piece turn, Piece p) {

		int n = possibleWinLines(board, p); // win lines for p
		int m = 0; // max of win lines of other players
		double d = 2.0 * dim + 2.0; // the max win lines a player can have

		for (Piece q : playersPieces) {
			if (!p.equals(q)) {
				int x = possibleWinLines(board, q);
				if (x > m) {
					m = x;
				}
			}
		}

		return (n / d) - (m / d);
	}

	@Override
	public List<GameMove> validMoves(Board board, List<Piece> playersPieces, Piece turn) {
		List<GameMove> moves = new ArrayList<GameMove>();

		for (int i = 0; i < board.getRows(); i++) {
			for (int j = 0; j < board.getCols(); j++) {
				if (board.getPosition(i, j) == null) {
					moves.add(new ConnectNMove(i, j, turn));
				}
			}
		}
		return moves;
	}

	/**
	 * Compute the number of win-lines for p. A win-line is a row, column or a
	 * diagonal that does not have a piece different from p.
	 * 
	 * @param board
	 *            The game board.
	 * @param p
	 *            A piece whose win-lines we want to compute.
	 * @return
	 */
	private int possibleWinLines(Board board, Piece p) {

		Set<Integer> rows = new HashSet<>();
		Set<Integer> cols = new HashSet<>();
		Set<Integer> diag = new HashSet<>();

		for (int i = 0; i < dim; i++) {
			rows.add(i);
			cols.add(i);
		}

		diag.add(1);
		diag.add(2);

		for (int i = 0; i < board.getRows() && (rows.size() > 0 || cols.size() > 0); i++) {
			for (int j = 0; j < board.getCols() && (rows.size() > 0 || cols.size() > 0); j++) {
				Piece q = board.getPosition(i, j);
				if (q != null && !q.equals(p)) {
					rows.remove(i);
					cols.remove(j);

					if (i == j) {
						diag.remove(1);
					}

					if (i + j == dim - 1) {
						diag.remove(2);
					}
				}

			}
		}

		return rows.size() + cols.size() + diag.size();
	}

}
