package Control;
import java.util.Scanner;
import Logic.World;

import Command.Command;
import Command.CommandParser;

public class Controller {
	//Attributes
	private World world;
	private Scanner in;
	//Constructor
	/**
	 * 
	 * @param world
	 * @param in
	 */
	public Controller(World world, Scanner in) {
		this.world = world;
		this.in = in;
	}
	
	//Methods
	/**
	 * Execute the simulation of the program by asking a command and executing its corresponding methods
	 */
	public void executeSimulation() {
		while(!world.isFinished()) { //Execute the world while the command is not exit
			System.out.println("Command > ");
			String str = in.nextLine();
			str.toLowerCase();
			
			String[] inputWords = str.split(" ");
			Command command = CommandParser.parseCommand(inputWords);
			
			if (command != null) {
				command.execute(this.world);
			}
			else 
				System.err.println("Invalid command! Please, type 'help' to know the commands availables.\n");
		}
	}
}
