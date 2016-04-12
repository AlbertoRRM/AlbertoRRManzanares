package Logic;

public class ArrayOfBooleans {
	//Attributes
	private boolean [][] matrix;
	private int numRows, numColumns;
	
	//Constructor
	/**
	 * Creates a new array of booleans
	 * @param numRows is the number of rows of the array
	 * @param numColumns is the number of columns of the array
	 */
	public ArrayOfBooleans (int numRows, int numColumns) {
		this.numRows = numRows;
		this.numColumns = numColumns;
		matrix = new boolean[numRows][numColumns];
	}
	
	//Methods
	/**
	 * Initializes all the booleans to false
	 */
	public void resetMatrix() {
		for(int i = 0; i < numRows; i++) {
			for (int j = 0; j < numColumns; j++) {
				matrix[i][j] = false;
			}
		}
	}
	
	/**
	 * Set the given position to true.
	 * @param row
	 * @param column
	 */
	public void setToTrue(int row, int column) {
		matrix[row][column] = true;
	}
	
	/**
	 * Set the given position to false.
	 * @param row
	 * @param column
	 */
	public void setToFalse(int row, int column) {
		matrix[row][column] = false;
	}
	
	/**
	 * Indicates the boolean of a given position
	 * @param row
	 * @param column
	 * @return the boolean of the given position
	 */
	public boolean getPosition(int row, int column) {
		return matrix[row][column];
	}
}
