package es.ucm.fdi.tp.basecode.ttt;

import es.ucm.fdi.tp.basecode.connectn.ConnectNRules;

/**
 * Tic-Tac-Toe game rules. It is the same as ConnectN, except that the board has
 * a fixed size of 3x3 and we have only two players.
 * 
 * <p>
 * Reglas del juego Tic-Tac-Toe. Son las mismas que las de ConnectN, pero el
 * tablero es de tamaño fijo 3x3 y solo hay dos jugadores.
 *
 */
public class TicTacToeRules extends ConnectNRules {

	public TicTacToeRules() {
		super(3);
	}

	@Override
	public String gameDesc() {
		return "Tic-Tac-Toe";
	}

	@Override
	public int maxPlayers() {
		return 2;
	}

}
