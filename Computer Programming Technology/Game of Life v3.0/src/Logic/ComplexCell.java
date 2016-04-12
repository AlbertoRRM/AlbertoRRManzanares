package Logic;

import java.util.Scanner;

public class ComplexCell implements Cell {

	// Attributes

	private int stepsToBurst;
	protected boolean edible;

	// Constructor

	/**
	 * Creates a complex cell.
	 * 
	 * @param eats
	 *            is the number of times the cell can eat another one.
	 */
	public ComplexCell(int eats) {
		stepsToBurst = eats;
	}

	// Methods

	// Setters

	/**
	 * Reduces the stepsToBurst of a cell.
	 */
	private void eatCell() {
		stepsToBurst -= 1;
	}
	// Getters

	public boolean isEdible() {
		return edible = false;
	}

	/**
	 * @return the stepsToBurst of a cell.
	 */
	private int getEatenCells() {
		return stepsToBurst;
	}

	// Calculators

	public Position executeMovement(Position init_pos, Surface surface) {
		Position final_pos = new Position(0, 0);
		ListOfPositions freePositions = surface.availableComplexPositions();

		// If the cell can move
		if (!freePositions.empty()) { 
			freePositions.shuffle();
			final_pos = freePositions.getPosition(0);

			// If there is a simple cell in the position
			if (surface.isEdible(final_pos)) { 
				surface.moveCell(init_pos, final_pos);
				// If the cell cannot eat more, it dies
				if (this.getEatenCells() == 0) { 
					surface.setMessage("Complex cell at (" + init_pos.getRow() + ", " + init_pos.getColumn()
							+ ") moves to (" + final_pos.getRow() + ", " + final_pos.getColumn()
							+ ") eating simple cell and bursting.\n");
					surface.deleteCell(final_pos);
					surface.deleteCell(init_pos);
					final_pos = null;
				} else { // Else, it eats the simple cell
					this.eatCell();
					surface.deleteCell(init_pos);
					surface.setMessage(
							"Complex cell at (" + init_pos.getRow() + ", " + init_pos.getColumn() + ") moves to ("
									+ final_pos.getRow() + ", " + final_pos.getColumn() + ") eating simple cell.\n");
				}
			} else { // If the position is free, it just moves
				surface.moveCell(init_pos, final_pos);
				surface.deleteCell(init_pos);
				surface.setMessage("Complex cell at (" + init_pos.getRow() + ", " + init_pos.getColumn()
						+ ") moves to (" + final_pos.getRow() + ", " + final_pos.getColumn() + ").\n");
			}
		} else // If the cell can't move
			final_pos = null;

		return final_pos;
	}

	public ComplexCell loadCell(Scanner file) {
		return new ComplexCell(file.nextInt());
	}

	public String saveCell() {
		return ("complex " + this.stepsToBurst);
	}

	public String toString() {
		return "*";
	}
}
