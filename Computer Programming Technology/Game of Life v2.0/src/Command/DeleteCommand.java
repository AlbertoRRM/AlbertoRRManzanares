package Command;
import Logic.Position;
import Logic.World;

public class DeleteCommand extends Command {
	//Attributes
	private Position position;
	
	//Constructor
	public DeleteCommand(Position position) {
		this.position = position;
	}
	
	//Methods
	public void execute(World world) {
		System.out.println(world.deleteCell(position));
		System.out.println(world.toString());
	}
	
	public Command parse(String[] commandString) {
		if ((commandString.length == 3) && (commandString[0].equals("delete"))) {
			Position position = new Position(Integer.parseInt(commandString[1]), Integer.parseInt(commandString[2]));
			return new DeleteCommand(position);
		}
		else
			return null;
	}
	
	public String helpText() {
		return "- Delete X Y: delete the cell at (x, y)\n".toString();
	}
}
