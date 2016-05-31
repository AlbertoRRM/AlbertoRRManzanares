package es.ucm.fdi.tp.basecode.bgame.control;

import java.util.ArrayList;
import java.util.List;

import es.ucm.fdi.tp.basecode.bgame.model.Game;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;

/**
 * The controller's top class. It allows starting a game with a list of pieces
 * (that is provided in the construction) and makes operations such as making a
 * move or stopping the game, etc.
 * 
 * <p>
 * Clase base del controlador. Permite que comience un juego con una lista de
 * fichas dadas en el constructor, y realiza operaciones como ejecutar un
 * movimiento de un jugador, parar el juego, etc.
 * 
 */
public class Controller {

	/**
	 * The game on which the controller operates.
	 * 
	 * <p>
	 * Juego sobre el que opera el controlador.
	 */
	protected Game game;

	/**
	 * The list of pieces to be used in {@link #game}.
	 * 
	 * <p>
	 * Lista de fichas que se utilizarán en {@link #game}.
	 */
	protected List<Piece> pieces;

	/**
	 * Constructs a controller. Should be called by subclasses to set the values
	 * of {@link #game} and {@link #pieces}.
	 * 
	 * <p>
	 * Construye un controlador. Este constructor debe ser llamado desde las
	 * subclases para dar valor a los atributos {@link #game} y {@link #pieces}.
	 * 
	 * @param game
	 *            An instance of class {@link Game} on which the controller
	 *            operates (it can be {@code null} as well).
	 *            <p>
	 *            Instancia de la clase {@link Game} sobre la que va a operar el
	 *            controlador (o {@code null}).
	 * @param pieces
	 *            A list of pieces to be used when starting {@link #game} (it
	 *            can be {@code null} as well).
	 *            <p>
	 *            Lista de fichas que se utilizara cuando comience el juego
	 *            {@link #game} (o {@code null}).
	 */
	public Controller(Game game, List<Piece> pieces) {
		this.game = game;
		if (pieces != null)
			this.pieces = new ArrayList<Piece>(pieces);
		else
			this.pieces = new ArrayList<Piece>();
	};

	/**
	 * Stops the game that is being played in this controller (the one
	 * referenced by {@link #game}).
	 * 
	 * <p>
	 * Finaliza el juego que se está ejecutando actualmente en este controlador,
	 * que está referenciado por {@link #game}).
	 */
	public void stop() {
		if (game != null) {
			game.stop();
		}
	}

	/**
	 * Restart the game (the one referenced by {@link #game}).
	 * 
	 * <p>
	 * Reinicia el juego referenciado por {@link #game}).
	 */
	public void restart() {
		if (game != null) {
			game.restart();
		}
	}

	/**
	 * Makes a move in the game being played.
	 * 
	 * <p>
	 * Hace un movimiento en el juego que se esta jugando.
	 * 
	 * @param player
	 *            A player.
	 */
	public void makeMove(Player player) {
		if (game != null && player != null) {
			game.makeMove(player);
		}
	}

	/**
	 * Starts the controller. In principle it starts the game assigned to
	 * {@link #game} if it is not {@code null}.
	 * 
	 * <p>
	 * Inicia el controlador. En principio, inicia el juego asignado al atributo
	 * {@link #game}, si es distinto de {@code null}.
	 */
	public void start() {
		if (game != null) {
			game.start(pieces);
		}
	}

}
