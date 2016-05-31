package es.ucm.fdi.tp.basecode.bgame.model;

/**
 * An implementation of a finite rectangular board.
 * 
 * <p>
 * Implementacion de un tablero rectangular de dimensión finita.
 * 
 */
public class FiniteRectBoard extends BasicBoard {

	private static final long serialVersionUID = 1L;

	/**
	 * The internal representation of the board. Simply a matrix of objects of
	 * type {@link Piece}.
	 * 
	 * <p>
	 * Representacion interna del tablero: una matriz de objetos de tipo
	 * {@link Piece}.
	 */
	private Piece[][] board;

	/**
	 * The number of occupied cells in the board.
	 * 
	 * <p>
	 * Numero de casillas ocupadas en el tablero.
	 */
	private int occupied;

	/**
	 * The number of cells in the board -- {@code rows*cols}. Just to avoid
	 * recalculating the value, etc.
	 * 
	 * <p>
	 * Numero total de casillas en el tablero --{@code rows*cols}. Se utiliza
	 * para evitar recalcular el valor.
	 */
	private int numOfCells;

	/**
	 * Number of columns in the board.
	 * <p>
	 * Numero de columnas del tablero.
	 */
	private int cols;

	/**
	 * Number of rows in the board.
	 * <p>
	 * Numero de filas del tablero.
	 */
	private int rows;

	/**
	 * This constructor constructs a finite rectangular board of a given
	 * dimension.
	 * 
	 * <p>
	 * Construye un tablero rectangular finito de una dimension determinada.
	 * 
	 * @param rows
	 *            Number of rows.
	 *            <p>
	 *            Numero de filas.
	 * @param cols
	 *            Number of columns.
	 *            <p>
	 *            Numero de columnas.
	 */
	public FiniteRectBoard(int rows, int cols) {
		if (rows <= 0 || cols <= 0) {
			throw new GameError("Invalid finte rectangular board size  (" + rows + "," + cols + ")");
		}

		this.rows = rows;
		this.cols = cols;
		board = new Piece[rows][cols];
		occupied = 0;
		numOfCells = rows * cols;
	}

	@Override
	public void setPosition(int row, int col, Piece p) {
		if (row < 0 || row >= rows || col < 0 || col >= cols) {
			throw new GameError("Trying to access an invalid position (" + row + "," + col + ")");
		}

		Piece q = board[row][col]; // we get the current piece at position
									// (row,col) just to correctly update the
									// number of occupied cells.

		if (p == null && q != null)
			occupied--;
		else if (p != null && q == null)
			occupied++;
		board[row][col] = p;
	}

	@Override
	public Piece getPosition(int row, int col) {
		if (row < 0 || row >= rows || col < 0 || col >= cols) {
			throw new GameError("Trying to access an invalid position (" + row + "," + col + ")");
		}
		return board[row][col];
	}

	@Override
	public int getRows() {
		return rows;
	}

	@Override
	public int getCols() {
		return cols;
	}

	private void copyTo(FiniteRectBoard newboard) {

		// ask the super class to copy its stuff first.
		super.copyTo(newboard);

		// copy the actual board
		newboard.board = new Piece[rows][cols];
		for (int i = 0; i < rows; i++)
			for (int j = 0; j < cols; j++)
				newboard.board[i][j] = board[i][j];
		newboard.cols = cols;
		newboard.rows = rows;

		// copy cell count information
		newboard.occupied = occupied;
		newboard.numOfCells = numOfCells;
	}

	@Override
	public Board copy() {
		FiniteRectBoard newboard = new FiniteRectBoard(rows, cols);
		copyTo(newboard);
		return newboard;
	}

	@Override
	public boolean isFull() {
		return occupied == numOfCells;
	}

	@Override
	public boolean isEmpty() {
		return occupied == 0;
	}

	/**
	 * Generates a string that represents the board. The symbols used to print
	 * the board are the first characters of the piece identifier.
	 * 
	 * <p>
	 * Genera un string que representa el tablero. El simbolo utilizado para
	 * cada ficha es el primer caracter de su id.
	 * 
	 * @return A string representation of the board.
	 */
	@Override
	public String toString() {
		StringBuilder render = new StringBuilder();

		int height = getRows();
		int width = getCols();

		for (int r = 0; r < height; ++r) {

			render.append("  +");
			for (int c = 0; c < width; ++c)
				render.append("---+");
			render.append("\n");
			render.append("" + (r % 10) + " |");

			for (int c = 0; c < width; ++c) {
				if (getPosition(r, c) == null) {
					render.append("   |");
				} else {
					render.append(" " + getPosition(r, c).toString().charAt(0) + " |");
				}
			} // for columns
			render.append("\n");
		} // for rows

		render.append("  +");
		for (int c = 0; c < width; ++c)
			render.append("---+");
		render.append("\n");
		render.append("   ");
		for (int c = 0; c < width; ++c)
			render.append(" " + (c % 10) + "  ");
		render.append("\n");

		return render.toString();
	}

}
