package Command;
import Logic.Position;
import Logic.ListOfPositions;
import Logic.World;

public class InitializeCommand extends Command {
	//Attributes

	//Constructor
	public InitializeCommand() {
		
	}
	
	//Methods
	public void execute(World world) {
		Position position = new Position(0,0);
		ListOfPositions surfacePositions = world.getSurfacePositions();
		//Shuffle the list of positions
		surfacePositions.shuffle();
		//Create cells in random positions
		for(int i = 0; i < world.getInitSimpleCells(); i++) {
			position = surfacePositions.getPosition(i);
			world.createSimpleCell(position);
		}
		for(int j = world.getInitSimpleCells(); j < world.getInitSimpleCells() + world.getInitComplexCells(); j++) {
			position = surfacePositions.getPosition(j);
			world.createComplexCell(position);
		}
		System.out.println(world.toString());
	}
	
	public Command parse(String[] commandString) {
		if ((commandString.length == 1) && (commandString[0].equals("initialize")))
			return this;
		else
			return null;
	}
	
	public String helpText() {
		
		return "- Initialize: place three simple and two complex cells at random positions in the surface\n".toString();
	}
}
