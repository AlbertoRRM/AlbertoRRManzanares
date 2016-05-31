package es.ucm.fdi.tp.assignment5;

import java.awt.Color;

import es.ucm.fdi.tp.basecode.bgame.control.Controller;
import es.ucm.fdi.tp.basecode.bgame.control.Player;
import es.ucm.fdi.tp.basecode.bgame.model.GameObserver;
import es.ucm.fdi.tp.basecode.bgame.model.Observable;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;
import es.ucm.fdi.tp.assignment5.BoardComponent;

@SuppressWarnings("serial")
public abstract class RectBoardSwingView extends SwingView {

	private BoardComponent boardComp;

	/**
	 * Sets the board as active or inactive.
	 */
	protected boolean activeBoard;

	/**
	 * Constructor that pass the information to the superclass and sets the board 'active'.
	 * @param g
	 * @param c
	 * @param localPiece
	 * @param randPlayer
	 * @param aiPlayer
	 */
	public RectBoardSwingView(Observable<GameObserver> g, Controller c, Piece localPiece, Player randPlayer,
			Player aiPlayer) {
		super(g, c, localPiece, randPlayer, aiPlayer);
		activeBoard = true;
	}

	@Override
	protected void initBoardGui() {
		boardComp = new BoardComponent() {

			@Override
			protected Color getPieceColor(Piece p) {
				return RectBoardSwingView.this.getPieceColor(p);
			}

			@Override
			protected boolean isPlayerPiece(Piece p) {
				// Return true if p is a player piece, false if not (e.g., an
				// obstacle)
				boolean found = false;
				int index = 0;

				while (!found && (index < getPieces().size())) {
					if (p == getPieces().get(index))
						found = true;
					index++;
				}

				return found;
			}

			@Override
			protected void mouseClicked(int row, int col, int mouseButton) {
				handleMouseClick(row, col, mouseButton);
			}
		};
		setBoardArea(boardComp);
	}

	@Override
	protected void activateBoard() {
		activeBoard = true;
	}

	@Override
	protected void deActivateBoard() {
		activeBoard = false;
	}

	@Override
	protected void redrawBoard() {
		boardComp.redraw(getBoard());
	}

	protected abstract void handleMouseClick(int row, int col, int mouseButton);

}
