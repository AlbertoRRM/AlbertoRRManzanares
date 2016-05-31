package es.ucm.fdi.tp.basecode.minmax;

import java.util.List;

import es.ucm.fdi.tp.basecode.bgame.model.AIAlgorithm;
import es.ucm.fdi.tp.basecode.bgame.model.Board;
import es.ucm.fdi.tp.basecode.bgame.model.GameError;
import es.ucm.fdi.tp.basecode.bgame.model.Game.State;
import es.ucm.fdi.tp.basecode.bgame.model.GameMove;
import es.ucm.fdi.tp.basecode.bgame.model.GameRules;
import es.ucm.fdi.tp.basecode.bgame.model.Pair;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;

@SuppressWarnings("serial")
public class MinMax implements AIAlgorithm {

	final private static int _TREE_DEPTH = 5;

	private boolean useAblphaBeta;
	private int depth;

	public MinMax() {
		this(_TREE_DEPTH, true);
	}

	public MinMax(boolean useAblphaBeta) {
		this(_TREE_DEPTH, useAblphaBeta);
	}

	public MinMax(int depth) {
		this(depth, true);
	}

	public MinMax(int depth, boolean useAblphaBeta) {
		if (depth < 1) {
			throw new GameError("Invalid depth ('" + depth + "') for the MinMax algorithm, it should be at east 1");
		}
		this.depth = depth;
		this.useAblphaBeta = useAblphaBeta;
	}

	@Override
	public GameMove getMove(Piece p, Board board, List<Piece> pieces, GameRules rules) {
		try {
			Pair<GameMove, Double> m = minmax(depth, -Double.MAX_VALUE, Double.MAX_VALUE, p, p, board, pieces, rules);
			return m.getFirst();
		} catch (Exception e) {
			return null;
		}
	}

	private Pair<GameMove, Double> minmax(int d, Double alpha, Double beta, Piece p, Piece turn, Board board,
			List<Piece> pieces, GameRules rules) throws InterruptedException {

		if (Thread.interrupted()) {
			throw new InterruptedException();
		}

		Pair<State, Piece> r = rules.updateState(board, pieces, turn);

		switch (r.getFirst()) {
		case Won:
			if (p.equals(r.getSecond())) {
				return new Pair<GameMove, Double>(null, 1.5 * (d + 1));
			} else {
				return new Pair<GameMove, Double>(null, -1.5 * (d + 1));
			}
		case Draw:
			return new Pair<GameMove, Double>(null, 0.0);
		default:
			break;
		}

		if (d < 1) {
			return new Pair<GameMove, Double>(null, rules.evaluate(board, pieces, turn, p));
		}

		List<GameMove> moves = rules.validMoves(board, pieces, turn);
		assert (moves.size() > 0);

		// max is p, min are all other players -- just trying to generalize to
		// more than two players, don't know if it makes sense!
		//
		if (p.equals(turn)) {
			return max(d, alpha, beta, p, turn, board, pieces, rules, moves);
		} else {
			return min(d, alpha, beta, p, turn, board, pieces, rules, moves);
		}
	}

	private Pair<GameMove, Double> max(int d, Double alpha, Double beta, Piece p, Piece turn, Board board,
			List<Piece> pieces, GameRules rules, List<GameMove> moves) throws InterruptedException {

		GameMove selectedMove = null;

		for (GameMove m : moves) {
			Board b = board.copy();
			m.execute(b, pieces);
			Piece nextTurn = rules.nextPlayer(b, pieces, turn);
			Pair<GameMove, Double> res = minmax(d - 1, alpha, beta, p, nextTurn, b, pieces, rules);
			if (res.getSecond() > alpha) {
				alpha = res.getSecond();
				selectedMove = m;
			}
			if (useAblphaBeta && alpha >= beta) {
				return new Pair<GameMove, Double>(selectedMove, alpha);
			}
		}

		return new Pair<GameMove, Double>(selectedMove, alpha);
	}

	private Pair<GameMove, Double> min(int d, Double alpha, Double beta, Piece p, Piece turn, Board board,
			List<Piece> pieces, GameRules rules, List<GameMove> moves) throws InterruptedException {

		GameMove selectedMove = null;

		for (GameMove m : moves) {
			Board b = board.copy();
			m.execute(b, pieces);
			Piece nextTurn = rules.nextPlayer(b, pieces, turn);
			Pair<GameMove, Double> res = minmax(d - 1, alpha, beta, p, nextTurn, b, pieces, rules);
			if (res.getSecond() < beta) {
				beta = res.getSecond();
				selectedMove = m;
			}
			if (useAblphaBeta && beta <= alpha) {
				return new Pair<GameMove, Double>(selectedMove, beta);
			}

		}

		return new Pair<GameMove, Double>(selectedMove, beta);
	}

}
