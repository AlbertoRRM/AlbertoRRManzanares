package es.ucm.fdi.tp.assignment4;

import java.util.List;

import es.ucm.fdi.tp.basecode.bgame.model.Board;
import es.ucm.fdi.tp.basecode.bgame.model.GameError;
import es.ucm.fdi.tp.basecode.bgame.model.GameMove;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;

public class AtaxxMove extends GameMove {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The row where the piece to move is.
	 * <p>
	 * Fila en la que se coloca la ficha devuelta por
	 * {@link GameMove#getPiece()}.
	 */
	protected int init_row;

	/**
	 * The column where to place the piece return by {@link GameMove#getPiece()}
	 * .
	 * <p>
	 * Columna en la que se coloca la ficha devuelta por
	 * {@link GameMove#getPiece()}.
	 */
	protected int init_col;

	/**
	 * The row where to place the piece return by {@link GameMove#getPiece()}.
	 * <p>
	 * Fila en la que se coloca la ficha devuelta por
	 * {@link GameMove#getPiece()}.
	 */
	protected int final_row;

	/**
	 * The column where to place the piece return by {@link GameMove#getPiece()}
	 * .
	 * <p>
	 * Columna en la que se coloca la ficha devuelta por
	 * {@link GameMove#getPiece()}.
	 */
	protected int final_col;

	/**
	 * This constructor should be used ONLY to get an instance of
	 * {@link AtaxxMove} to generate game moves from strings by calling
	 * {@link #fromString(String)}
	 * 
	 * <p>
	 * Solo se debe usar este constructor para obtener objetos de
	 * {@link AtaxxMove} para generar movimientos a partir de strings usando el
	 * metodo {@link #fromString(String)}
	 * 
	 */

	public AtaxxMove() {
	}

	/**
	 * Constructor with parameters for an ataxx move.
	 * 
	 * @param init_row
	 *            is the initial row of the selected piece.
	 * @param init_col
	 *            is the initial column of the selected piece.
	 * @param final_row
	 *            is the destination row.
	 * @param final_col
	 *            is the destination column.
	 * @param p
	 *            is the piece that is going to be moved.
	 */
	public AtaxxMove(int init_row, int init_col, int final_row, int final_col, Piece p) {
		super(p);
		this.init_row = init_row;
		this.init_col = init_col;
		this.final_row = final_row;
		this.final_col = final_col;
	}

	@Override
	public void execute(Board board, List<Piece> pieces) {
		Piece p = null;
		int row_distance = Math.abs(init_row - final_row);
		int column_distance = Math.abs(init_col - final_col);

		// If there's a player's piece
		if (getPiece().equals(board.getPosition(init_row, init_col))) {

			// If the destination coordinates are not occupied
			if (board.getPosition(final_row, final_col) == null) {

				// If the distance is lower or equal than 2
				if ((row_distance <= 2) && (column_distance <= 2)) {
					// Set the player's piece
					board.setPosition(final_row, final_col, getPiece());

					// If the distance is two, theh piece is not duplicated
					if ((row_distance == 2) || (column_distance == 2))
						board.setPosition(init_row, init_col, null);
					convertNeighbouringPieces(board, p, pieces);

				} else
					throw new GameError("the destination cell (" + final_row + "," + final_col
							+ ") is at a distance greater than 2.");
			} else
				throw new GameError("the position (" + final_row + "," + final_col + ") is already occupied.");
		} else
			throw new GameError("the piece at position (" + init_row + "," + init_col + ") is not one of your pieces.");
	}

	/**
	 * Converts the enemy's neughbouring pieces into the current player ones
	 * 
	 * @param board
	 *            is the current board of the game.
	 * @param p
	 *            is the piece that has been moved.
	 * @param pieces
	 *            is the list of pieces of the game.
	 */
	private void convertNeighbouringPieces(Board board, Piece p, List<Piece> pieces) {
		// Traverses the neighboring positions to convert all the opposite
		// pieces adjacent to it.
		for (int i = -1; i <= 1; i++) {
			for (int j = -1; j <= 1; j++) {
				// If it is a valid position and there is an opposite piece,
				// change it
				if (final_row + i >= 0 && final_col + j >= 0 && final_row + i < board.getRows()
						&& final_col + j < board.getCols()) {
					p = board.getPosition(final_row + i, final_col + j);
					if ((p != null) && !(getPiece().equals(p)) && pieces.contains(p)) {
						board.setPosition(final_row + i, final_col + j, getPiece());
					}
				}
			}
		}
	}

	/**
	 * Creates a move with the given piece.
	 * 
	 * @param init_row
	 *            is the initial row of the selected piece.
	 * @param init_col
	 *            is the initial column of the selected piece.
	 * @param final_row
	 *            is the destination row.
	 * @param final_col
	 *            is the destination column.
	 * @param p
	 *            is the piece that is going to be moved.
	 * @return an AtaxxMove.
	 */
	protected GameMove createMove(int init_row, int init_col, int final_row, int final_col, Piece p) {
		return new AtaxxMove(init_row, init_col, final_row, final_col, p);
	}

	@Override
	public GameMove fromString(Piece p, String str) {
		String[] words = str.split(" ");
		if (words.length != 4) {
			return null;
		}

		try {
			int init_row, init_col, final_row, final_col;
			init_row = Integer.parseInt(words[0]);
			init_col = Integer.parseInt(words[1]);
			final_row = Integer.parseInt(words[2]);
			final_col = Integer.parseInt(words[3]);
			return createMove(init_row, init_col, final_row, final_col, p);
		} catch (NumberFormatException e) {
			return null;
		}
	}

	@Override
	public String help() {
		return "Row and column for origin and for destination, separated by spaces (four numbers).";
	}

	public String toString() {
		if (getPiece() == null) {
			return help();
		} else
			return null;
	}
}
