package Commands;

import Control.Controller;

public class ExitCommand implements Command {
	// Attributes

	// Constructor

	/**
	 * Creates an exitCommand object.
	 */
	public ExitCommand() {

	}

	// Methods

	public void execute(Controller controller) {
		controller.setFinishedToTrue();
	}

	public Command parse(String[] commandString) {
		if ((commandString.length == 1) && (commandString[0].equals("exit")))
			return this;
		else
			return null;
	}

	public String helpText() {
		return "- Exit: abandon the game\n".toString();
	}
}
