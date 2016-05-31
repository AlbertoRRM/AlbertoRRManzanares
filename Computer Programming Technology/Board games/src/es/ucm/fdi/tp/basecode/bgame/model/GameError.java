package es.ucm.fdi.tp.basecode.bgame.model;

/**
 * This class, that extends {@link RuntimeException}, is used to throw
 * exceptions that are detected while playing the game.
 * 
 * <p>
 * Esta clase, que extiende {@link RuntimeException}, se utiliza para lanzar
 * excepciones que se detectan durante la partida.
 */
public class GameError extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public GameError(String msg) {
		super(msg);
	}

}
