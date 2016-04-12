package Logic;

public class World {
	//Attributes
		private final int rows = 3, columns = 4;
		private Surface mySurface;
		private ArrayOfBooleans myMatrix = new ArrayOfBooleans(rows, columns);
	
	//Constructor
	/**
	 * Prepares the surface by placing a fixed number of cells in 
	 * random positions and it prepares the array of booleans
	 */
	public World() {
		mySurface = new Surface(rows, columns);
		myMatrix.resetMatrix();
	}
	
	//Methods
	/**
	 * It calls the method moveCells which is in charge of execute the steps of the cells
	 * and returns the string of movements.
	 * @return the string of movements
	 */
	public String evolve() {
		return mySurface.moveCells(myMatrix); 
	}
	
	/**
	 * Indicates if a cell have been created or not.
	 * @param row is the row given by the user
	 * @param column is the column given by the user
	 * @return boolean indicating whether the cell have been created or not
	 */
	public boolean create(int row, int column) {
		return mySurface.createCell(row, column);
	}
	
	/**
	 * Indicates if a cell have been deleted or not.
	 * @param row is the row given by the user
	 * @param column is the column given by the user
	 * @return boolean indicating whether the cell have been deleted or not
	 */
	public boolean destroy(int row, int column) {
		return mySurface.deleteCell(row, column);
	}
	
	/**
	 * It calls two methods: cleanSurface, which deletes all the cells from the surface 
	 * (if there are) and resetMatric, which reset all the booleans to false.
	 */
	public void cleanSurface() {
		mySurface.cleanSurface();
		myMatrix.resetMatrix();
	}
	
	/**
	 * It creates the list of positions of the surface and calls the method initSurface,
	 * which initializes the surface.
	 */
	public void initSurface() {
		ListOfPositions positions = mySurface.surfacePositions(mySurface.getRows(), mySurface.getColumns());
		mySurface.initSurface(positions);
	}
	
	//Returns the surface's string
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return mySurface.toString();
	}
}
