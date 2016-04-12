package Command;
import Logic.World;

public abstract class Command {
	//Attributes
	
	//Constructor
	public Command() {
		
	}
	
	//Methods
	/**
	 * Execute the corresponding command in the world
	 * @param world is where the commands will be executed
	 */
	public abstract void execute(World world);
	/**
	 * Parse a string looking for the corresponding object of that command
	 * @param commandString is the string introduced by the user
	 * @return an object of the corresponding command
	 */
	public abstract Command parse(String[] commandString);
	public abstract String helpText();
}
