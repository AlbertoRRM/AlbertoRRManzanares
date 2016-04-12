package Control;
import java.util.Scanner;

import Logic.World;

public class Controller {
	//Attributes
	private World world;
	private Scanner in;
	
	//Constructor
	/**
	 * Creates a controller which will manage the world and the input.
	 * @param world is where the surface is created
	 * @param in receives the inputs from the keyboard
	 */
	public Controller(World world, Scanner in) {
		this.world = world;
		this.in = in;
	}
	
	//Methods
	/**
	 * Asks the user to enter a command and executes the proper action
	 */
	public void executeSimulation() {
		String command;
		int f, c;
		boolean ok = false;
		
		while(!ok) {
			System.out.println("Command > ");
			command = in.next();
			
			switch(command.toLowerCase()) {
			case "step":
				System.out.println();
				System.out.println(world.evolve());
				System.out.println(world);
				break;
			case "init":
				world.cleanSurface();
				world.initSurface();
				System.out.println(world);
				break;
			case "clean":
				world.cleanSurface();
				System.out.println(world);
				break;
			case "create":
				f = in.nextInt();
				c = in.nextInt();
				if (world.create(f, c)) {
					System.err.println("New cell created at (" + f + "," + c + ")\n" );
					System.out.println(world);
				}
				else {
					System.err.println("Error. Couldn't create the cell, invalid position or it is occupied.\n");
					System.out.println(world);
				}
				break;
			case "delete":
				f = in.nextInt();
				c = in.nextInt();
				if (world.destroy(f, c)) {
					System.out.println("Cell deleted at (" + f + "," + c + ")\n" );
					System.out.println(world);
				}
				else {
					System.err.println("Error. There's no cell at that position, or is an invalid one.\n");
					System.out.println(world);
				}
				break;
			case "help":
				System.out.println("- Step: perform a simulation step. \n"
						+ "- Init: initialise the simulation by cleaning the surface and creating a new one.\n"
						+ "- Clean: Delete all the cells from the surface. \n"
						+ "- Create f c: Create a new cell at position (f,c) on the surface. \n"
						+ "- Delete f c: Delete the cell at position (f,c) on the surface. \n"
						+ "- Exit: Abandon the game.\n");
				break;
			case "exit":
				ok = true;
				break;
			default:
				in.nextLine();
				System.err.println("Error. Command no identified, type 'help' for information");
			}
		}
	}
}
