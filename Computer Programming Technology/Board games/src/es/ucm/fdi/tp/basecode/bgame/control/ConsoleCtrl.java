package es.ucm.fdi.tp.basecode.bgame.control;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import es.ucm.fdi.tp.basecode.bgame.control.commands.Command;
import es.ucm.fdi.tp.basecode.bgame.control.commands.CommandSet;
import es.ucm.fdi.tp.basecode.bgame.control.commands.HelpCommand;
import es.ucm.fdi.tp.basecode.bgame.control.commands.PlayCommand;
import es.ucm.fdi.tp.basecode.bgame.control.commands.QuitCommand;
import es.ucm.fdi.tp.basecode.bgame.control.commands.RestartCommand;
import es.ucm.fdi.tp.basecode.bgame.model.Game;
import es.ucm.fdi.tp.basecode.bgame.model.GameError;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;
import es.ucm.fdi.tp.basecode.bgame.model.Game.State;

/**
 * A console controller that does not use MVC -- you can see this in the
 * {@link #start()} method since it prints the state of the game in each
 * iteration of the while loop, etc.
 * 
 * <p>
 * Clase controlador que no usa MVC -- Se puede ver en el metodo
 * {@link #start()} que imprime el estado del juego en cada iteracion del bucle
 * while, etc.
 */
public class ConsoleCtrl extends Controller {

	/**
	 * A map that associates pieces with players (manual, random, etc.).
	 * 
	 * <p>
	 * Map que asocia fichas con jugadores (manual, random, etc.).
	 */
	protected Map<Piece, Player> players;

	/**
	 * A scanner from which we ask for command (e.g., play, quit, etc.).
	 * 
	 * <p>
	 * Scanner desde el que se solicitan comandos (por ejemplo, play, quit,
	 * etc.).
	 */
	protected Scanner in;

	/**
	 * Construct a controller for playing {@code game}, with a a list of
	 * {@code pieces} where the i-th piece should be played by the i-th player
	 * in the list {@code players}. Thus, the lists of {@code pieces} and
	 * {@code players} should be of the same length.
	 * 
	 * <p>
	 * Construye un controlador para jugar al juego {@code game} con una lista
	 * de fichas {@code pieces} en la que la i-esima ficha es la que utiliza el
	 * i-esimo jugador de la lista de jugadores {@code players}. Por tanto,
	 * ambas listas deben ser de la misma longitud.
	 * 
	 * @param game
	 *            A game.
	 * @param pieces
	 *            A list of pieces.
	 * @param players
	 *            A list of players.
	 * @param in
	 *            A scanner from which we ask for a string representing a
	 *            command (e.g., play, quit, etc.)
	 */
	public ConsoleCtrl(Game game, List<Piece> pieces, List<Player> players, Scanner in) {
		super(game, pieces);

		// generate a HashMap that associates pieces with players.
		this.players = new HashMap<Piece, Player>();
		for (int i = 0; i < pieces.size(); i++) {
			this.players.put(pieces.get(i), players.get(i));
		}

		// Save the scanner for future use.
		this.in = in;

		// define the supported commands.
		initializeCommandsSet();

	}

	/**
	 * We override {@link Controller#makeMove(Player)} to ignore its parameter
	 * and fetch the player from the {@link #players} table.
	 * 
	 * <p>
	 * Se sobrescribe {@link Controller#makeMove(Player)} para ignorar su
	 * parametro y obtener el jugador de la tabla de jugadores.
	 * 
	 * @param p
	 *            Typically {@code null} since it is ignored.
	 *            <p>
	 *            Normalmente es {@code null} pues se ignora.
	 */
	@Override
	public void makeMove(Player p) {
		game.makeMove(players.get(game.getTurn()));
	};

	/**
	 * Initializes the set of command in {@link CommandSet}. It is called in the
	 * constructor {@link ConsoleCtrl#ConsoleCtrl(Game, List, List, Scanner)}.
	 * You can override it to change the set of supported commands.
	 * 
	 * <p>
	 * Inicializa la lista de comandos en {@link CommandSet}. Este metodo es
	 * llamado desde el constructor
	 * {@link ConsoleCtrl#ConsoleCtrl(Game, List, List, Scanner)}. Se puede
	 * sobrescribir para cambiar el conjunto de comandos permitidos.
	 */
	protected void initializeCommandsSet() {
		CommandSet.addCommand(new PlayCommand());
		CommandSet.addCommand(new RestartCommand());
		CommandSet.addCommand(new HelpCommand());
		CommandSet.addCommand(new QuitCommand());
	}

	/**
	 * Starts the controller, which includes a loop that asks for one command in
	 * each iteration until either the game is over or the user ask to 'quit'
	 * the game.
	 * 
	 * <p>
	 * Arranca el controlador, que incluye un bucle que pide una accion en cada
	 * iteracion hasta que el juego termina o el usuario solicita terminar juego
	 * con el comando 'quit'.
	 */
	@Override
	public void start() {
		if (game == null || pieces == null) {
			throw new GameError("There is no game or pieces to start");
		}

		// start the game
		game.start(pieces);

		// print the starting message
		System.out.println("Starting '" + game.gameDesc() + "'");
		System.out.println();

		// we loop asking for command as far as the game state is {@link
		// State.InPlay}
		//
		while (game.getState() == State.InPlay) {

			// print the current status of the game (board, turn, etc.)
			// NO MVC!
			System.out.println();
			System.out.println(game);
			System.out.println();
			System.out.println("Turn for " + game.getTurn());

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
					System.err.println("Error: " + e.getLocalizedMessage());
					System.err.flush();
				}
			} else {
				System.err.println("Uknown command: " + line);
				System.err.flush();
			}
		}

		// Print the final status of the game
		//
		System.out.println("Game Over!!");
		System.out.println();
		System.out.println(game);
	}

}
