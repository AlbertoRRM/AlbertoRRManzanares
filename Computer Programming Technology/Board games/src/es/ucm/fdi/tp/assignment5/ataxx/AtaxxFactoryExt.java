package es.ucm.fdi.tp.assignment5.ataxx;

import java.lang.reflect.InvocationTargetException;

import javax.swing.SwingUtilities;

import es.ucm.fdi.tp.assignment4.AtaxxFactory;
import es.ucm.fdi.tp.assignment5.ataxx.AtaxxSwingView;
import es.ucm.fdi.tp.basecode.bgame.control.Controller;
import es.ucm.fdi.tp.basecode.bgame.control.Player;
import es.ucm.fdi.tp.basecode.bgame.model.GameError;
import es.ucm.fdi.tp.basecode.bgame.model.GameObserver;
import es.ucm.fdi.tp.basecode.bgame.model.Observable;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;

/**
 * A factory for creating Ataxx game. See {@link AtaxxRules} for the description
 * of the game.
 * 
 * 
 * <p>
 * Factoria para la creacion de Ataxx. Vease {@link AtaxxRules} para la
 * descripcion del juego.
 */

@SuppressWarnings("serial")
public class AtaxxFactoryExt extends AtaxxFactory {

	/**
	 * Constructor with parameters of AtaxxFactoryExtended.
	 * 
	 * @param dimRows
	 *            is the given dimension.
	 * @param obstacles
	 *            is the number of given obstacles involved in the game.
	 */
	public AtaxxFactoryExt(Integer dimRows, Integer obstacles) {
		super(dimRows, obstacles);
	}

	/**
	 * Constructor used when no dimension is given but there are obstacles.
	 * 
	 * @param obstacles
	 *            is the number of given obstacles involved in the game.
	 */
	public AtaxxFactoryExt(Integer obstacles) {
		super(7, obstacles);
	}

	/**
	 * Constructor without parameters of AtaxxFactoryExt.
	 */
	public AtaxxFactoryExt() {
		super();
	}

	@Override
	public void createSwingView(Observable<GameObserver> g, Controller ctrl, Piece viewPiece, Player randPlayer,
			Player aiPlayer) {
		try {
			SwingUtilities.invokeAndWait(new Runnable() {
				@Override
				public void run() {
					new AtaxxSwingView(g, ctrl, viewPiece, randPlayer, aiPlayer);
				}
			});
		} catch (InvocationTargetException | InterruptedException e) {
			throw new GameError("...");
		}
	}
}
