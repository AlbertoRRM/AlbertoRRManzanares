package Command;
import Logic.World;

public class StepCommand extends Command {
	//Attributes

	//Constructor
	public StepCommand() {
		
	}
	
	//Methods
	public void execute(World world) {
		System.out.println(world.evolve());
		System.out.println(world.toString());
	}
	
	public Command parse(String[] commandString) {
		if ((commandString.length == 1) && (commandString[0].equals("step")))
			return this;
		else
			return null;
	}
	
	public String helpText() {
		return "- Step: executes a simulation step\n".toString();
	}
}
