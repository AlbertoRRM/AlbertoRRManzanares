package es.ucm.fdi.tp.basecode.bgame.model;

import java.util.List;

/**
 * An observer for games. Will be notified when anything interesting happens
 * (the game starts or finishes, a move starts or finishes, the turn changes, or
 * an error happens).
 * 
 * <p>
 * Observador de juegos. Será notificado cuando ocurre algo interesante (el
 * juego comienza o acaba, un movimiento comienza o acaba, el turno cambia u
 * ocurre un error).
 */
public interface GameObserver {

	/**
	 * Notifies that a game has started.
	 * 
	 * <p>
	 * Notifica que se ha iniciado un juego.
	 * 
	 * @param board
	 *            The current (typically read only) board. <b>El tablero actual
	 *            (de solo lectura).
	 * @param gameDesc
	 *            A string describing the currently played game.
	 *            <p>
	 *            Un string que describe el juego al que se esta jugando.
	 * @param pieces
	 *            A list of pieces that are used to play the game (each piece
	 *            belongs to a player).
	 *            <p>
	 *            Lista de fichas que se utilizan para jugar a este juego (cada
	 *            ficha pertenece a un jugador).
	 * @param turn
	 *            The piece to be played next.
	 *            <p>
	 *            Ficha a la que corresponde mover.
	 */
	public void onGameStart(Board board, String gameDesc, List<Piece> pieces, Piece turn);

	/**
	 * Notifies that a game has finished (with a winner, draw, or stopped).
	 * 
	 * <p>
	 * Notifica que ha terminado un juego (con un ganador, en tablas, o se ha
	 * interrumpido).
	 * 
	 * @param board
	 *            The current (typically read only) board. <b>El tablero actual
	 *            (de solo lectura).
	 * @param state
	 *            The state in which the game has finished.
	 *            <p>
	 *            Estado en el que ha quedado el tablero.
	 * @param winner
	 *            The piece of the winner, if the game has finished with a
	 *            winner, otherwise {@code null}.
	 *            <p>
	 *            Ficha del ganador si el juego ha terminado con un ganador, o
	 *            {@code null} en caso contrario.
	 */
	public void onGameOver(Board board, Game.State state, Piece winner);

	/**
	 * Notifies that a move is about to start executing.
	 * 
	 * <p>
	 * Notifica que va a iniciarse un movimiento.
	 * 
	 * @param board
	 *            The current (typically read only) board. <b>El tablero actual
	 *            (de solo lectura).
	 * @param turn
	 *            The piece to be played next (by the move).
	 *            <p>
	 *            Ficha a la que corresponde mover a continuacion.
	 */
	public void onMoveStart(Board board, Piece turn);

	/**
	 * Notifies that the execution of a move has finished.
	 * 
	 * <p>
	 * Notifica que ha terminado la realización de un movimiento.
	 * 
	 * @param board
	 *            The current (typically read only) board. <b>El tablero actual
	 *            (de solo lectura).
	 * @param turn
	 *            The piece that was supposed to be played by the move.
	 *            <p>
	 *            Ficha a la que correspondia mover en el movimiento que acaba
	 *            de terminar.
	 * @param success
	 *            {@code true} if the move has been executed successfully,
	 *            otherwise {@code false}.
	 *            <p>
	 *            {@code true} si el movimiento se ha realizado correctamente,
	 *            {@code false} en caso contrario.
	 */
	public void onMoveEnd(Board board, Piece turn, boolean success);

	/**
	 * Notifies that there has been a turn change.
	 * 
	 * <p>
	 * Notifica que se ha producido un cambio de turno.
	 * 
	 * @param board
	 *            The current (typically read only) board. <b>El tablero actual
	 *            (de solo lectura).
	 * @param turn
	 *            The piece to be played next.
	 *            <p>
	 *            Ficha a la que corresponde mover a continuacion.
	 */
	public void onChangeTurn(Board board, Piece turn);

	/**
	 * Notifies that something went wrong.
	 * 
	 * <p>
	 * Notifica que se ha producido un error.
	 * 
	 * @param msg
	 *            An error message.
	 *            <p>
	 *            Mensaje de error.
	 */
	public void onError(String msg);

}
