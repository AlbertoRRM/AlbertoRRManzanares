package Control;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import Commands.Command;
import Commands.CommandParser;
import Exceptions.FileFormatException;
import Exceptions.PositionOutOfBoundsException;
import Logic.World;
import Logic.Position;
import Logic.SimpleWorld;
import Logic.ComplexWorld;
import Logic.ListOfPositions;

public class Controller {

	// Attributes

	private World world;
	private Scanner in;
	boolean finished = false;

	// Constructor

	/**
	 * Creates a controller object.
	 * 
	 * @param world
	 *            is the current one.
	 * @param in
	 *            is the scanner for the input of the system.
	 */
	public Controller(World world, Scanner in) {
		this.world = world;
		this.in = in;
	}

	// Methods

	// Initializers

	/**
	 * Calls the initializeWorld() method from the world class.
	 */
	public void initializeWorld() {
		world.initializeWorld();
	}

	// Setters

	/**
	 * Sets the current world with the given one.
	 * 
	 * @param world
	 *            is the one we want to execute.
	 */
	public void changeWorld(World world) {
		this.world = world;
	}

	/**
	 * Set to true the value of the boolean which indicates if the execution of
	 * the program has finished
	 */
	public void setFinishedToTrue() {
		finished = true;
	}

	// Getters

	/**
	 * Calls the getSurfacePositions() method from the world class.
	 * 
	 * @return a list of the positions of the surface.
	 */
	public ListOfPositions getSurfacePositions() {
		return world.getSurfacePositions();
	}

	/**
	 * Calls the getSurfaceColumns() method from the world class.
	 * 
	 * @return the number of columns in the surface.
	 */
	public int getSurfaceColumns() {
		return world.getSurfaceColumns();
	}

	/**
	 * Calls the getSurfaceRows() method from the world class.
	 * 
	 * @return the number of rows in the surface.
	 */
	public int getSurfaceRows() {
		return world.getSurfaceRows();
	}

	// Calculators

	/**
	 * Executes the simulation of the program by asking a command and executing
	 * its corresponding methods.
	 */
	public void executeSimulation() {
		// Executes the world while the command is not exit.
		while (!finished) { 
			System.out.println("Command > ");
			String str = in.nextLine();
			str.toLowerCase();

			// Parses the command to obtain its object.
			String[] inputWords = str.split(" ");
			Command command = CommandParser.parseCommand(inputWords);

			// Executes the command if it was introduced properly.
			if (command != null) { 
				command.execute(this);
			} else
				System.err.println("Invalid command! Please, type 'help' to know the commands availables.\n");
		}
	}

	/**
	 * Calls the evolve() method from the world class.
	 * 
	 * @return a String indicating all the movements of the cells.
	 */
	public String evolve() {
		return world.evolve();
	}

	/**
	 * Creates a cell in the given position.
	 * 
	 * @param pos
	 *            is the position.
	 * @return a String indicating the position where it has been created or
	 *         "Try again" if it was an invalid position.
	 */
	public String createCell(Position pos) {
		try {
			if ((pos.getRow() < 0) || (pos.getRow() >= world.getSurfaceRows()) || (pos.getColumn() < 0)
					|| (pos.getColumn() >= world.getSurfaceColumns()))
				throw new PositionOutOfBoundsException();
			else
				return world.createCell(pos);
		} catch (PositionOutOfBoundsException e) {
			System.err.println("The given position is out of the surface.");
		}
		return "Try again";
	}

	/**
	 * Deletes a cell from the given position.
	 * 
	 * @param pos
	 *            is the position.
	 * @return a String indicating the position where it has been deleted or
	 *         "Try again" if it was an invalid position.
	 */
	public String deleteCell(Position pos) {
		try {
			if ((pos.getRow() < 0) || (pos.getRow() >= world.getSurfaceRows()) || (pos.getColumn() < 0)
					|| (pos.getColumn() >= world.getSurfaceColumns()))
				throw new PositionOutOfBoundsException();
			else
				return world.deleteCell(pos);
		} catch (PositionOutOfBoundsException e) {
			System.err.println("The given position is out of the surface.");
		}
		return "Try again";
	}

	/**
	 * Loads a game from a given file.
	 * 
	 * @param fileName
	 *            is the name of the file.
	 */
	public void load(String fileName) {
		Scanner file = null;
		String worldType;
		try {
			// Create an input stream
			file = new Scanner(new BufferedReader(new FileReader(fileName)));
			World world;

			// Read
			worldType = file.nextLine();
			 // If Simple, create a simple world.
			if (worldType.equals("Simple")) {
				world = new SimpleWorld();
				world.loadWorld(file);
				this.world = world;
			} else if (worldType.equals("Complex")) { // If complex, create a complex world.
				world = new ComplexWorld();
				world.loadWorld(file);
				this.world = world;
			} else
				throw new FileFormatException();
		} catch (IOException e) {
			System.err.println("The file does not exist!");
		} catch (FileFormatException e) {
			System.err.println("Invalid file");
		} finally {
			// Close
			if (file != null) {
				file.close();
				System.out.println(toString());
			}
		}
	}

	/**
	 * Calls the saveWorld() method from the world class.
	 * 
	 * @param fileName
	 *            is the name of the saved file.
	 */
	public void save(String fileName) {
		world.saveWorld(fileName);
	}

	public String toString() {
		return world.toString();
	}
}
