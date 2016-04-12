package Logic;

import java.util.Scanner;

public interface Cell {

	// Methods

	// Getters

	/**
	 * @return whether the cell is edible or not.
	 */
	public abstract boolean isEdible();

	// Calculators

	/**
	 * Executes the movement of a cell in a given position.
	 * 
	 * @param pos
	 *            is the position of the cell.
	 * @param surface
	 *            is where the cell is living.
	 * @return the position where the cell has moved.
	 */
	public abstract Position executeMovement(Position pos, Surface surface);

	/**
	 * Loads a cell from a file.
	 * 
	 * @param file.
	 * @return a new cell with the read parameters.
	 */
	public abstract Cell loadCell(Scanner file);

	/**
	 * Saves a cell to a file.
	 * 
	 * @return the information of the cell.
	 */
	public abstract String saveCell();

	public abstract String toString();

}
