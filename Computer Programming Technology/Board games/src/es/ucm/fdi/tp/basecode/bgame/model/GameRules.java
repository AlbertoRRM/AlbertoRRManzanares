package es.ucm.fdi.tp.basecode.bgame.model;

import java.util.List;

/**
 * Interface that represents the rules of a game. It provides the following
 * operations:
 * <ul>
 * <li>Get a textual description of a game</li>
 * <li>Create an initial board.</li>
 * <li>Select an initial player.</li>
 * <li>Provides information on the minimum and maximum number of players allowed
 * by the game rules.</li>
 * <li>Update the current state of the game (won, draw, etc.)</li>
 * <li>Select the player to player next.</li>
 * <li>Evaluate how close is a given piece to win the game (for automatic
 * players).</li>
 * <li>Generate a list of valid moves for a given player (for automatic players mainly).</li>
 * </ul>
 * 
 * <p>
 * Interfaz para representar las reglas de un juego. Proporciona las siguientes
 * operaciones:
 * <ul>
 * <li>Obtener una descripcion textual del juego</li>
 * <li>Crear un tablero inicial.</li>
 * <li>Seleccionar el jugador inicial.</li>
 * <li>Proporcionar el numero minimo y maximo de jugadores que pueden jugar a
 * este juego.</li>
 * <li>Actualizar el estado actual del juego (fin de partida con ganador,
 * tablas, etc.)</li>
 * <li>Seleccionar el jugador que juega a continuacion.</li>
 * <li>Evaluar la proximidad de una ficha a un fin de juego ganador (para
 * jugadores automaticos).</li>
 * <li>Generar una lista de movimientos validos para una ficha (principalmente para jugadores
 * automaticos).</li>
 * </ul>
 * 
 */
public interface GameRules {

	/**
	 * Generates a textual description of the game.
	 * 
	 * <p>
	 * Genera una descripcion textual del juego.
	 * 
	 * @return Textual description of the game.
	 * 
	 *         <p>
	 *         Descripcion textual del juego
	 */
	public String gameDesc();

	/**
	 * Creates the initial board according to the game rules.
	 * 
	 * <p>
	 * Crea el tablero inicial segun las reglas del juego.
	 * 
	 * @param pieces
	 *            The list of pieces involved in the game (each correspond to a
	 *            player).
	 *            <p>
	 *            Lista de fichas de todos los jugadores (cada una corresponde a
	 *            un jugador).
	 * 
	 * @return An initial board for the game.
	 * 
	 *         <p>
	 *         Tablero inicial del juego.
	 */
	public Board createBoard(List<Piece> pieces);

	/**
	 * 
	 * Consults the initial player according to the games rules.
	 * 
	 * <p>
	 * Proporciona el jugador inicial segun las reglas del juego.
	 * 
	 * @param board
	 *            The initial board of the game.
	 *            <p>
	 *            El tablero inicial del juego
	 * @param pieces
	 *            The list of pieces involved in the game (each correspond to a
	 *            player).
	 *            <p>
	 *            Lista de las fichas de todos los jugadores (cada una
	 *            corresponde a un jugador).
	 * 
	 * @return The piece to be played first.
	 *         <p>
	 *         Ficha del jugador inicial.
	 */
	public Piece initialPlayer(Board board, List<Piece> pieces);

	/**
	 * Consults the minimum number of players allowed.
	 * 
	 * <p>
	 * Proporciona el numero minimo de jugadores permitido para este juego.
	 * 
	 * @return The minimum number of players.
	 *         <p>
	 *         Número mínimo de jugadores.
	 */
	public int minPlayers();

	/**
	 * Consults the maximum number of players allowed.
	 * 
	 * <p>
	 * Proporciona el numero maximo de jugadores permitido para este juego.
	 * 
	 * @return The maximum number of players.
	 *         <p>
	 *         Numero maximo de jugadores.
	 */
	public int maxPlayers();

	/**
	 * Returns the next state of the game. I.e., if it has finished with a
	 * winner, draw, or still in play. If there is a winner it also returns the
	 * winner.
	 * 
	 * <p>
	 * Devuelve el estado del juego según las reglas del juego y el tablero
	 * dado: si ha terminado con un ganador, en tablas, si no ha terminado. Si
	 * hay ganador, tambien devuelve la ficha ganadora.
	 * 
	 * 
	 * @param board
	 *            The current board of the game.
	 *            <p>
	 *            El tablero actual.
	 * @param pieces
	 *            The list of pieces involved in the game (each correspond to a
	 *            player).
	 *            <p>
	 *            Lista de fichas de todos los jugadores (cada una corresponde a
	 *            un jugador).
	 * @param turn
	 *            The piece that has been played last.
	 *            <p>
	 *            Ficha que ha jugado en el ultimo movimiento.
	 * 
	 * @return A pair containing the new state of the game and the piece of the
	 *         winner in case the game has finished with a winner, and
	 *         {@null} otherwise.
	 *         <p>
	 *         Un par que contiene el nuevo estado del juego y la ficha del
	 *         ganador si el juego ha acabado, o {@null} en caso contrario.
	 */
	public Pair<Game.State, Piece> updateState(Board board, List<Piece> pieces, Piece turn);

	/**
	 * Consults the next player.
	 * <p>
	 * Devuelve el suguiente jugador.
	 * 
	 * @param board
	 *            The current board of the game.
	 *            <p>
	 *            El tablero actual.
	 * @param pieces
	 *            The list of pieces involved in the game (each correspond to a
	 *            player).
	 *            <p>
	 *            La lista de fichas de todos los jugadores (cada una
	 *            corresponde a un jugador).
	 * @param turn
	 *            The piece that has been played last.
	 *            <p>
	 *            Ficha que ha jugado en el ultimo movimiento.
	 * 
	 * @return The piece to be played next.
	 *         <p>
	 *         La ficha del siguiente jugador, según las reglas del juego.
	 */
	public Piece nextPlayer(Board board, List<Piece> pieces, Piece turn);

	/**
	 * Evaluates how close the player {@link p} is to winning (1) or losing
	 * (-1). To be used in the automatic players. This method should at least
	 * return the neutral value (0) if there is no actual underlying algorithm
	 * that evaluates.
	 * 
	 * <p>
	 * The method must be called with a board that is InPlay, it cannot be
	 * called with a finished game.
	 * 
	 * <p>
	 * Evalua la proximidad del jugador que juega con la ficha {@link p} de
	 * ganar (1) o de perder (-1). Debe usarse para implementar estrategias de
	 * IA. Al menos, si no hay ningun algoritmo de evaluacion del movimiento,
	 * debe devolver el valor neutral 0.
	 * 
	 * @param board
	 *            The current board of the game.
	 *            <p>
	 *            El tablero actual.
	 * @param pieces
	 *            The list of pieces involved in the game (each correspond to a
	 *            player).
	 *            <p>
	 *            La lista de fichas de todos los jugadores (cada una
	 *            corresponde a un jugador).
	 * @param turn
	 *            The piece to be played next.
	 *            <p>
	 *            La ficha del siguiente jugador, según las reglas del juego.
	 * @param p
	 *            The piece w.r.t which we want to make the evaluation
	 *            <p>
	 *            La ficha a la que corresponde la evaluacion.
	 * 
	 * @return How close the player {@link turn} is to winning (1) or losing
	 *         (-1). The value 0 is neutral.
	 *         <p>
	 *         Un valor numerico que indica la cercania del jugador con ficha
	 *         {@link turn} de ganar (1) o de perder (-1). El valor 0 es
	 *         neutral.
	 */
	public double evaluate(Board board, List<Piece> pieces, Piece turn, Piece p);

	/**
	 * Generates a list of valid moves for a give piece, or {@code null} if this
	 * operation is not supported.
	 * 
	 * <p>
	 * Genera una lista de movimientos validos para una ficha o {@code null} si
	 * no se permite esta operacion.
	 * 
	 * @param board
	 *            The current board of the game.
	 *            <p>
	 *            El tablero actual.
	 * @param pieces
	 *            The list of pieces involved in the game (each correspond to a
	 *            player).
	 *            <p>
	 *            La lista de fichas de todos los jugadores (cada una
	 *            corresponde a un jugador).
	 * 
	 * @param turn
	 *            The piece for which we want to generate the valid moves.
	 *            <p>
	 *            La ficha para el que queremos generar los movimientos validos.
	 * @return A list of instances of a subclass of {@link GameMove} that
	 *         represent all valid move for{@code turn}, or {@code null} if this
	 *         operation is not supported. The actual class depends on the
	 *         actual implementation of {@link GameRules}.
	 * 
	 *         <p>
	 *         Lista de objetos de una subclase de {@link GameMove} que
	 *         representa todos los movimientos validos para la ficha
	 *         {@code turn}, o {@code null} si no se permite esta operacion. La
	 *         clase concreta dependerá de la implementación de
	 *         {@link GameRules} que corresponda.
	 */
	public List<GameMove> validMoves(Board board, List<Piece> pieces, Piece turn);
}
