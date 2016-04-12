package Logic;

public abstract class Cell {
	//Attributes
	protected boolean edible;
	
	
	//Constructor
	public Cell() {
		
	}
	
	//Methods
	/**
	 * Execute the movement of a cell following its corresponding rules
	 * @param pos is the initial position of the cell
	 * @param surface is the surface where the cell is located
	 * @return the position where the cell has moved.
	 */
	public abstract Position executeMovement(Position pos, Surface surface);
	/**
	 * @return a boolean indicating if the cell is edible or not
	 */
	public abstract boolean isEdible();
	public abstract String toString();
	
}

