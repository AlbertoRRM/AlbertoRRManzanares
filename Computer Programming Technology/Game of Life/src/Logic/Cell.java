package Logic;
public class Cell {
	
	//Attributes
	private int stepsToDie;
	private int stepsToMature;
	private final int MAX_STEPS_WITHOUT_MOVING, REPRODUCTION_STEPS;
	
	//Constructor
	/**
	 * Creates a cell with a specified life (steps remaining to die) and
	 * a specified immaturity (steps remaining to be mature).
	 * @param life is the number of steps before dying
	 * @param immaturity is the number of steps before becoming mature
	 */
	public Cell (int life, int immaturity) { 
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
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		String cell;
		
		cell = stepsToDie + "-" + stepsToMature;
		
		return cell;
	}
}
