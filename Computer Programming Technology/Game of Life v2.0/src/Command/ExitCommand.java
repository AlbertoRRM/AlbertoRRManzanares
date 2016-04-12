package Command;
import Logic.World;

public class ExitCommand extends Command {
	//Attributes

	//Constructor
	public ExitCommand() {
		
	}
	
	//Methods
	public void execute(World world) {
		world.setFinishedToTrue();
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
