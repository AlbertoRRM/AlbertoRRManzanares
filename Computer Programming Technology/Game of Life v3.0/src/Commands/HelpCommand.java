package Commands;

import Control.Controller;

public class HelpCommand implements Command {
	// Attributes

	// Constructor

	/**
	 * Creates a helpCommand object.
	 */
	public HelpCommand() {

	}

	// Methods

	public void execute(Controller controller) {
		System.out.println(CommandParser.helpTextCommands());
	}

	public Command parse(String[] commandString) {
		if ((commandString.length == 1) && (commandString[0].equals("help")))
			return this;
		else
			return null;
	}

	public String helpText() {
		return "- Help: show the information of all the commands availables\n".toString();
	}
}
