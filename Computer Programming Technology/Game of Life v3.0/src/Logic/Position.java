package Logic;

public class Position {

	// Attributes

	private int row, column;

	// Constructor

	/**
	 * Creates a position with the given row and column.
	 * 
	 * @param row
	 * @param column
	 */
	public Position(int row, int column) {
		this.row = row;
		this.column = column;
	}

	// Methods

	// Getters
	/**
	 * @return the row of a position.
	 */
	public int getRow() {
		return row;
	}

	/**
	 * @return the column of a position.
	 */
	public int getColumn() {
		return column;
	}

	// Calculators

	/**
	 * Returns the list of neighboring positions of a given position.
	 * 
	 * @param rows
	 *            indicate the number of rows of the surface.
	 * @param columns
	 *            indicate the number of columns of the surface.
	 * @return the list of neighboring positions.
	 */
	public ListOfPositions neighbours(int rows, int columns) {
		ListOfPositions neighboursList = new ListOfPositions();
		int[] diffx = { 0, -1, -1, -1, 0, 1, 1, 1 };
		int[] diffy = { -1, -1, 0, 1, 1, 1, 0, -1 };
		int row, column;

		for (int i = 0; i < 8; i++) {
			row = this.row;
			column = this.column;
			row += diffx[i];
			column += diffy[i];

			if ((row < 0) || (column < 0) || (row == rows) || (column == columns)) {
				// Discard position
			} else {
				neighboursList.add(new Position(row, column));
			}
		}

		return neighboursList;
	}

	/**
	 * Compares two positions.
	 * 
	 * @param pos
	 *            is the position that is going to be compared.
	 * @return a boolean indicating if the positions are the same or not.
	 */
	public boolean isEqual(Position pos) {
		boolean equal;

		if ((this.row == pos.getRow()) && (this.column == pos.getColumn()))
			equal = true;
		else
			equal = false;

		return equal;
	}
}
