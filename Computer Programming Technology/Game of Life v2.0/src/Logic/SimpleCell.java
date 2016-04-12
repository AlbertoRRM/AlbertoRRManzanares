package Logic;

public class SimpleCell extends Cell {
	//Attributes
	private int stepsToDie;
	private int stepsToMature;
	private final int MAX_STEPS_WITHOUT_MOVING, REPRODUCTION_STEPS;
	
	//Constructor
	public SimpleCell(int life, int immaturity) {
		MAX_STEPS_WITHOUT_MOVING = stepsToDie = life;
		REPRODUCTION_STEPS = stepsToMature = immaturity;
	}
	
	//Methods
	/**
	 * Decreases one cell's life
	 */
	public void decreaseCellLife() {
		stepsToDie-= 1;
	}
	/**
	 * Decreases a step to become mature
	 */
	public void decreaseCellImmature() {
		stepsToMature-= 1;
	}
	
	/**
	 * This method will reset the maturity of a cell
	 */
	public void resetMaturity() {
		stepsToMature = REPRODUCTION_STEPS;
	}
	
	/**
	 * This method will be called if a cell moves in the surface
	 */
	public void cellStep() {
		stepsToDie = MAX_STEPS_WITHOUT_MOVING;
	}
	
	/**
	 * Indicates the number of steps remaining to die of a cell
	 * @return the steps remaining to die of a cell
	 */
	public int getLife() {
		return stepsToDie;
	}
	
	/**
	 * Indicates the number of steps before becoming mature of a cell
	 * @return the steps remaining to become mature of a cell
	 */
	public int getMaturity() {
		return stepsToMature;
	}
	
	public Position executeMovement(Position init_pos, Surface surface) {
		Position final_pos = new Position(0, 0);
		ListOfPositions neighbours = init_pos.neighbours(surface.getRows(), surface.getColumns());
		ListOfPositions freeNeighbours = surface.availableNeighbours(neighbours);
		
		if(!freeNeighbours.empty()) { //If the cell can move
			if(this.getMaturity() == 0) { //If it is mature, it divides
				this.resetMaturity();
				this.cellStep();
				freeNeighbours.shuffle(); //Shuffle list of available neighbours
				final_pos = freeNeighbours.getPosition(0); //Get the first position of that list
				surface.moveCell(init_pos, final_pos);
				surface.setMessage("Simple cell at (" + init_pos.getRow() + ", " + init_pos.getColumn() + ") divided creating new cells at "
						+ "(" + init_pos.getRow() + ", " + init_pos.getColumn() + ") and (" + final_pos.getRow() + 
						", " + final_pos.getColumn() + ").\n");
			}
			else { //If not, delete previous position
				freeNeighbours.shuffle(); //Shuffle list of available neighbours
				final_pos = freeNeighbours.getPosition(0); //Get the first position of that list
				surface.setMessage("Simple cell at (" + init_pos.getRow() + ", " + init_pos.getColumn() + ") moves to (" + final_pos.getRow() + ", " 
						+ final_pos.getColumn() + ").\n");
				surface.moveCell(init_pos, final_pos);
				this.decreaseCellImmature();
				surface.deleteCell(init_pos);
			}
			
		}
		else { //If the cell can't move
			if((this.getLife() == 0) || (this.getMaturity() == 0)) { //It dies
				if(this.getLife() == 0)
					surface.setMessage("Simple cell at (" + init_pos.getRow() + ", " + init_pos.getColumn() + ") dies of inactivity.\n");
				else
					surface.setMessage("Simple cell at (" + init_pos.getRow() + ", " + init_pos.getColumn() + ") dies for being unable to reproduce.\n");
				surface.deleteCell(init_pos);
			}
			else { //Decrease its attributes
				decreaseCellLife();
				decreaseCellImmature();	
			}
			final_pos = null;
		}
		
		return final_pos;
	}
	
	public boolean isEdible(){
		return edible = true;
	}

	public String toString() {
		return "X";
	}
}
