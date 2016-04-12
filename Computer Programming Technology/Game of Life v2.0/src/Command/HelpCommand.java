package Command;
import Logic.World;

public class HelpCommand extends Command {
	//Attributes

	//Constructor
	public HelpCommand() {
		
	}
	
	//Methods
	public void execute(World world) {
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
