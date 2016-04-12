package Commands;

import Control.Controller;
import Logic.Position;
import java.lang.String;

public class CreateCommand implements Command {

	// Attributes

	Position position;

	// Constructor

	/**
	 * Creates a createCommand object with the given position.
	 * 
	 * @param position.
	 */
	public CreateCommand(Position position) {
		this.position = position;
	}

	// Methods

	public void execute(Controller controller) {
		if (position != null) {
			System.out.println(controller.createCell(position));
			System.out.println(controller.toString());
		}
	}

	public Command parse(String[] commandString) {
		if ((commandString.length == 3) && (commandString[0].equals("create"))) {
			Position position = null;
			try {
				position = new Position(Integer.parseInt(commandString[1]), Integer.parseInt(commandString[2]));
			} catch (NumberFormatException e) {
				System.err.println("Error. You have not introduced a number.");
				position = null;
			}
			return new CreateCommand(position);
		} else
			return null;
	}

	public String helpText() {
		return "- Create X Y: create a cell at (x, y)\n".toString();
	}
}
