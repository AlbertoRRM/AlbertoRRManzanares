package Command;
import java.lang.StringBuilder;

public class CommandParser {
	//Attributes
	static Command[] availableCommands = { new InitializeCommand(), new StepCommand(), new CleanCommand(),
			new CreateSimpleCommand(null), new CreateComplexCommand(null), new DeleteCommand(null), new HelpCommand(),
			new ExitCommand() };
	
	//Constructor
	public CommandParser() {
		
	}
	
	//Methods
	/**
	 * Construct a string with all the help text of each command
	 * @return a string containing all the help text of each command
	 */
	static public String helpTextCommands() {
		StringBuilder textCommands = new StringBuilder();
		
		textCommands.append("Available commands: \n");
		for (int i = 0; i < availableCommands.length; i++) { //It adds all the help text of each command in a StringBuilder
			textCommands.append(availableCommands[i].helpText());
		}
		
		return textCommands.toString();
	}
	
	/**
	 * Parse the String[ ] passed as a parameter and returns 
	 * @param commandString is the String[ ] of commands
	 * @return an object of the corresponding derived class of Command or null, if the input string doesn't correspond to any of the known commands
	 */
	static public Command parseCommand(String[] commandString) {
		int i = 0;
		
		while (i < availableCommands.length) {
			if (availableCommands[i].parse(commandString) != null) {
				return availableCommands[i].parse(commandString);
			}
			else
				i++;
		}
		
		return null;
	}
	
	
}
