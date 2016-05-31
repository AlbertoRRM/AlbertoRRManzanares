package es.ucm.fdi.tp.assignment5.connectN;

import es.ucm.fdi.tp.assignment5.RectBoardSwingView;
import es.ucm.fdi.tp.basecode.bgame.control.Controller;
import es.ucm.fdi.tp.basecode.bgame.control.Player;
import es.ucm.fdi.tp.basecode.bgame.model.GameObserver;
import es.ucm.fdi.tp.basecode.bgame.model.Observable;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;

@SuppressWarnings("serial")
public class ConnectNSwingView extends RectBoardSwingView {

	private ConnectNSwingPlayer player;

	/**
	 * Constructor for a ConnectN Swing view.
	 * 
	 * @param g
	 * @param c
	 * @param localPiece
	 * @param randPlayer
	 * @param aiPlayer
	 */
	public ConnectNSwingView(Observable<GameObserver> g, Controller c, Piece localPiece, Player randPlayer,
			Player aiPlayer) {
		super(g, c, localPiece, randPlayer, aiPlayer);
		player = new ConnectNSwingPlayer();
		// Depending on the view, the current player will appear in the title or
		// not.
		if (localPiece == null)
			setTitle("Board Games: ConnectN ");
		else
			setTitle("Board Games: ConnectN " + "(" + localPiece.getId() + ")");
	}

	@Override
	protected void handleMouseClick(int row, int col, int mouseButton) {
		// Do nothing if the board is not active
		if (activeBoard) {
			player.setMove(row, col);
			decideMakeManualMove(player);
		}
	}

	@Override
	protected void activateBoard() {
		// -declare the board active, so handleMouseClick accepts moves
		// -add corresponding message to the status messages indicating what to
		// do for making a move, etc...
		super.activateBoard();
		addMsg("Click on an origin cell\n");
	}

	@Override
	protected void deActivateBoard() {
		// Declare the board inactive, so handleMouseClick rejects moves
		super.deActivateBoard();
	}
}
