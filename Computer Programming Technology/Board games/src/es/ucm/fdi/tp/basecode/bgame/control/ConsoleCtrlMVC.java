package es.ucm.fdi.tp.basecode.bgame.control;

import java.util.List;
import java.util.Scanner;

import es.ucm.fdi.tp.basecode.bgame.control.commands.Command;
import es.ucm.fdi.tp.basecode.bgame.control.commands.CommandSet;
import es.ucm.fdi.tp.basecode.bgame.model.Game;
import es.ucm.fdi.tp.basecode.bgame.model.GameError;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;
import es.ucm.fdi.tp.basecode.bgame.model.Game.State;

/**
 * A controller that uses MVC. The difference from {@link ConsoleCtrl} is that
 * it does not print any output related to the game (only user interaction
 * errors).
 * 
 * <p>
 * Controlador que usa MVC. Se diferencia de {@link ConsoleCtrl} en que no
 * escribe ningun resultado relacionado con el juego (solo los errores de
 * interaccion con el usuario).
 */
public class ConsoleCtrlMVC extends ConsoleCtrl {

	public ConsoleCtrlMVC(Game g, List<Piece> pieces, List<Player> players, Scanner in) {
		super(g, pieces, players, in);
	}

	@Override
	public void start() {

		// start the game
		game.start(pieces);

		while (game.getState() == State.InPlay) {

			// get a line from the user
			System.out.println();
			System.out.print("Please type a command ('help' for usage info.): ");
			String line = in.nextLine().trim();

			// parse and execute the command
			Command cmd = CommandSet.parse(line);
			if (cmd != null) {
				try {
					cmd.execute(this);
				} catch (GameError e) {
				}
			} else {
				System.err.println("Uknown command: " + line);
				System.err.flush();
			}
		}

	}

}
