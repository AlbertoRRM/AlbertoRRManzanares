package Command;
import Logic.World;
import Logic.Position;

public class CleanCommand extends Command {
	//Attributes

	//Constructor
	public CleanCommand() {
		
	}
	
	public void execute(World world) {
		Position pos;
		for(int i = 0; i < world.getSurfaceRows(); i++) { //Traverse the surface deleting each cell
			for (int j = 0; j < world.getSurfaceColumns(); j++) {
				pos = new Position(i, j);
				world.deleteCell(pos);
			}
		}
		System.out.println(world.toString());
	}
	
	public Command parse(String[] commandString) {
		if ((commandString.length == 1) && (commandString[0].equals("clean")))
			return this;
		else
			return null;
	}
	
	public String helpText() {
		return "- Clean: delete all the cells in the surface\n".toString();
	}
}
