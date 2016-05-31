package es.ucm.fdi.tp.assignment5.ataxx;

import es.ucm.fdi.tp.assignment5.RectBoardSwingView;
import es.ucm.fdi.tp.basecode.bgame.control.Controller;
import es.ucm.fdi.tp.basecode.bgame.control.Player;
import es.ucm.fdi.tp.basecode.bgame.model.GameObserver;
import es.ucm.fdi.tp.basecode.bgame.model.Observable;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;

@SuppressWarnings("serial")
public class AtaxxSwingView extends RectBoardSwingView {

	private AtaxxSwingPlayer player;
	private int init_row, init_col;
	private boolean initAvailable;

	/**
	 * Constructor of an Ataxx swing view.
	 * 
	 * @param g
	 *            is the game observer.
	 * @param c
	 *            is the controller.
	 * @param localPiece
	 *            is the piece of the current player.
	 * @param randPlayer
	 * @param aiPlayer
	 */
	public AtaxxSwingView(Observable<GameObserver> g, Controller c, Piece localPiece, Player randPlayer,
			Player aiPlayer) {
		super(g, c, localPiece, randPlayer, aiPlayer);
		initAvailable = false;
		player = new AtaxxSwingPlayer();
		// Depending on the view, the current player will appear in the title or
		// not.
		if (localPiece == null)
			setTitle("Board Games: Attax ");
		else
			setTitle("Board Games: Attax " + "(" + localPiece.getId() + ")");
	}

	@Override
	protected void handleMouseClick(int row, int col, int mouseButton) {
		// Do nothing if the board is not active
		if (activeBoard) {
			if (!initAvailable) {
				init_row = row;
				init_col = col;
				initAvailable = true;
			} else {
				player.setMove(init_row, init_col, row, col);
				initAvailable = false;
				decideMakeManualMove(player);
			}
		}
	}

	@Override
	protected void activateBoard() {
		// Declare the board active, so handleMouseClick accepts moves
		super.activateBoard();
		addMsg("Click on an origin cell\n");
	}

	@Override
	protected void deActivateBoard() {
		// Declare the board inactive, so handleMouseClick rejects moves
		super.deActivateBoard();
	}
}
