package Command;
import Logic.Position;
import Logic.World;

public class CreateComplexCommand extends Command {
	//Attributes
	Position position;
	//Constructor
	public CreateComplexCommand(Position position) {
		 this.position = position;
	}
	
	//Methods
	public void execute(World world) {
		System.out.println(world.createComplexCell(position));
		System.out.println(world.toString());
	}
	
	public Command parse(String[] commandString) {
		if ((commandString.length == 3) && (commandString[0].equals("createcomplex"))) {
			Position position = new Position(Integer.parseInt(commandString[1]), Integer.parseInt(commandString[2]));
			return new CreateComplexCommand(position);
		}
		else
			return null;
	}
	
	public String helpText() {
		return "- CreateComplex X Y: create a complex cell at (x, y)\n".toString();
	}
}
