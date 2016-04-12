package Logic;
import java.lang.StringBuilder;

public class Surface {
	//Attributes
	private Cell [][] surface;
	private static final int LIFE = 1, IMMATURE = 2, INIT_CELLS = 6;
	private int rows, columns;
	
	/**
	 * Initializes the surface by calling the method initSurface
	 * @param numRows is the number of rows that the surface has
	 * @param numColumns is the number of columns that the surface has
	 */
	public Surface (int numRows, int numColumns){
		rows = numRows;
		columns = numColumns;
		surface = new Cell[rows][columns];
		initSurface(surfacePositions(rows, columns));
	}
	
	//Methods
	/**
	 * Initializes the surface with INIT_CELLS cells on it
	 * @param surfacePositions is the list of all the positions of the surface
	 */
	public void initSurface(ListOfPositions surfacePositions) {
		Position position = new Position(0,0);
		int row, column;
		//Shuffle the list of positions
		surfacePositions.shuffle();
		//Create cells in random positions
		for(int i = 0; i < INIT_CELLS; i++) {
			position = surfacePositions.getPosition(i);
			row = position.getRow();
			column = position.getColumn();
			createCell(row, column);
		}
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
	 * Cleans the surface of cells
	 */
	public void cleanSurface() {
		for(int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				surface[i][j] = null;
			}
		}
	}
	/**
	 * Creates a new cell in the surface
	 * @param row is the row specified by the user
	 * @param column is the column specified by the user
	 * @return a boolean indicating whether the cell have been created or not
	 */
	public boolean createCell(int row, int column) {
		boolean created = false;
		
		//Checks if it is a valid position and if so, create the cell
		if ((validPosition(row, column)))  {
			if (surface[row][column] == null) {
			surface[row][column] = new Cell(LIFE, IMMATURE);
			created = true;
			}
		}
		
		return created;
	}
	/**
	 * Deletes a cell in the surface
	 * @param row is the row specified by the user
	 * @param column is the column specified by the user
	 * @return a boolean indicating whether the cell have been deleted or not
	 */
	public boolean deleteCell(int row, int column) {
		boolean deleted = false;
		
		//Checks if it is a valid position and if there is no cell on it, if so, delete the cell
		if(validPosition(row, column)) {
			if(surface[row][column] == null)
				deleted = false;
			else {
				surface[row][column] = null;
				deleted = true;
			}
		}
		else 
			deleted = false;
		
		return deleted;
	}
	
	/**
	 * Given a position, return true if it is a valid one, false in any other case.
	 * @param row is the row specified by the user
	 * @param column is the column specified by the user
	 * @return a boolean indicating whether is a valid position or not
	 */
	public boolean validPosition(int row, int column) {
		boolean valid;
		
		if((row < rows) && (column < columns))
			valid = true;
		else
			valid = false;
		
		return valid;
	}
	
	
	
	/**
	 * Indicates the number of rows of the surface
	 * @return the number of rows of the surface
	 */
	public int getRows() {
		return rows;
	}
	
	/**
	 * Indicates the number of columns in the surface
	 * @return the number of columns of the surface
	 */
	public int getColumns(){
		return columns;
	}
	
	/**
	 * Check if a position is not occupied by a cell
	 * @param row is the row of a given position
	 * @param column is the column of a given position
	 * @return a boolean indicating whether a position is occupied or not
	 */
	public boolean availablePosition(int row, int column) {
		boolean free = false;
		
		if(surface[row][column] == null) {
			free = true;
		}
		
		return free;
	}
	
	/**
	 * Returns a list of free neighbouring positions
	 * @param neighboursList is the list of neighbours of a position
	 * @return a list of available positions of a given position
	 */
	public ListOfPositions availableNeighbours(ListOfPositions neighboursList) {
		ListOfPositions availableNeighbours = new ListOfPositions();
		int row, column;
		
		//We traverse the list of neighbours looking for empty positions
		for(int i = 0; i < neighboursList.length(); i++) {
			row = neighboursList.getPosition(i).getRow();
			column = neighboursList.getPosition(i).getColumn();
			if(availablePosition(row, column) == true) {
				availableNeighbours.add(neighboursList.getPosition(i));
			}
		}
		
		return availableNeighbours;
	}
	
	/**
	 * Executes a step for all the cells of the surface
	 * @param matrix is the array of booleans
	 * @return a string indicating the movements of the cells
	 */
	public String moveCells(ArrayOfBooleans matrix) {
		int row, column;
		StringBuilder printMovements = new StringBuilder();
		//We traverse the array
		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < columns; j++) {
				//We check if there is a cell in that position, or if the cell have already moved
				if ((surface[i][j] != null) && !(matrix.getPosition(i, j))) {
					Position position = new Position(i, j);
					ListOfPositions neighbours = position.neighbours(rows, columns);
					ListOfPositions freeNeighbours = availableNeighbours(neighbours);
					
					if(!freeNeighbours.empty()) { //If the cell can move
						if(surface[i][j].getMaturity() == 0) { //If it is mature, it divides
							freeNeighbours.shuffle(); //Shuffle list of available neighbours
							position = freeNeighbours.getPosition(0); //Get the first position of that list
							row = position.getRow();
							column = position.getColumn();
							createCell(row, column); //Create a new cell in the new position
							matrix.setToTrue(row, column);
							surface[i][j].resetMaturity(); //Reset the values of the previous cell
							surface[i][j].cellStep();
							matrix.setToTrue(i, j);
							printMovements.append("Cell at (" + i + ", " + j + ") moved to (" + row + ", " + column + ")\n");
							printMovements.append("New cell born at (" + i + ", " + j + ")\n");
						}
						else { //If the cell isn't mature, it moves
							surface[i][j].cellStep();
							surface[i][j].decreaseCellImmature();
							freeNeighbours.shuffle(); //Shuffle list of available neighbours
							position = freeNeighbours.getPosition(0); //Get the first position of that list
							row = position.getRow();
							column = position.getColumn();
							surface[row][column] = surface[i][j]; //Move the cell to that position
							matrix.setToTrue(row, column);
							deleteCell(i, j); 
							printMovements.append("Cell at (" + i + ", " + j + ") moved to (" + row + ", " + column + ")\n");
						}
					}
					else { //If the cell can't move
						//If the cell has less than 0 lives or it is mature, it dies
						if((surface[i][j].getLife() == 0) || (surface[i][j].getMaturity() == 0)) {
							if(surface[i][j].getLife() == 0) {
								printMovements.append("Cell at (" + i + ", " + j + ") dies of inactivity. \n");
							}
							else
								printMovements.append("Cell at (" + i + ", " + j + ") dies for being unable to reproduce. \n");
							deleteCell(i, j);	
						}
						else { //If not, its values decreases
						surface[i][j].decreaseCellLife();
						surface[i][j].decreaseCellImmature();
						}
					}
				}
			}
		}
		matrix.resetMatrix();
		return printMovements.toString();
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		StringBuilder printedSurface = new StringBuilder(); 
		
		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < columns; j++) {
				if (surface[i][j] != null) 
					printedSurface.append(" " + surface[i][j] + " ");
				else
					printedSurface.append("  -  ");
			}
			printedSurface.append("\n");
		}
		
		return printedSurface.toString();
	}
}