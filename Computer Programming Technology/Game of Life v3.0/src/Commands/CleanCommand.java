package Commands;

import Logic.Position;
import Control.Controller;

public class CleanCommand implements Command {

	// Constructor

	/**
	 * Creates a cleanCommand object.
	 */
	public CleanCommand() {

	}

	public void execute(Controller controller) {
		Position pos;

		// Traverse the surface deleting each cell
		for (int i = 0; i < controller.getSurfaceRows(); i++) {
			for (int j = 0; j < controller.getSurfaceColumns(); j++) {
				pos = new Position(i, j);
				controller.deleteCell(pos);
			}
		}
		System.out.println(controller.toString());
	}

	public Command parse(String[] commandString) {
		if ((commandString.length == 1) && (commandString[0].equals("clean")))
			return this;
		else
			return null;
	}

	public String helpText() {
		return "- Clean: delete all the cells in the surface\n".toString();
	}
}
