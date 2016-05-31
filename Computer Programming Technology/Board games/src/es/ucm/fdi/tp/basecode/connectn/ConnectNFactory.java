package es.ucm.fdi.tp.basecode.connectn;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import es.ucm.fdi.tp.basecode.bgame.control.AIPlayer;
import es.ucm.fdi.tp.basecode.bgame.control.ConsolePlayer;
import es.ucm.fdi.tp.basecode.bgame.control.Controller;
import es.ucm.fdi.tp.basecode.bgame.control.DummyAIPlayer;
import es.ucm.fdi.tp.basecode.bgame.control.GameFactory;
import es.ucm.fdi.tp.basecode.bgame.control.Player;
import es.ucm.fdi.tp.basecode.bgame.model.AIAlgorithm;
import es.ucm.fdi.tp.basecode.bgame.model.GameError;
import es.ucm.fdi.tp.basecode.bgame.model.GameMove;
import es.ucm.fdi.tp.basecode.bgame.model.GameObserver;
import es.ucm.fdi.tp.basecode.bgame.model.GameRules;
import es.ucm.fdi.tp.basecode.bgame.model.Observable;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;
import es.ucm.fdi.tp.basecode.bgame.views.GenericConsoleView;

/**
 * A factory for creating connect-N games. See {@link ConnectNRules} for the
 * description of the game.
 * 
 * 
 * <p>
 * Factoria para la creacion de juegos Connect-n. Vease {@link ConnectNRules}
 * para la descripcion del juego.
 */
public class ConnectNFactory implements GameFactory {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected int dim;

	public ConnectNFactory() {
		this(5);
	}

	public ConnectNFactory(int dim) {
		if (dim < 3) {
			throw new GameError("Dimension must be at least 3: " + dim);
		} else {
			this.dim = dim;
		}
	}

	@Override
	public GameRules gameRules() {
		return new ConnectNRules(dim);
	}

	@Override
	public Player createConsolePlayer() {
		ArrayList<GameMove> possibleMoves = new ArrayList<GameMove>();
		possibleMoves.add(new ConnectNMove());
		return new ConsolePlayer(new Scanner(System.in), possibleMoves);
	}

	@Override
	public Player createRandomPlayer() {
		return new ConnectNRandomPlayer();
	}

	@Override
	public Player createAIPlayer(AIAlgorithm alg) {
		if ( alg != null ) {
			return new AIPlayer(alg);
		} else {
			return new DummyAIPlayer(createRandomPlayer(), 1000);
		}
	}

	/**
	 * By default, we have two players, X and O.
	 * <p>
	 * Por defecto, hay dos jugadores, X y O.
	 */
	@Override
	public List<Piece> createDefaultPieces() {
		List<Piece> pieces = new ArrayList<Piece>();
		pieces.add(new Piece("X"));
		pieces.add(new Piece("O"));
		return pieces;
	}

	@Override
	public void createConsoleView(Observable<GameObserver> g, Controller c) {
		new GenericConsoleView(g, c);
	}

	@Override
	public void createSwingView(final Observable<GameObserver> g, final Controller c, final Piece viewPiece,
			Player random, Player ai) {
		throw new UnsupportedOperationException("There is no swing view");
	}

}
