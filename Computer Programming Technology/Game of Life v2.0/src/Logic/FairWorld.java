package Logic;

public class FairWorld implements WorldType {
	//Attributes
		private final int rows = 5, columns = 4;
		private Surface mySurface;
		private boolean finished = false;
		
		//Constructor
		/**
		 * Creates the surface of size rows x columns
		 */
		public FairWorld() {
			mySurface = new Surface(rows, columns);
		}
		
		//Methods
		
		/**
		 * Executes the evolution of the world
		 * @return a string with the movements of the cells
		 */
		public String evolve() {
			ListOfPositions alreadyMovedList = new ListOfPositions();
			ListOfPositions surfacePositions = mySurface.surfacePositions(rows, columns);
			boolean moved;
			
			surfacePositions.shuffle();
			alreadyMovedList.empty(); //Empty the list of movements
			mySurface.resetMessage(); //Reset the string of movements
			for(int i = 0; i < surfacePositions.length(); i++) {
				
				Position init_pos = surfacePositions.getPosition(i);
				int k = 0;
				moved = false;
				if(!mySurface.availablePosition(init_pos)) { //If there is a cell
					while((k < alreadyMovedList.length()) && !moved) { 
						if (init_pos.isEqual(alreadyMovedList.getPosition(k))) //Look if the cell has already moved
							moved = true;
						else
							k++;		
					}
					if (!moved) { //If the cell hasn't moved, now it moves and the position is introduced to the list of movements
						Position final_pos = mySurface.executeMovement(init_pos);
						if(final_pos != null)
							alreadyMovedList.add(final_pos);
					}
				}
			}
			return readMessage().toString();
		}
		
		
		/**
		 * @return a boolean indicating if the execution of the program has finished or not
		 */
		public boolean isFinished() {
			return finished;
		}
		
		/**
		 * Set to true the value of the boolean which indicates if the execution of the program has finished
		 */
		public void setFinishedToTrue() {
			finished = true;
		}
		
		/**
		 * Delete the cell located in the given position
		 * @param pos is the position where the cell should be
		 * @return a string indicating if the cell has been deleted or not
		 */
		public String deleteCell(Position pos) {
			return mySurface.deleteCell(pos);
		}
		
		/**
		 * Get a list of all the surface positions
		 * @return a list of surface positions
		 */
		public ListOfPositions getSurfacePositions() {
			return mySurface.surfacePositions(rows, columns);
		}
		
		/**
		 * @return the number of rows in the surface
		 */
		public int getSurfaceRows() {
			return mySurface.getRows();
		}
		
		/**
		 * @return the number of columns in the surface
		 */
		public int getSurfaceColumns() {
			return mySurface.getColumns();
		}
		
		/**
		 * @return the number of initial simple cells in the surface
		 */
		public int getInitSimpleCells() {
			return mySurface.getInitSimpleCells();
		}
		
		/**
		 * @return the number of initial complex cells in the surface
		 */
		public int getInitComplexCells() {
			return mySurface.getInitComplexCells();
		}
		
		/**
		 * @return a StringBuilder that contains the movements of the cells
		 */
		public StringBuilder readMessage() {
			return mySurface.getMessage();
		}
		
		/**
		 * Create a simple cell in the given position
		 * @param pos is the position where the cell must be created
		 * @return a string indicating whether the cell has been created or not
		 */
		public String createSimpleCell(Position pos) {
			return mySurface.createSimpleCell(pos);
		}
		
		/**
		 * Create a complex cell in the given position
		 * @param pos is the position where the cell must be created
		 * @return a string indicating whether the cell has been created or not
		 */
		public String createComplexCell(Position pos) {
			return mySurface.createComplexCell(pos);
		}
		
		public String toString() {
			return mySurface.toString();
		}
}
