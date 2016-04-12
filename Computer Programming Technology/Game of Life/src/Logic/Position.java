package Logic;
public class Position {
	//Attributes
	private int row, column;
	
	//Constructor
	/**
	 * Creates a new position
	 * @param row 
	 * @param column
	 */
	public Position(int row, int column) {
		this.row = row;
		this.column = column;
	}
	
	//Methods
	/**
	 * Returns the list of neighbouring positions of a given position
	 * @param rows indicate the number of rows of the surface
	 * @param columns indicate the number of columns of the surface
	 * @return the list of neighbouring positions
	 */
	public ListOfPositions neighbours(int rows, int columns) {
		ListOfPositions neighboursList = new ListOfPositions();
		int[] diffx = {0, -1, -1, -1, 0, 1, 1, 1};
		int[] diffy = {-1, -1, 0, 1, 1, 1, 0, -1};
		int row, column;
		
		for(int i = 0; i < 8; i++) {
			row = this.row;
			column = this.column;
			row += diffx[i];
			column += diffy[i];
			
			if((row < 0) || (column < 0) || (row == rows) || (column == columns)) {
				//Discard position
			}
			else {
				neighboursList.add(new Position(row, column));
			}
		}

		return neighboursList;
	}
	
	/**
	 * Indicates the row of a position
	 * @return the row of a position
	 */
	public int getRow() {
		return row;
	}
	
	/**
	 * Indicates the column of a position
	 * @return the column of a position
	 */
	public int getColumn() {
		return column;
	}
}
