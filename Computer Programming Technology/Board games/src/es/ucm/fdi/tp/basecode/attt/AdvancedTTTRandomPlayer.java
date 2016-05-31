package es.ucm.fdi.tp.basecode.attt;

import es.ucm.fdi.tp.basecode.bgame.control.Player;
import es.ucm.fdi.tp.basecode.bgame.model.Board;
import es.ucm.fdi.tp.basecode.bgame.model.GameMove;
import es.ucm.fdi.tp.basecode.bgame.model.GameRules;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;
import es.ucm.fdi.tp.basecode.connectn.ConnectNRandomPlayer;

import java.util.List;
import java.util.Random;

/**
 * A random player for Advanced Tic-Tac-Toe. It inherits the functionality from
 * {@link ConnectNRandomPlayer} just by overriding the method
 * {@link #createMove(int, int, Piece)}.
 * 
 * <p>
 * Jugador aleatorio para el juego Tic-Tac-Toe avanzado. Hereda la funcionalidad
 * de {@link ConnectNRandomPlayer} pues sobrescribe solamente el metodo
 * {@link #createMove(int, int, Piece)}.
 *
 */
public class AdvancedTTTRandomPlayer extends Player {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static Random random = new Random();

	@Override
	public GameMove requestMove(Piece p, Board board, List<Piece> pieces, GameRules rules) {
		List<GameMove> availableMoves = rules.validMoves(board, pieces, p);
		return availableMoves.get(random.nextInt(availableMoves.size()));
	}}
