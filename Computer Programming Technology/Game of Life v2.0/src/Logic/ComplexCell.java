package Logic;

public class ComplexCell extends Cell {
	//Attributes
	private int stepsToBurst;
	//Constructor
	public ComplexCell(int eats) {
		stepsToBurst = eats;
	}
	public Position executeMovement(Position init_pos, Surface surface) {
		Position final_pos = new Position(0, 0);
		ListOfPositions freePositions = surface.availableComplexPositions();
		
		if(!freePositions.empty()) { //If the cell can move
			freePositions.shuffle();
			final_pos = freePositions.getPosition(0);
			
			if(surface.isEdible(final_pos)) { //If there is a simple cell in the position
				surface.moveCell(init_pos, final_pos);
				if (this.getEatenCells() == 0) { //If the cell cannot eat more, it dies
					surface.setMessage("Complex cell at (" + init_pos.getRow() + ", " + init_pos.getColumn() + ") moves to (" + final_pos.getRow() + ", " 
							+ final_pos.getColumn() + ") eating simple cell and bursting.\n");
					surface.deleteCell(final_pos);
					surface.deleteCell(init_pos);
					final_pos = null;
				}
				else { //Else, it eats the simple cell
					this.eatCell();
					surface.deleteCell(init_pos);
					surface.setMessage("Complex cell at (" + init_pos.getRow() + ", " + init_pos.getColumn() + ") moves to (" + final_pos.getRow() + ", " 
							+ final_pos.getColumn() + ") eating simple cell.\n");
				}
			}
			else { //If the position is free, it just moves
				surface.moveCell(init_pos, final_pos);
				surface.deleteCell(init_pos);
				surface.setMessage("Complex cell at (" + init_pos.getRow() + ", " + init_pos.getColumn() + ") moves to (" + final_pos.getRow() + ", " 
						+ final_pos.getColumn() + ").\n");
			}
		} 
		else  //If the cell can't move
			final_pos = null;
		
		return final_pos;
	}
	
	private void eatCell() {
		stepsToBurst -= 1;
	}
	public boolean isEdible(){
		return edible = false;
	}

	private int getEatenCells() {
		return stepsToBurst;
	}
	
	public String toString() {
		return "*";
	}
}
