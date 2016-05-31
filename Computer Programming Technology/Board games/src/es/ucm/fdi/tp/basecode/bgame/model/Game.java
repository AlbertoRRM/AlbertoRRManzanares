package es.ucm.fdi.tp.basecode.bgame.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import es.ucm.fdi.tp.basecode.bgame.control.Player;

/**
 * A class that represents a board game being played. It implements the
 * {@link Observable} interface for notifying the observers the changes in its
 * state.
 * 
 * <p>
 * Clase que representa una partida de juego. Implementa el interface
 * {@link Observable} para notificar a los observadores los cambios en su
 * estado.
 */
public class Game implements Observable<GameObserver> {

	/**
	 * List of observers.
	 * 
	 * <p>
	 * Lista de observadores.
	 */
	protected ArrayList<GameObserver> observers;

	/**
	 * The board used for the current game.
	 * 
	 * <p>
	 * Tablero usado para este juego.
	 */
	private Board board;

	/**
	 * A read only version of {@link #board}. In practice it is an instance of
	 * {@link ReadOnlyBoard} that encapsulates {@link #board}, thus
	 * modifications to {@link #board} will be visible through {@link #roBoard}.
	 * 
	 * <p>
	 * Una version de solo lectura de {@link #board}. En la practica es una
	 * instancia de {@link ReadOnlyBoard} que encapsula {@link #board}, de forma
	 * que los cambios que se hagan a {@link #board} seran visibles en
	 * {@link #roBoard}.
	 */
	private Board roBoard;

	/**
	 * A list of pieces of all players in the current game. The order represents
	 * the order in which they have to play (in case it is required by the game
	 * rules).
	 * 
	 * <p>
	 * La lista de fichas de todos los jugadores del juego.
	 */
	private List<Piece> pieces;

	/**
	 * A read only version of {@link #pieces}.
	 * 
	 * <p>
	 * Version de solo lectura de {@link #pieces}.
	 */
	private List<Piece> roPieces;

	/**
	 * The rules of the game.
	 * 
	 * <p>
	 * Las reglas del juego.
	 */
	protected GameRules rules;

	/**
	 * The piece of the player with the current turn.
	 * 
	 * <p>
	 * La ficha del jugador que tiene el turno.
	 */
	private Piece turn;

	/**
	 * Current state of the game.
	 * 
	 * <p>
	 * Estado actual del juego.
	 */
	private State state;

	/**
	 * Piece of the winner player ({@code null} if there is no winner)
	 * 
	 * Ficha del jugador ganador ({@code null} si no hay un ganador).
	 */
	private Piece winner;

	/**
	 * The possible states of a game.
	 * 
	 * <p>
	 * Los estados posibles de un juego.
	 */
	public enum State {
		/**
		 * The game has not yet started.
		 * 
		 * <p>
		 * el juego aun no ha empezado.
		 */
		Starting,
		/**
		 * The game is still being played.
		 * 
		 * <p>
		 * el juego se esta jugando.
		 */
		InPlay,
		/**
		 * The game has finished. In class {@link Game}, the winner will be
		 * stored in {@link Game#winner}.
		 * 
		 * <p>
		 * el juego ha acabado. El ganador esta almacenado en el atributo
		 * {@link Game#winner}.
		 */
		Won,
		/**
		 * The game has finished with a draw. No player has won.
		 * 
		 * <p>
		 * el juego acaba con empate. No ha ganado ningún jugador.
		 */
		Draw,
		/**
		 * The game has been stopped. No player has won.
		 * 
		 * <p>
		 * el juego ha sido interrumpido. No hay ganador.
		 */
		Stopped
	}

	/**
	 * Creates a new game.
	 * 
	 * <p>
	 * Creacion de un nuevo juego.
	 * 
	 * @param rules
	 *            The game rules.
	 */
	public Game(GameRules rules) {
		this.rules = rules;
		this.observers = new ArrayList<GameObserver>();
		this.state = State.Starting;
		this.turn = null;
		this.winner = null;
	}

	/**
	 * Starts a game with a given list of pieces {@link #pieces}. The order is
	 * important, as it might be used by the game rules to decide who is playing
	 * next.
	 * 
	 * <p>
	 * Comienza un juego con la lista de fichas {@link #pieces}. El orden es
	 * importante, pues pueden utilizarlo las reglas del juego para decidir
	 * quien juega a continuacion.
	 * 
	 * @param pieces
	 *            A list of pieces.
	 *            <p>
	 *            lista de fichas.
	 */
	public void start(List<Piece> pieces) {

		// We cannot start a game that is not in Starting state. In this case
		// you should use restart() instead.
		if (state != State.Starting) {
			notifyError(new GameError("Game alreay started"));
		}

		// check that number of players is OK
		if (pieces.size() < rules.minPlayers() || pieces.size() > rules.maxPlayers()) {
			notifyError(new GameError("Too many or too few players: " + pieces.size()));
		}

		// check for duplicate player names
		Set<Piece> tmpPieces = new HashSet<Piece>();
		boolean errors = false;
		for (Piece p : pieces) {
			if (p == null || !tmpPieces.add(p)) {
				notifyError(new GameError("Multiple or invalid piece '" + p + "'"));
				errors = true;
			}
		}
		if (!errors) {
			// create the initial board
			this.board = rules.createBoard(pieces);
			// keep a read-only copy of the same
			this.roBoard = new ReadOnlyBoard(board);
			// keep a copy of the piece-list
			this.pieces = new ArrayList<Piece>(pieces);
			// and a read-only copy of the same
			this.roPieces = Collections.unmodifiableList(this.pieces);
			// set the initial player
			this.turn = rules.initialPlayer(board, pieces);
			// mark the game as started
			this.state = State.InPlay;
			// and notify everybody that it has started
			notifyGameStart(null);
		}
	}

	/**
	 * Restarts the game.
	 * 
	 * <p>
	 * reinicia el guego.
	 */
	public void restart() {
		// We cannot restart a game that has not started yet.
		if (state == State.Starting) {
			notifyError(new GameError("The game has not started yet, you cannot restart it."));
		}

		state = State.Starting; // mark it as starting
		start(pieces); // and use start again to restart teh game
	}

	/**
	 * Stops the game.
	 * 
	 * <p>
	 * para el juego
	 */
	public void stop() {
		if (state != State.Stopped) {
			state = State.Stopped; // mark it as stopped
			notifyStopped(); // and notify the observers that it has been
								// stopped
		}
	}

	/**
	 * Consults the textual description of the game. It is taken from the game
	 * rules.
	 * 
	 * <p>
	 * Proporciona la descripcion textual del juego. Se obtiene de las reglas
	 * del juego.
	 * 
	 * @return Textual description of the game.
	 * 
	 *         <p>
	 *         Descripcion textual del juego
	 */
	public String gameDesc() {
		return rules.gameDesc();
	}

	/**
	 * Consults that list of pieces.
	 * 
	 * @return A read-only version of list of pieces.
	 * 
	 *         <p>
	 *         Version de solo lectura de la lista de fichas de los jugadores
	 */
	public List<Piece> getPlayersPieces() {
		return roPieces;
	}

	/**
	 * Consults the current state of the game.
	 * 
	 * <p>
	 * Proporciona el estado actual del juego.
	 * 
	 * @return The state of the game.
	 */
	public Game.State getState() {
		return state;
	}

	/**
	 * Consults which piece is playing next. Might be {@code null} if the game
	 * has not started yet.
	 * 
	 * <p>
	 * Proporciona la ficha que juega a continuacion. Puede ser {@code null} si
	 * el juego todavia no ha comenzado.
	 * 
	 * @return The piece that should play next.
	 */
	public Piece getTurn() {
		return turn;
	}

	/**
	 * Consults the winner.
	 * 
	 * <p>
	 * Proporciona el ganador del juego.
	 * 
	 * @return The piece that has won, and {@code null} if there is no winner.
	 *         <p>
	 *         Ficha que ha ganado, o {@code null} si no hay ganador.
	 */
	public Piece getWinner() {
		return winner;
	}

	/**
	 * Make a move using the player {@code player}. It will request a move from
	 * player {@code player}, and then execute it using
	 * {@link #executeMove(GameMove)}.
	 * 
	 * <p>
	 * Realiza un movimiento utilizando el jugador {@code player}. Solicita un
	 * movimiento al jugador {@code player} y lo ejecuta utilizando
	 * {@link #executeMove(GameMove)}.
	 * 
	 * @param player
	 *            A player to make a move.
	 * 
	 *            <p>
	 *            El jugador que realiza el movimiento
	 */
	public void makeMove(Player player) {
		GameMove m = null;
		boolean errors = false;
		try {
			m = player.requestMove(turn, roBoard, roPieces, rules);
			if (m == null) {
				throw new GameError("Player couldn't generate a valid mode!");
			}
		} catch (GameError e) {
			notifyError(e);
			errors = true;
		}
		if (!errors) {
			executeMove(m);
		}
	}

	/**
	 * Executes a given game move, according to the following steps:
	 * <ul>
	 * <li>The game state must be {@link State#InPlay}.</li>
	 * <li>The move must correspond to the piece in {@link #turn}.</li>
	 * <li>Notify all observers that we are about to execute a move.</li>
	 * <li>Execute the move, and notify the observers if it has finished with
	 * success or failed.</li>
	 * <li>Compute the next status of the game, and depending on if the game has
	 * finished or not, update {@link #state}, {@link #winner} and {@link #turn}
	 * , and also send corresponding notifications to the observers.</li>
	 * </ul>
	 * 
	 * <p>
	 * Procesa un movimiento de un jugador, conforme a los siguientes pasos:
	 * <ul>
	 * <li>El estado del juego debe ser {@link State#InPlay}.</li>
	 * <li>El movimiento debe corresponder a la ficha en {@link #turn}.</li>
	 * <li>notifica a todos los observadores que el movimiento va a comenzar
	 * </li>
	 * <li>Ejecuta el movimiento y notifica a los observadores si ha acabado con
	 * exito o ha fallado.</li>
	 * <li>Calcula el siguiente estado del juego y, dependiendo de si el juego
	 * ha terminado o no, actualiza {@link #state}, {@link #winner} y
	 * {@link #turn}. Por ultimo, envia la notificacion correspondiente a los
	 * observadores.</li>
	 * </ul>
	 * 
	 * @param move
	 *            A move to execute.
	 * 
	 *            <p>
	 *            movimiento a ejecutar.
	 */
	private void executeMove(GameMove move) {

		boolean errors = false;

		// check if the game is in play
		if (state != State.InPlay) {
			notifyError(new GameError("Game is not in play"));
			errors = true;
		}

		// the move must correspond to the current player
		if (!move.getPiece().equals(turn)) {
			notifyError(new GameError("It is not turn of " + move.getPiece()));
			errors = true;
		}

		notifyStartMove(); // we are about to execute a move

		// execute the move
		if (!errors) {
			try {
				// execute the move
				move.execute(this.board, this.roPieces);
				// no exceptions? notify that the move has finished correctly
				notifyEndMove(true);
			} catch (GameError e) {
				// exception caught: notify that the move has ended with error
				notifyEndMove(false);
				// and then send the actual error message
				notifyError(e);
				errors = true;
			}
		}

		if (!errors) {
			// compute the new status of the game
			//
			Pair<State, Piece> gameOverStatus = rules.updateState(board, pieces, turn);

			// modify the game state, according to result of updateState(...)
			//
			switch (gameOverStatus.getFirst()) {
			case Draw:
				state = State.Draw;
				notifyDraw();
				break;
			case InPlay:
				turn = rules.nextPlayer(board, pieces, turn);
				notifyChangeTurn();
				break;
			case Won:
				winner = gameOverStatus.getSecond();
				state = State.Won;
				notifyWon();
				break;
			default:
				throw new UnsupportedOperationException(
						"The state " + gameOverStatus.getFirst() + " is invalid at this point, something went wrong!");
			}
		}
	}

	/**
	 * Notifies the observers that the game has started. If the parameter
	 * {@code observer} is {@code null} it notifies all observers, otherwise it
	 * notifies only the the one passed as parameter.
	 * 
	 * <p>
	 * Notifica a los observadores que el juego ha comenzado. En el caso que el
	 * paramertro {@code observer} sea {@code null} notificamos a todos los
	 * observadores, en caso contartio notificamos solo a {@code observer}.
	 * 
	 * @param observer
	 */
	private void notifyGameStart(GameObserver observer) {
		if (observer == null) {
			for (GameObserver o : observers) {
				o.onGameStart(roBoard, rules.gameDesc(), roPieces, turn);
			}
		} else {
			observer.onGameStart(roBoard, rules.gameDesc(), roPieces, turn);
		}
	}

	/**
	 * Notifies the observers that the game has finished with draw.
	 * 
	 * <p>
	 * Notifica a los observadores que el juego ha terminado con empate.
	 */
	private void notifyDraw() {
		for (GameObserver o : observers) {
			o.onGameOver(roBoard, state, null); // we pass a read only board
		}
	}

	/**
	 * Notifies the observers that the game has finished and there is a winner!.
	 * 
	 * <p>
	 * Notifica a los observadores que el juego ha terminado con un ganador.
	 */
	protected void notifyWon() {
		for (GameObserver o : observers) {
			o.onGameOver(roBoard, state, winner); // we pass a read only board
		}
	}

	/**
	 * Notifies the observers that we are about to start executing a move.
	 * 
	 * <p>
	 * Notifica a los observadores que se va a ejecutar un movimiento.
	 */
	protected void notifyStartMove() {
		for (GameObserver o : observers) {
			o.onMoveStart(roBoard, turn); // we pass a read only board
		}
	}

	/**
	 * Notifies the observers that the execution of a move has been finished.
	 * 
	 * <p>
	 * Notifica a los observadores que se ha terminado de ejecutar un
	 * movimiento.
	 * 
	 * @param success
	 *            {@code true} if the move ended successfully, {@code false}
	 *            otherwise.
	 *            <p>
	 *            {@code true} si el movimiento ha terminado con exito,
	 *            {@code false} en caso contrario.
	 */
	protected void notifyEndMove(boolean success) {
		for (GameObserver o : observers) {
			o.onMoveEnd(roBoard, turn, success); // we pass a read only board
		}
	}

	/**
	 * Notifies the observers that the game has been stopped!.
	 * 
	 * <p>
	 * Notifica a los observadores que se ha interrumpido el juego.
	 */
	protected void notifyStopped() {
		for (GameObserver o : observers) {
			o.onGameOver(roBoard, state, null); // we pass a read only board
		}
	}

	/**
	 * Notifies the observers that the turn has changed.
	 * 
	 * <p>
	 * Notifica a los observadores que ha cambiado el turno.
	 */
	protected void notifyChangeTurn() {
		for (GameObserver o : observers) {
			o.onChangeTurn(roBoard, turn); // we pass a read only board
		}
	}

	/**
	 * Notifies the observers that some error occurred, and throw an exception
	 * as well.
	 * 
	 * <p>
	 * Notifica a los observadores que ha ocurrido un error y lanza una
	 * excepcion.
	 */
	protected void notifyError(RuntimeException e) {
		String msg = e.getLocalizedMessage();
		for (GameObserver o : observers) {
			o.onError(msg);
		}
		throw e;
	}

	/**
	 * Adds a game observer. If an observer is added when the game has started
	 * already, we should notify it with the current state.
	 * 
	 * <p>
	 * Añade un observador del juego. Si se añade un observador a un juego que
	 * ya ha comenzado, se notifica el inicio de juego con el estado actual.
	 * 
	 * @param o
	 *            A game observer.
	 *            <p>
	 *            Observador del juego.
	 */
	@Override
	public void addObserver(GameObserver o) {
		observers.add(o);
		if (state != State.Starting) { // i
			notifyGameStart(o);
		}
	}

	/**
	 * Removes a game observer.
	 * 
	 * <p>
	 * Elimina un observador del juego.
	 * 
	 * @param o
	 *            A game observer.
	 *            <p>
	 *            Observador del juego.
	 */

	@Override
	public void removeObserver(GameObserver o) {
		observers.remove(o);
	}

	@Override
	public String toString() {
		String s = board.toString();
		s = s + "Players: ";
		for (Piece p : pieces) {
			s = s + p;
			if (board.getPieceCount(p) != null) {
				s = s + "(" + board.getPieceCount(p) + ") ";
			} else {
				s = s + " ";
			}
		}
		s = s + "\n" + "Game Status: " + getState();
		if (getState() == State.Won) {
			s = s + "\n" + "Winner: " + getWinner();
		}
		return s;
	}

}
