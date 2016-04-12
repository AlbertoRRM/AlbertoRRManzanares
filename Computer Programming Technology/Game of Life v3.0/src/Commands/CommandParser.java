package Commands;

import java.lang.StringBuilder;

public class CommandParser {

	// Attributes

	static Command[] availableCommands = { new StepCommand(), new CleanCommand(), new PlayCommand(null),
			new SaveFileCommand(), new LoadFileCommand(), new CreateCommand(null), new DeleteCommand(null),
			new HelpCommand(), new ExitCommand() };

	// Constructor

	/**
	 * Creates a commandParser object.
	 */
	public CommandParser() {

	}

	// Methods

	/**
	 * Construct a string with all the help text of each command.
	 * 
	 * @return a string containing all the help text of each command.
	 */
	static public String helpTextCommands() {
		StringBuilder textCommands = new StringBuilder();

		textCommands.append("Available commands: \n");
		// It adds all the help text of each command in a StringBuilder
		for (int i = 0; i < availableCommands.length; i++) {
			textCommands.append(availableCommands[i].helpText());
		}

		return textCommands.toString();
	}

	/**
	 * Parse the String[ ] passed as a parameter and returns .
	 * 
	 * @param commandString
	 *            is the String[ ] of commands.
	 * @return an object of the corresponding derived class of Command or null,
	 *         if the input string doesn't correspond to any of the known
	 *         commands.
	 */
	static public Command parseCommand(String[] commandString) {
		int i = 0;
		Command command = null;

		while (i < availableCommands.length) {
			command = availableCommands[i].parse(commandString);
			if (command != null) {
				return command;
			} else
				i++;
		}

		return null;
	}
}
