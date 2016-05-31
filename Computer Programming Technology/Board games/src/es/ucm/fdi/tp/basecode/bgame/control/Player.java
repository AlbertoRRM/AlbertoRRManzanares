
package es.ucm.fdi.tp.basecode.bgame.control;

import java.util.List;

import es.ucm.fdi.tp.basecode.bgame.model.Board;
import es.ucm.fdi.tp.basecode.bgame.model.GameMove;
import es.ucm.fdi.tp.basecode.bgame.model.GameRules;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;

/**
 * Abstract class for implementing game players. Players should not be confused
 * with pieces. Each piece represent a turn in the game, while instances of this
 * class are those that generate the moves to place the pieces. For example, we
 * can have a player that chooses the place in which to place the piece
 * randomly, asking the user to introduce it, compute it automatically, etc.
 * 
 * <p>
 * Clase abstracta para implementar jugadores. Los jugadores no se deben
 * confundir con las fichas. Cada ficha representa un turno en el juego,
 * mientras que las instancias de esta clase son las que generan los movimientos
 * que ponen las fichas. Por ejemplo, podemos tener un jugador que elige el
 * lugar en el que colocar cada ficha de forma aleatoria, lo solicita al
 * usuario, lo calcula automaticamente, etc.
 */
public abstract class Player implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Returns a game move. It can be used obtain the next move from this player
	 * taking into account the input {@code board}, {@code pieces}, and
	 * {@code rules}.
	 * 
	 * <p>
	 * Obtiene el siguiente movimiento jugado por este jugador considerando el
	 * tablero {@code board}, la lista de fichas {@code pieces}, y las reglas
	 * {@code rules}.
	 * 
	 * 
	 * @param p
	 *            The piece to be used for making the move.
	 *            <p>
	 *            La ficha que se utiliza para realizar el movimiento.
	 * @param board
	 *            A board on which the move will be executed.
	 *            <p>
	 *            Tablero sobre el que se esta jugando.
	 * @param pieces
	 *            The list of pieces involved in the game.
	 *            <p>
	 *            La lista de fichas de todos los jugadores.
	 * @param rules
	 *            The rules of the game being played.
	 *            <p>
	 *            Las reglas del juego que se esta jugando.
	 * @return A game move.
	 *         <p>
	 *         Un movimiento proporcionado por este jugador.
	 */
	public abstract GameMove requestMove(Piece p, Board board, List<Piece> pieces, GameRules rules);

}
