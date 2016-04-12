package Commands;

import Control.Controller;

public interface Command {

	// Methods

	/**
	 * Execute the corresponding command in the world.
	 * 
	 * @param controller
	 *            is where the commands will be executed.
	 */
	public abstract void execute(Controller controller);

	/**
	 * Parse a string looking for the corresponding object of that command.
	 * 
	 * @param commandString
	 *            is the string introduced by the user.
	 * @return an object of the corresponding command.
	 */
	public abstract Command parse(String[] commandString);

	public abstract String helpText();
}
