package Logic;

import java.io.*;
import java.util.Scanner;

public class SimpleWorld extends World {

	// Constructor

	/**
	 * Creates an empty simple world.
	 */
	public SimpleWorld() {
		super();
	}

	/**
	 * Creates a simple world with the given parameters.
	 * 
	 * @param rows
	 * @param columns
	 * @param simple
	 *            is the number of initial simple cells.
	 * @param complex
	 *            is the number of initial complex cells, 0 by default.
	 */
	public SimpleWorld(int rows, int columns, int simple, int complex) {
		super(rows, columns);
		this.INIT_SIMPLE_CELLS = simple;
		this.INIT_COMPLEX_CELLS = complex;
		initializeWorld();
	}

	// Methods

	// Initializers

	public void initializeWorld() {
		Position position = new Position(0, 0);
		ListOfPositions surfacePositions = getSurfacePositions();
		// Shuffle the list of positions
		surfacePositions.shuffle();
		// Create cells in random positions
		for (int i = 0; i < INIT_SIMPLE_CELLS; i++) {
			position = surfacePositions.getPosition(i);
			createCell(position);
		}

		System.out.println(toString());
	}

	// Getters

	/**
	 * @return a StringBuilder that contains the movements of the cells
	 */
	public StringBuilder readMessage() {
		return mySurface.getMessage();
	}

	// Calculators

	public String createCell(Position pos) {
		return mySurface.createSimpleCell(pos);
	}

	public void loadWorld(Scanner file) {
		int rows, columns;

		// Read the size of the surface
		rows = file.nextInt();
		columns = file.nextInt();
		this.setRows(rows);
		this.setColumns(columns);
		// Create a new surface with the read parameters and load the surface.
		mySurface = new Surface(rows, columns);
		mySurface.loadSurface(file);
	}

	public void saveWorld(String fileName) {
		PrintWriter file = null;
		try {
			// Create an output stream
			file = new PrintWriter(new BufferedWriter(new FileWriter(fileName)));

			// Write the type of the world and the size of the surface.
			file.println("Simple");
			super.saveWorld(file);

		} catch (IOException e) {
			System.err.println("The file already exist!");
		} finally {
			// Close
			if (file != null)
				file.close();
		}
	}

	public String toString() {
		return mySurface.toString();
	}
}
