package es.ucm.fdi.tp.basecode.bgame.model;

import java.io.Serializable;
import java.util.List;

/**
 * An interface for Artificial Intelligence algorithms that are used by
 * automatic players to automatically decide the next (best) move, e.g., MinMax.
 * 
 * <p>
 * Interfaz para que los jugadores automaticos puedan utilizar algoritmos de
 * inteligencia artificial para decidir automaticamente el mejor movimiento,
 * como por ejemplo MINIMAX.
 *
 */
public interface AIAlgorithm extends Serializable {

	/**
	 * Computes the 'best' next move.
	 * 
	 * <p>
	 * Calcula el 'mejor' siguiente movimiento.
	 * 
	 * @param p
	 *            A piece, representing the next player.
	 *            <p>
	 *            Ficha que representa el siguiente jugador.
	 * @param board
	 *            The board on which the move should be made.
	 *            <p>
	 *            Tablero en el que se realizara el movimiento.
	 * @param pieces
	 *            A list of pieces representing the players (the order is
	 *            important).
	 *            <p>
	 *            Lista de fichas que representan los jugadores (el orden es
	 *            importante).
	 * @param rules
	 *            The rules of the currently played game.
	 *            <p>
	 *            Reglas del juego al que se esta jugando.
	 * @return The (best) move as calculated by the underlying algorithm.
	 *         <p>
	 *         (Mejor) movimiento calculado por el algoritmo.
	 */
	GameMove getMove(Piece p, Board board, List<Piece> pieces, GameRules rules);
}
