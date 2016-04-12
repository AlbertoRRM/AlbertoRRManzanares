package Logic;

import java.io.PrintWriter;
import java.util.Scanner;

public abstract class World {

	// Attributes

	protected int rows, columns;
	protected int INIT_SIMPLE_CELLS, INIT_COMPLEX_CELLS;
	protected Surface mySurface;

	// Constructor

	/**
	 * Creates an empty world.
	 */
	public World() {
		this.rows = 0;
		this.columns = 0;
		this.INIT_SIMPLE_CELLS = 0;
		this.INIT_COMPLEX_CELLS = 0;
	}

	/**
	 * Creates a world with the given parameters.
	 */
	public World(int rows, int columns) {
		this.rows = rows;
		this.columns = columns;
		mySurface = new Surface(this.rows, this.columns);
	}

	// Methods

	// Initializers

	/**
	 * Initializes the world with the rows, columns, and initial cells.
	 */
	public abstract void initializeWorld();

	// Setters

	/**
	 * Sets the number of rows.
	 * 
	 * @param rows
	 */
	public void setRows(int rows) {
		this.rows = rows;
	}

	/**
	 * Sets the number of columns.
	 * 
	 * @param columns
	 */
	public void setColumns(int columns) {
		this.columns = columns;
	}

	// Getters

	/**
	 * @return a StringBuilder that contains the movements of the cells.
	 */
	public abstract StringBuilder readMessage();

	/**
	 * Gets a list of all the surface positions.
	 * 
	 * @return a list of surface positions.
	 */
	public ListOfPositions getSurfacePositions() {
		return mySurface.surfacePositions(rows, columns);
	}

	/**
	 * @return the number of columns in the surface.
	 */
	public int getSurfaceColumns() {
		return mySurface.getColumns();
	}

	/**
	 * @return the number of rows in the surface.
	 */
	public int getSurfaceRows() {
		return mySurface.getRows();
	}

	// Calculators

	/**
	 * Executes the evolution of the world.
	 * 
	 * @return a string with the movements of the cells.
	 */
	public String evolve() {
		ListOfPositions alreadyMovedList = new ListOfPositions();
		ListOfPositions surfacePositions = mySurface.surfacePositions(rows, columns);
		boolean moved;

		surfacePositions.shuffle();
		// Empty the list of movements
		alreadyMovedList.empty(); 
		 // Reset the string of movements
		mySurface.resetMessage();
		for (int i = 0; i < surfacePositions.length(); i++) {

			Position init_pos = surfacePositions.getPosition(i);
			int k = 0;
			moved = false;

			// If there is a cell
			if (!mySurface.availablePosition(init_pos)) {
				while ((k < alreadyMovedList.length()) && !moved) {
					// Look if the cell has already moved
					if (init_pos.isEqual(alreadyMovedList.getPosition(k)))
						moved = true;
					else
						k++;
				}
				// If the cell hasn't moved, now it moves and the position is
				// introduced to the list of movements
				if (!moved) {
					Position final_pos = mySurface.executeMovement(init_pos);
					if (final_pos != null)
						alreadyMovedList.add(final_pos);
				}
			}
		}
		return readMessage().toString();
	}

	/**
	 * Creates a cell in the given position.
	 * 
	 * @param pos
	 *            is the position.
	 * @return a String indicating where the cell has moved.
	 */
	public abstract String createCell(Position pos);

	/**
	 * Deletes the cell located in the given position.
	 * 
	 * @param pos
	 *            is the position where the cell should be.
	 * @return a string indicating if the cell has been deleted or not.
	 */
	public String deleteCell(Position pos) {
		return mySurface.deleteCell(pos);
	}

	/**
	 * Loads a world from a given file.
	 * 
	 * @param file
	 */
	public abstract void loadWorld(Scanner file);

	public abstract void saveWorld(String fileName);

	/**
	 * Saves a world to a given file
	 * 
	 * @param fileName
	 *            is the name of the file.
	 */
	public void saveWorld(PrintWriter file) {
		file.println(rows);
		file.println(columns);
		// Write the surface
		mySurface.saveSurface(file);
	}

	public abstract String toString();
}
