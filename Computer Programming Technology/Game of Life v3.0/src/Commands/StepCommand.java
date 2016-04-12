package Commands;

import Control.Controller;

public class StepCommand implements Command {

	// Attributes

	// Constructor

	/**
	 * Creates a stepCommand object.
	 */
	public StepCommand() {

	}

	// Methods

	public void execute(Controller controller) {
		System.out.println(controller.evolve());
		System.out.println(controller.toString());
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
