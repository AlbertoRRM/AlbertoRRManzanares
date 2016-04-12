package Commands;

import Control.Controller;

public class SaveFileCommand implements Command {
	// Attributes

	String fileName;

	// Constructor

	/**
	 * Creates a saveFileCommand object.
	 */
	public SaveFileCommand() {

	}
	// Methods

	public void execute(Controller controller) {
		controller.save(fileName);

	}

	public Command parse(String[] commandString) {
		if ((commandString.length == 2) && (commandString[0].equals("save"))) {
			fileName = commandString[1];
			return this;
		}

		else
			return null;
	}

	public String helpText() {
		return "- Save + fileName.txt: Save the current game in the indicated .txt file.\n".toString();
	}
}
