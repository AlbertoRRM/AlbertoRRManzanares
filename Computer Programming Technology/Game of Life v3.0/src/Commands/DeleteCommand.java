package Commands;

import Logic.Position;
import Control.Controller;

public class DeleteCommand implements Command {
	// Attributes

	private Position position;

	// Constructor

	/**
	 * Create a deleteCommand with the given position.
	 * 
	 * @param position.
	 */
	public DeleteCommand(Position position) {
		this.position = position;
	}

	// Methods

	public void execute(Controller controller) {
		if (position != null) {
			System.out.println(controller.deleteCell(position));
			System.out.println(controller.toString());
		}
	}

	public Command parse(String[] commandString) {
		if ((commandString.length == 3) && (commandString[0].equals("delete"))) {
			Position position = null;
			try {
				position = new Position(Integer.parseInt(commandString[1]), Integer.parseInt(commandString[2]));
			} catch (NumberFormatException e) {
				System.err.println("Error. You have not introduced a number.");
				position = null;
			}
			return new DeleteCommand(position);
		} else
			return null;
	}

	public String helpText() {
		return "- Delete X Y: delete the cell at (x, y)\n".toString();
	}
}
