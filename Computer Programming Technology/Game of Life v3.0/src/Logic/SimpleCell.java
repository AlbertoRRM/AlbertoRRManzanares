package Logic;

import java.util.Scanner;

public class SimpleCell implements Cell {

	// Attributes

	private int stepsToDie;
	private int stepsToMature;
	private final int MAX_STEPS_WITHOUT_MOVING, REPRODUCTION_STEPS;
	protected boolean edible;

	// Constructor

	/**
	 * Creates a cell with the given parameters.
	 * 
	 * @param life
	 *            is the number of steps before dying.
	 * @param immaturity
	 *            is the number of steps before becoming mature.
	 */
	public SimpleCell(int life, int immaturity) {
		MAX_STEPS_WITHOUT_MOVING = stepsToDie = life;
		REPRODUCTION_STEPS = stepsToMature = immaturity;
	}

	// Methods

	// Initializers 
	
	/**
	 * Resets the maturity of a cell to the initial values.
	 */
	public void resetMaturity() {
		stepsToMature = REPRODUCTION_STEPS;
	}

	/**
	 * Resets the life of a cell to the initial values.
	 */
	public void cellStep() {
		stepsToDie = MAX_STEPS_WITHOUT_MOVING;
	}
	
	// Setters

	/**
	 * Decreases one cell's life.
	 */
	public void decreaseCellLife() {
		stepsToDie -= 1;
	}

	/**
	 * Decreases a step to become mature.
	 */
	public void decreaseCellImmature() {
		stepsToMature -= 1;
	}

	// Getters

	public boolean isEdible() {
		return edible = true;
	}

	/**
	 * Indicates the number of steps remaining to die of a cell.
	 * 
	 * @return the steps remaining to die of a cell.
	 */
	public int getLife() {
		return stepsToDie;
	}

	/**
	 * Indicates the number of steps before becoming mature of a cell.
	 * 
	 * @return the steps remaining to become mature of a cell.
	 */
	public int getMaturity() {
		return stepsToMature;
	}

	// Calculators
	public Position executeMovement(Position init_pos, Surface surface) {
		Position final_pos = new Position(0, 0);
		ListOfPositions neighbours = init_pos.neighbours(surface.getRows(), surface.getColumns());
		ListOfPositions freeNeighbours = surface.availableNeighbours(neighbours);
		
		// If the cell can move
		if (!freeNeighbours.empty()) { 
			// If it is mature, it divides
			if (this.getMaturity() == 0) { 
				this.resetMaturity();
				this.cellStep();
				// Shuffle list of available neighbors
				freeNeighbours.shuffle(); 
				// Get the first position of that list
				final_pos = freeNeighbours.getPosition(0);
				surface.moveCell(init_pos, final_pos);
				surface.setMessage("Simple cell at (" + init_pos.getRow() + ", " + init_pos.getColumn()
						+ ") divided creating new cells at " + "(" + init_pos.getRow() + ", " + init_pos.getColumn()
						+ ") and (" + final_pos.getRow() + ", " + final_pos.getColumn() + ").\n");
			} else { // If not, delete previous position
				// Shuffle list of available neighbors
				freeNeighbours.shuffle();
				// Get the first position of that list
				final_pos = freeNeighbours.getPosition(0);
				surface.setMessage("Simple cell at (" + init_pos.getRow() + ", " + init_pos.getColumn() + ") moves to ("
						+ final_pos.getRow() + ", " + final_pos.getColumn() + ").\n");
				surface.moveCell(init_pos, final_pos);
				this.decreaseCellImmature();
				surface.deleteCell(init_pos);
			}

		} else { // If the cell can't move
			if ((this.getLife() == 0) || (this.getMaturity() == 0)) { // It dies
				if (this.getLife() == 0)
					surface.setMessage("Simple cell at (" + init_pos.getRow() + ", " + init_pos.getColumn()
							+ ") dies of inactivity.\n");
				else
					surface.setMessage("Simple cell at (" + init_pos.getRow() + ", " + init_pos.getColumn()
							+ ") dies for being unable to reproduce.\n");
				surface.deleteCell(init_pos);
			} else { // Decrease its attributes
				decreaseCellLife();
				decreaseCellImmature();
			}
			final_pos = null;
		}

		return final_pos;
	}

	public Cell loadCell(Scanner file) {
		return new SimpleCell(file.nextInt(), file.nextInt());
	}

	public String saveCell() {
		// Print a line of text
		return ("simple " + this.stepsToDie + " " + this.stepsToMature);
	}

	public String toString() {
		return "X";
	}
}
