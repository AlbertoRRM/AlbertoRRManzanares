package Logic;
import java.util.Random;

public class ListOfPositions {
	//Attributes
	private final static int MAX = 20;
	private int counter = 0;
	private Position[] list;
	
	//Constructor
	/**
	 * Creates a new list of positions of size MAX
	 */
	public ListOfPositions() {
		list = new Position[MAX];
	}
	
	//Methods
	/**
	 * Indicates if the list is empty or not
	 * @return a boolean indicating if the list has no elements.
	 */
	public boolean empty() {
		return counter == 0;
	}
	
	/**
	 * Indicates the lenght of the list
	 * @return the number of elements in the list
	 */
	public int length() {
		return counter;
	}
	
	/**
	 * This adds a new position to the list
	 * @param position 
	 * @return boolean indicating if the element has been added or not
	 */
	public boolean add(Position position) {
		boolean ok;
		
		if(counter == MAX)
			ok = false;
		else {
			list[counter] = position;
			counter ++;
			ok = true;
		}
		return ok;
	}
	
	/**
	 * Gets a position from the list
	 * @param i is a position of the list 
	 * @return the position of a given element in the list
	 */
	public Position getPosition(int i) {
		Position position = new Position(0,0);
		position = list[i];
		
		return position;
	}
	
	/**
	 * Swap two positions of the list
	 * @param i is a parameter used to access the list
	 * @param j is a parameter used to access the list
	 */
	private void swap(int i, int j) { 
		Position aux;
		aux = list[i];
		list[i] = list[j];
		list[j] = aux;
	}
	
	/**
	 * Shuffle the positions of the list using random numbers.
	 */
	public void shuffle() {
		Random rn = new Random();
		
		for(int i = counter; i > 1; i--){
			swap(i - 1, rn.nextInt(i));
		}
	}
}
