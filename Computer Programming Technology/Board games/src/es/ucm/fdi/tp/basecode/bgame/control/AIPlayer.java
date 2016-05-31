package es.ucm.fdi.tp.basecode.bgame.control;

import java.util.List;

import es.ucm.fdi.tp.basecode.bgame.model.AIAlgorithm;
import es.ucm.fdi.tp.basecode.bgame.model.Board;
import es.ucm.fdi.tp.basecode.bgame.model.GameMove;
import es.ucm.fdi.tp.basecode.bgame.model.GameRules;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;

/**
 * Class that implements an AI player that simply delegates the requests to an
 * {@link AIAlgorithm}.
 * 
 * <p>
 * Clase que implementa un jugador con IA que delega la peticion a un
 * {@link AIAlgorithm}..
 */
public class AIPlayer extends Player {

	private static final long serialVersionUID = 1L;
	private AIAlgorithm alg;

	/**
	 * Constructs an AI player.
	 *
	 * <p>
	 * Construye un jugador AI.
	 * 
	 * @param alg
	 *            The {@link AIAlgorithm} to be used by this player.
	 *            
	 *            <p>
	 *            El {@link AIAlgorithm} que usa este jugador. 
	 */
	public AIPlayer(AIAlgorithm alg) {
		this.alg = alg;
	}

	@Override
	public GameMove requestMove(Piece p, Board board, List<Piece> pieces, GameRules rules) {
		return alg.getMove(p, board, pieces, rules);
	}

}
