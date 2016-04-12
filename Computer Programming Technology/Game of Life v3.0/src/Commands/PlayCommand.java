package Commands;

import Control.Controller;
import Exceptions.InitialisationException;
import Logic.World;
import Logic.SimpleWorld;
import Logic.ComplexWorld;

public class PlayCommand implements Command {

	// Attributes

	private World PlayCommandWorld;

	// Constructor

	/**
	 * Creates a playCommand object with the given world.
	 * 
	 * @param world.
	 */
	public PlayCommand(World world) {
		PlayCommandWorld = world;
	}

	// Methods

	public void execute(Controller controller) {
		controller.changeWorld(PlayCommandWorld);
	}

	public Command parse(String[] commandString) {
		if ((commandString.length == 5) && (commandString[0].equals("play")) && (commandString[1].equals("simple"))) {
			Integer rows = Integer.parseInt(commandString[2]);
			Integer columns = Integer.parseInt(commandString[3]);
			Integer initSimpleCells = Integer.parseInt(commandString[4]);
			try {
				if (initSimpleCells > rows * columns) {
					initSimpleCells = 0;
					throw new InitialisationException();
				}
			} catch (InitialisationException e) {
				System.err.println("The number of initial cells is greater than the number of positions");
			}
			return new PlayCommand(new SimpleWorld(rows, columns, initSimpleCells, 0));

		} else if ((commandString.length == 6) && (commandString[0].equals("play"))
				&& (commandString[1].equals("complex"))) {
			Integer rows = Integer.parseInt(commandString[2]);
			Integer columns = Integer.parseInt(commandString[3]);
			Integer initSimpleCells = Integer.parseInt(commandString[4]);
			Integer initComplexCells = Integer.parseInt(commandString[5]);
			try {
				if ((initSimpleCells + initComplexCells) > (rows * columns)) {
					initSimpleCells = 0;
					initComplexCells = 0;
					throw new InitialisationException();
				}
			} catch (InitialisationException e) {
				System.err.println("The number of initial cells is greater than the number of positions");
			}
			return new PlayCommand(new ComplexWorld(rows, columns, initSimpleCells, initComplexCells));
		} else
			return null;
	}

	public String helpText() {
		return "- Play: Choose between simple and complex game. Then introduce the dimension and the init cells.\n"
				.toString();
	}
}
