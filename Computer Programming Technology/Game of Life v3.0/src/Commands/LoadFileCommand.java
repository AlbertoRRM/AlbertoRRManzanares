package Commands;

import Control.Controller;

public class LoadFileCommand implements Command {
	// Attributes

	String fileName;

	// Constructor

	/**
	 * Creates a loadFileCommand object.
	 */
	public LoadFileCommand() {

	}
	// Methods

	public void execute(Controller controller) {
		controller.load(fileName);

	}

	public Command parse(String[] commandString) {
		if ((commandString.length == 2) && (commandString[0].equals("load"))) {
			fileName = commandString[1];
			return this;
		}

		else
			return null;
	}

	public String helpText() {
		return "- Load + fileName.txt: Load a game from the given .txt file.\n".toString();
	}
}
