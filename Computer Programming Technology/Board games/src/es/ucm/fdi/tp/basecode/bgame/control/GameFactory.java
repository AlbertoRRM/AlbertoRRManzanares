package es.ucm.fdi.tp.basecode.bgame.control;

import java.util.List;

import es.ucm.fdi.tp.basecode.bgame.model.AIAlgorithm;
import es.ucm.fdi.tp.basecode.bgame.model.GameObserver;
import es.ucm.fdi.tp.basecode.bgame.model.GameRules;
import es.ucm.fdi.tp.basecode.bgame.model.Observable;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;

/**
 * Interface for game factories. It contains the prototypes of the methods
 * required for creating the objects involved in a game. This simplifies adding
 * new games to the overall application, etc.
 * 
 * <p>
 * Interfaz para las factorías de juegos. Contiene los prototipos de los métodos
 * necesarios para crear los objetos que intervienen en un juego. Esto
 * simplifica el proceso de añadir nuevos juegos en la aplicacion.
 */
public interface GameFactory extends java.io.Serializable {

	/**
	 * Creates the rules for the game for the game implementing this interface.
	 *
	 * <p>
	 * Crea las reglas del juego que implementa el interface {@link GameFactory}
	 * 
	 * @return The rules for this game.
	 *         <p>
	 *         Un objeto de una subclase de {@link GameRules} que contiene las
	 *         reglas de este juego.
	 */
	 GameRules gameRules();

	/**
	 * Creates a console player for the game implementing this interface.
	 * 
	 * <p>
	 * Crea un jugador de consola para el juego que implementa este interface.
	 * 
	 * @return A {@link Player} that represents a console player.
	 * 
	 *         <p>
	 *         Un nuevo objeto {@link Player} para este juego.
	 */
	Player createConsolePlayer();

	/**
	 * Creates a random player for the game implementing this interface. A
	 * random player is a player that makes a move using some random decisions.
	 * 
	 * <p>
	 * Crea un jugador aleatorio para el juego que implementa este interface. Un
	 * jugador aleatorio es un jugador que hace un movimiento mediante
	 * decisiones aleatorias.
	 * 
	 * @return A {@link Player} that represents a player.
	 * 
	 *         <p>
	 *         Un nuevo objeto {@link Player} que representa el jugador.
	 */
	Player createRandomPlayer();

	/**
	 * Creates an AI player for the game implementing this interface. The
	 * AI will use the specified algorithm to decide on a move.
	 *
	 * <p>
	 * Crea un jugador inteligente que usará el algoritmo especificado
	 * para elegir sus movimientos.
     *
	 * @param alg
	 *            The AI algorithm to use.
	 *            <p>
	 *            El algoritmo de IA que se usará.
	 *
	 * 
	 * @return A {@link Player} that represents an AI player.
	 * 
	 *         <p>
	 *         Un nuevo objeto {@link Player} para este juego.
	 */
	Player createAIPlayer(AIAlgorithm alg);

	/**
	 * Creates the default list of pieces (to be used when the user does not
	 * provide a list of players)
	 * 
	 * <p>
	 * Crea la lista de fichas por defecto (cuando el usuario no proporciona una
	 * lista de jugadores).
	 * 
	 */
	List<Piece> createDefaultPieces();

	/**
	 * Creates a corresponding console view and connect it to game reference by
	 * the parameter {@code game} and controller {@code ctrl}.
	 * 
	 * <p>
	 * Crea una vista de consola y la conecta al juego {@code game} y al
	 * controlador {@code ctrl}.
	 * 
	 * @param game
	 *            The game to view.
	 *            <p>
	 *            El juego que se quiere ver.
	 * 
	 * @param ctrl
	 *            The controller of the game.
	 *            <p>
	 *            El controlador del juego.
	 * 
	 */
	void createConsoleView(Observable<GameObserver> game, Controller ctrl);

	/**
	 * Creates a Swing view and connects it to game reference by {@code game}
	 * and controller {@code ctrl}. The parameter {@code viewPiece} indicates to
	 * which piece it belong (when it equals to {@code null} to should be used
	 * for all pieces). The parameters {@code randPlayer} and {code aiPlayer}
	 * should be used by the view to generate random and automatic moves.
	 * 
	 * <p>
	 * Crea una vista GUI y la conecta al juego {@code game} y al controlador
	 * {@code ctrl}. El parametro {@code viewPiece} indica la ficha a la que
	 * pertenece la vista (cuando es {@code null}, se utiliza la misma vista
	 * para todas las fichas). Los parametros {@code randPlayer} y {code
	 * aiPlayer} se deben utilizar en la vista para generar movimientos
	 * aleatorios y automaticos.
	 * 
	 * @param game
	 *            The game to view.
	 *            <p>
	 *            El juego que se quiere ver.
	 * 
	 * @param ctrl
	 *            The controller of the game.
	 *            <p>
	 *            El controlador del juego.
	 * 
	 * @param viewPiece
	 *            The piece to which this view belongs ({@code null} means that
	 *            it belongs to all pieces).
	 *            <p>
	 *            La ficha a la que pertenece la vista ({@code null} si la vista
	 *            pertenece a todos los jugadores).
	 * 
	 * @param randPlayer
	 *            The player to be used for generating random moves, if
	 *            {@code null} the view should not support random player.
	 *            <p>
	 *            El jugador que se va a utilizar para generar movimientos
	 *            aleatorios. Si es {@code null}, la vista no permite jugadores
	 *            aleatorios.
	 * 
	 * @param aiPlayer
	 *            The player to be used for generating automatics moves, if
	 *            {@code null} the view should not support AI (automatic)
	 *            player.
	 *            <p>
	 *            El jugador que se va a utilizar para generar movimientos
	 *            automaticos. Si es {@code null}, la vista no permite jugadores
	 *            IA (automaticos).
	 * 
	 */
	void createSwingView(final Observable<GameObserver> game, final Controller ctrl, final Piece viewPiece,
			Player randPlayer, Player aiPlayer);
}
