package es.ucm.fdi.tp.assignment5.attt;

import es.ucm.fdi.tp.assignment5.RectBoardSwingView;
import es.ucm.fdi.tp.basecode.bgame.control.Controller;
import es.ucm.fdi.tp.basecode.bgame.control.Player;
import es.ucm.fdi.tp.basecode.bgame.model.GameObserver;
import es.ucm.fdi.tp.basecode.bgame.model.Observable;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;

@SuppressWarnings("serial")
public class ATTTSwingView extends RectBoardSwingView {

	private ATTTSwingPlayer player;
	private int init_row;
	private int init_col;
	private boolean initAvailable;
	private Piece turn;

	/**
	 * Constructor for ATTT Swing view
	 * @param g
	 * @param c
	 * @param localPiece
	 * @param randPlayer
	 * @param aiPlayer
	 */
	public ATTTSwingView(Observable<GameObserver> g, Controller c, Piece localPiece, Player randPlayer,
			Player aiPlayer) {
		super(g, c, localPiece, randPlayer, aiPlayer);
		initAvailable = false;
		player = new ATTTSwingPlayer();

		// Depending on the view, the current player will appear in the title or
		// not.
		if (localPiece == null)
			setTitle("Board Games: Advanced TicTacToe ");
		else
			setTitle("Board Games: Advanced TicTacToe " + "(" + localPiece.getId() + ")");
	}

	@Override
	protected void handleMouseClick(int row, int col, int mouseButton) {
		// Do nothing if the board is not active
		this.turn = getTurn();
		if (activeBoard) {
			if (getBoard().getPieceCount(turn) == 0) {
				if(initAvailable){
					player.setMove(init_row, init_col, row, col);
					initAvailable = false;
					decideMakeManualMove(player);
				}
				else {
					init_row = row;
					init_col = col;
					initAvailable = true;
				}
			}
			else {
				player.setMove(-1, -1, row, col);
				decideMakeManualMove(player);
			}
			
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
