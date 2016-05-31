package es.ucm.fdi.tp.basecode.connectn;

import java.util.List;

import es.ucm.fdi.tp.basecode.bgame.Utils;
import es.ucm.fdi.tp.basecode.bgame.control.Player;
import es.ucm.fdi.tp.basecode.bgame.model.Board;
import es.ucm.fdi.tp.basecode.bgame.model.GameError;
import es.ucm.fdi.tp.basecode.bgame.model.GameMove;
import es.ucm.fdi.tp.basecode.bgame.model.GameRules;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;

/**
 * A random player for ConnectN.
 * 
 * <p>
 * Un jugador aleatorio para ConnectN.
 *
 */
public class ConnectNRandomPlayer extends Player {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public GameMove requestMove(Piece p, Board board, List<Piece> pieces, GameRules rules) {
		if (board.isFull()) {
			throw new GameError("The board is full, cannot make a random move!!");
		}

		int rows = board.getRows();
		int cols = board.getCols();

		// pick an initial random position
		int currRow = Utils.randomInt(rows);
		int currCol = Utils.randomInt(cols);

		// start at (currRow,currColl) and look for the first empty position.
		while (true) {
			if (board.getPosition(currRow, currCol) == null) {
				return createMove(currRow, currCol, p);
			}
			currCol = (currCol + 1) % cols;
			if (currCol == 0) {
				currRow = (currRow + 1) % rows;
			}
		}

	}

	/**
	 * Creates the actual move to be returned by the player. Separating this
	 * method from {@link #requestMove(Piece, Board, List, GameRules)} allows us
	 * to reuse it for other similar games by overriding this method.
	 * 
	 * <p>
	 * Crea el movimiento concreto que sera devuelto por el jugador. Se separa
	 * este metodo de {@link #requestMove(Piece, Board, List, GameRules)} para
	 * permitir la reutilizacion de esta clase en otros juegos similares,
	 * sobrescribiendo este metodo.
	 * 
	 * @param row
	 *            row number.
	 * @param col
	 *            column number..
	 * @param p
	 *            Piece to place at ({@code row},{@code col}).
	 * @return
	 */
	protected GameMove createMove(int row, int col, Piece p) {
		return new ConnectNMove(row, col, p);
	}

}
