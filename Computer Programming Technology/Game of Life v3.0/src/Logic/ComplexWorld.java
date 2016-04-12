package Logic;

import java.io.*;
import java.util.Scanner;

public class ComplexWorld extends World {

	// Constructor

	/**
	 * Creates an empty complex world.
	 */
	public ComplexWorld() {
		super();
	}

	/**
	 * Creates a complex world with the given parameters.
	 * 
	 * @param rows
	 * @param columns
	 * @param simple
	 *            is the number of the initial simple cells.
	 * @param complex
	 *            is the number of the initial complex cells.
	 */
	public ComplexWorld(int rows, int columns, int simple, int complex) {
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
			createSimpleCell(position);
		}
		for (int j = INIT_SIMPLE_CELLS; j < INIT_SIMPLE_CELLS + INIT_COMPLEX_CELLS; j++) {
			position = surfacePositions.getPosition(j);
			createComplexCell(position);
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
		if (mySurface.validPosition(pos)) {
			// The user has to choose between complex or simple cell
			Scanner inn = new Scanner(System.in);
			System.out.println("Which type of cell: Complex (1) or simple (2): ");
			Integer num = inn.nextInt();

			if (num == 1)
				return createComplexCell(pos);
			else if (num == 2)
				return createSimpleCell(pos);
			else
				return "Invalid type of cell";
		} else
			return "Invalid position!";
	}

	/**
	 * Calls the createSimpleCell from the surface class.
	 * 
	 * @param pos
	 *            is the position where the cell must be created.
	 * @return a string indicating whether the cell has been created or not.
	 */
	public String createSimpleCell(Position pos) {
		return mySurface.createSimpleCell(pos);
	}

	/**
	 * Calls the createComplexCell method from the surface class.
	 * 
	 * @param pos
	 *            is the position where the cell must be created.
	 * @return a string indicating whether the cell has been created or not.
	 */
	public String createComplexCell(Position pos) {
		return mySurface.createComplexCell(pos);
	}

	public void loadWorld(Scanner file) {
		int rows, columns;

		// Read the size of the surface.
		rows = file.nextInt();
		columns = file.nextInt();
		this.setRows(rows);
		this.setColumns(columns);
		// Create a new surface with the read parameters and load it.
		mySurface = new Surface(rows, columns);
		mySurface.loadSurface(file);
	}

	public void saveWorld(String fileName) {
		PrintWriter file = null;
		try {
			// Create an output stream
			file = new PrintWriter(new BufferedWriter(new FileWriter(fileName)));

			// Write the type of the world and the size of the surface.
			file.println("Complex");
			super.saveWorld(file);
		} catch (IOException e) {
			System.err.println("Unable to save the world");
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
