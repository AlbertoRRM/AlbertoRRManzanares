package es.ucm.fdi.tp.assignment4;

import java.util.ArrayList;
import java.util.List;

import es.ucm.fdi.tp.basecode.bgame.Utils;
import es.ucm.fdi.tp.basecode.bgame.model.Board;
import es.ucm.fdi.tp.basecode.bgame.model.FiniteRectBoard;
import es.ucm.fdi.tp.basecode.bgame.model.GameError;
import es.ucm.fdi.tp.basecode.bgame.model.Game.State;
import es.ucm.fdi.tp.basecode.bgame.model.GameMove;
import es.ucm.fdi.tp.basecode.bgame.model.GameRules;
import es.ucm.fdi.tp.basecode.bgame.model.Pair;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;

/**
 * Rules for Ataxx game.
 * <ul>
 * <li>The game is played on an NxN board (with N odd and N >= 5).</li>
 * <li>The number of players is between 2 and 4.</li>
 * <li>The player turn in the given order, each moving a piece to an empty cell
 * at distance 1 or 2. If the distance is 1, the piece is replicated, if it is
 * 2, it is only moved. The winner is the one who achieves the greater amount of
 * pieces when the board is completely filled.</li>
 * </ul>
 */
public class AtaxxRules implements GameRules {

	// This object is returned by gameOver to indicate that the game is not
	// over. Just to avoid creating it multiple times, etc.
	//
	protected final Pair<State, Piece> gameInPlayResult = new Pair<State, Piece>(State.InPlay, null);

	/**
	 * Dimension of the board.
	 */
	private int dim;

	/**
	 * Number of obstacles in the board.
	 */
	private int obstacles;

	/**
	 * Creates a default board with a default number of obstacles.
	 */
	public AtaxxRules() {
		this.dim = 5;
		this.obstacles = 2;
	}

	/**
	 * Creates a board with the given dimension and obstacles.
	 * 
	 * @param dim
	 *            The dimension of the board
	 * @param obstacles
	 *            The obstacles placed in the board
	 */
	public AtaxxRules(int dim, int obstacles) {
		if ((dim < 5) || ((dim % 2) == 0)) {
			throw new GameError("Dimension must be at least 5 and must be odd: " + dim);
		} else {
			this.dim = dim;
			this.obstacles = obstacles;
		}
	}

	@Override
	public String gameDesc() {
		return "Ataxx " + dim + "x" + dim;
	}

	@Override
	public Board createBoard(List<Piece> pieces) {
		Board board = new FiniteRectBoard(dim, dim);

		for (int i = 0; i < pieces.size(); i++) {
			if (i == 0) {
				board.setPosition(0, 0, pieces.get(i));
				board.setPosition(dim - 1, dim - 1, pieces.get(i));
			} else if (i == 1) {
				board.setPosition(0, dim - 1, pieces.get(i));
				board.setPosition(dim - 1, 0, pieces.get(i));
			} else if (i == 2) {
				board.setPosition((dim - 1) / 2, 0, pieces.get(i));
				board.setPosition((dim - 1) / 2, dim - 1, pieces.get(i));
			} else {
				board.setPosition(0, (dim - 1) / 2, pieces.get(i));
				board.setPosition(dim - 1, (dim - 1) / 2, pieces.get(i));
			}
		}

		if (this.obstacles > (dim * dim) * 0.1)
			this.obstacles = (int) ((dim * dim) * 0.1);

		Piece obstac = getObstPiece(pieces);
		int n = board.getRows() - 1;
		for (int i = 0; i < this.obstacles; i++) {
			int row, col;

			do {
				row = Utils.randomInt((board.getRows() / 2) + 1);
				col = Utils.randomInt((board.getCols() / 2) + 1);
			} // The second and third conditions check if the row/column is not
				// the middle one, because I want to locate the obstacles
				// in the quadrants
			while ((board.getPosition(row, col) != null || (row == (int) (board.getRows() / 2)))
					|| (col == (int) (board.getCols() / 2)));

			board.setPosition(row, col, obstac);
			board.setPosition(row, n - col, obstac);
			board.setPosition(n - row, col, obstac);
			board.setPosition(n - row, n - col, obstac);
		}
		return board;
	}

	/**
	 * Creates a piece which is an obstacle with an inexisting ID
	 * 
	 * @param l
	 *            is the list of pieces involved in the game
	 * @return an obstacle with an specific ID
	 */
	Piece getObstPiece(List<Piece> l) {
		int i = 0;

		while (true) {
			Piece o = new Piece("*#" + i);
			if (!l.contains(o))
				return o;
			i++;
		}
	}

	@Override
	public Piece initialPlayer(Board board, List<Piece> playersPieces) {
		return nextPlayer(board, playersPieces, playersPieces.get(playersPieces.size() - 1));
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
		if (nextPlayer(board, playersPieces, lastPlayer) == null) {
			int count, max = 0, tie = -1;
			Piece winner = null;

			// Traverse each player
			for (int i = 0; i < playersPieces.size(); i++) {
				Piece p = playersPieces.get(i);
				// Count pieces
				count = countPieces(board, p);
				// If the pieces is the maximum amount, the winner could be the
				// current player
				if (count > max) {
					winner = p;
					max = count;
				} else if (count == max) {
					tie = count;// If two player have the maximum amount of
								// pieces and there are
					// no other players to check, it is a draw
				}
			}

			if ((tie != -1) && (tie >= max))
				return new Pair<State, Piece>(State.Draw, winner);
			else
				return new Pair<State, Piece>(State.Won, winner);
		} else {
			int cont = 0;
			for (int m = 0; m < playersPieces.size(); m++) {
				Piece q = playersPieces.get(m);
				if (validMoves(board, playersPieces, q).isEmpty()) {
					cont++;
				}

				if (cont == playersPieces.size() - 1) {
					return new Pair<State, Piece>(State.Won, lastPlayer);
				}
			}

		}

		return gameInPlayResult;
	}

	/**
	 * Count the number of pieces on the board of a player
	 * 
	 * @param board
	 * @param p
	 * @return the current number of pieces on the board
	 */
	private int countPieces(Board board, Piece p) {
		int count = 0;

		for (int r = 0; r < dim; r++) {
			for (int c = 0; c < dim; c++) {
				if (board.getPosition(r, c).equals(p))
					count++;
			}
		}

		return count;
	}

	@Override
	public Piece nextPlayer(Board board, List<Piece> playersPieces, Piece lastPlayer) {
		int i = playersPieces.indexOf(lastPlayer);
		Piece nextPlayer = playersPieces.get((i + 1) % playersPieces.size());

		while (nextPlayer != lastPlayer) {
			if (validMoves(board, playersPieces, nextPlayer).size() != 0) 
				return nextPlayer;
			else
				i++;
			nextPlayer = playersPieces.get((i + 1) % playersPieces.size());
		}

		if ((nextPlayer == lastPlayer) && validMoves(board, playersPieces, nextPlayer).size() == 0)
			nextPlayer = null;

		return nextPlayer;
	}

	@Override
	public List<GameMove> validMoves(Board board, List<Piece> playersPieces, Piece turn) {
		List<GameMove> moves = new ArrayList<GameMove>();

		for (int i = 0; i < board.getRows(); i++) {
			for (int j = 0; j < board.getCols(); j++) {

				if (turn.equals(board.getPosition(i, j))) {

					for (int k = -2; k <= 2; k++) {
						for (int l = -2; l <= 2; l++) {

							if (i + k >= 0 && j + l >= 0 && i + k < board.getRows() && j + l < board.getCols()) {
								if (board.getPosition(i + k, j + l) == null) {
									moves.add(new AtaxxMove(i, j, i + k, j + l, turn));
								}
							}
						}
					}
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
