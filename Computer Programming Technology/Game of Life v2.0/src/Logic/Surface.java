package Logic;

public class Surface {
	//Attributes
	private Cell [][] surface;
	private static final int LIFE = 1, IMMATURE = 2, EATS = 2, INIT_SIMPLE_CELLS = 3, INIT_COMPLEX_CELLS = 2;
	private int rows, columns;
	private StringBuilder message = new StringBuilder();
	
	//Constructor
	/**
	 * Initializes the surface by calling the method initSurface
	 * @param numRows is the number of rows that the surface has
	 * @param numColumns is the number of columns that the surface has
	 */
	public Surface (int numRows, int numColumns){
		rows = numRows;
		columns = numColumns;
		surface = new Cell[rows][columns];
	}
	
	//Methods
	/**
	 * Tell the cell that it has to be executed
	 * @param pos is the position of the cell
	 * @return the position where the cell has moved
	 */
	public Position executeMovement(Position pos) {
			return surface[pos.getRow()][pos.getColumn()].executeMovement(pos, this);
	}
	
	/**
	 * Create a simple cell in the given position
	 * @param pos is the position where the cell must be created
	 * @return a string indicating if the cell has been created or not
	 */
	public String createSimpleCell(Position pos) {
		String message;
	
		if(validPosition(pos)) {
			if(availablePosition(pos)) {
				surface[pos.getRow()][pos.getColumn()] = new SimpleCell(LIFE, IMMATURE);
				message = "New simple cell created at (" + pos.getRow() + ", " + pos.getColumn() + ").\n";
			}
			else
				message = "This position is already occupied by another cell.";
			
		}
		else
			message = "Invalid position!";
		
		return message;
	}
	
	/**
	 * Create a complex cell in the given position
	 * @param pos is the position where the cell must be created
	 * @return a string indicating if the cell has been created or not
	 */
	public String createComplexCell(Position pos) {
		String message;
		
		if(validPosition(pos)) {
			if(availablePosition(pos)) {
				surface[pos.getRow()][pos.getColumn()] = new ComplexCell(EATS);
				message = "New complex cell created at (" + pos.getRow() + ", " + pos.getColumn() + ").\n";
			}
			else
				message = "This position is already occupied by another cell.";
		}
		else
			message = "Invalid position!";
		
		return message;
	}
	
	/**
	 * Move the cell from an initial given position to a final given position
	 * @param init_pos is the initial position
	 * @param final_pos is the final position
	 */
	public void moveCell(Position init_pos, Position final_pos) {
		surface[final_pos.getRow()][final_pos.getColumn()] = surface[init_pos.getRow()][init_pos.getColumn()];
	}
	
	/**
	 * Returns a list of positions of the surface
	 * @param rows is the number of rows of the surface
	 * @param columns is the number of columns of the surface
	 * @return a list with the positions of the surface
	 */
	public ListOfPositions surfacePositions(int rows, int columns) {
		ListOfPositions list = new ListOfPositions();
		
		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < columns; j++) {
				list.add(new Position(i, j));
			}
		}
		
		return list;
	}
	
	/**
	 * Return a list of available neighbours of a simple cell
	 * @param neighboursList is the list of neighbours of a simple cell
	 * @return a list of free neighbouring positions of a cell
	 */
	public ListOfPositions availableNeighbours(ListOfPositions neighboursList) {
		ListOfPositions availableNeighbours = new ListOfPositions();
		
		//We traverse the list of neighbours looking for empty positions
		for(int i = 0; i < neighboursList.length(); i++) {
			if(availablePosition(neighboursList.getPosition(i)) == true) {
				availableNeighbours.add(neighboursList.getPosition(i));
			}
		}
		
		return availableNeighbours;
	}
	
	/**
	 * Given a position, return true if it is a valid one, false in any other case.
	 * @param row is the row specified by the user
	 * @param column is the column specified by the user
	 * @return a boolean indicating whether is a valid position or not
	 */
	public boolean validPosition(Position pos) {
		return ((pos.getRow() < rows) && (pos.getColumn() < columns));
	}
	
	/**
	 * Check if a position is not occupied by a cell
	 * @param pos
	 * @return
	 */
	public boolean availablePosition(Position pos) {
		return (surface[pos.getRow()][pos.getColumn()] == null);
	}
	
	public int getRows() {
		return rows;
	}
	
	public int getColumns() {
		return columns;
	}
	
	public int getInitComplexCells() {
		return INIT_COMPLEX_CELLS;
	}
	
	public int getInitSimpleCells() {
		return INIT_SIMPLE_CELLS;
	}
	
	/**
	 * @return a StringBuilder with the movements of the cells
	 */
	public StringBuilder getMessage() {
		return message;
	}
	
	/**
	 * @param message is the message containing the movement of a cell
	 */
	public void setMessage(String message) {
		this.message.append(message);
	}
	
	public void resetMessage() {
		this.message.setLength(0);
	}
	
	/**
	 * Indicate if the cell at the given position is edible or not
	 * @param pos
	 * @return
	 */
	public boolean isEdible(Position pos) {
		if(!availablePosition(pos))
			return surface[pos.getRow()][pos.getColumn()].isEdible();
		else
			return false;
	}
	
	/**
	 * Delete a cell from a given position
	 * @param pos is the position where the cell must be deleted
	 * @return a string indicating if the cell has been deleted or not
	 */
	public String deleteCell(Position pos) {
		String message;
		
		if(validPosition(pos)) {
			surface[pos.getRow()][pos.getColumn()] = null;
			message = "Cell at (" + pos.getRow() + ", " + pos.getColumn() + ") has been deleted.\n";
		}
		else
			message = "Invalid position!";
		
		return message;
	}
	
	/**
	 * @return a list with the available positions of a complex cell.
	 */
	public ListOfPositions availableComplexPositions() {
		ListOfPositions list = new ListOfPositions();
		
		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < columns; j++) {
				if (surface[i][j] == null || surface[i][j].isEdible())
						list.add(new Position(i, j));
			}
		}
		
		return list;
	}
	
	public String toString() {
		StringBuilder printedSurface = new StringBuilder(); 
		
		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < columns; j++) {
				if (surface[i][j] != null)
					printedSurface.append(" " + surface[i][j].toString() + " ");
					
				else
					printedSurface.append(" - ");
			}
			printedSurface.append("\n");
		}
		
		return printedSurface.toString();
	}
}
