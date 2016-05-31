package es.ucm.fdi.tp.basecode.attt;

import java.util.ArrayList;
import java.util.List;

import es.ucm.fdi.tp.basecode.bgame.model.Board;
import es.ucm.fdi.tp.basecode.bgame.model.FiniteRectBoard;
import es.ucm.fdi.tp.basecode.bgame.model.GameMove;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;
import es.ucm.fdi.tp.basecode.ttt.TicTacToeRules;

/**
 * Rules for Advanced Tic-Tac-Toe. It is exactly like Tic-Tac-Toe, but each
 * player has at most 3 pieces.
 * 
 * <p>
 * Reglas del juego Tic-Tac-Toe avanzado. Es como Tic-Tac-Toe, pero cada jugador
 * tiene como mucho 3 fichas.
 */
public class AdvancedTTTRules extends TicTacToeRules {
	@Override
	public String gameDesc() {
		return "Advanced Tic-Tac-Toe";
	}

	@Override
	public Board createBoard(List<Piece> playersPieces) {
		Board b = new FiniteRectBoard(3, 3);
		b.setPieceCount(playersPieces.get(0), 3);
		b.setPieceCount(playersPieces.get(1), 3);
		return b;
	}

	@Override
	public List<GameMove> validMoves(Board board, List<Piece> playersPieces, Piece turn) {
		List<GameMove> moves = new ArrayList<GameMove>();

		if ( board.getPieceCount(turn) > 0) {
			for (int i = 0; i < board.getRows(); i++) {
				for (int j = 0; j < board.getCols(); j++) {
					if (board.getPosition(i, j) == null) {
						moves.add(new AdvancedTTTMove(-1, -1, i, j, turn));
					}
				}
			}
		} else {
			// note: could be made more efficient,
			// but on 3x3 board, max inner loops is ~ 3x9 = 27; not worth it
			for (int i = 0; i < board.getRows(); i++) {
				for (int j = 0; j < board.getCols(); j++) {
					if (turn.equals( board.getPosition(i, j) )) {
						for (int x = 0; x < board.getRows(); x++) {
							for (int y = 0; y < board.getCols(); y++) {
								if (board.getPosition(x, y) == null) {
									moves.add(new AdvancedTTTMove(
											i, j, x, y,	turn));
								}
							}
						}
					}
				}
			}
		}
		return moves;
	}

}
