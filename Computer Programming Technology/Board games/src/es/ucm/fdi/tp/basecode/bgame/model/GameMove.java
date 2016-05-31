package es.ucm.fdi.tp.basecode.bgame.model;

import java.util.List;

/**
 * An abstract class representing a game move.
 * 
 * <p>
 * Clase abstracta para representar un movimiento de un juego.
 */
public abstract class GameMove implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The piece (i.e., player) to which this move belongs. It can be set only
	 * when creating an instance, and accessed only through {@link #getPiece()}.
	 * 
	 * <p>
	 * Ficha (es decir, jugador) al que pertenece esta accion. Se asigna
	 * solamente cuando se crea una instancia de esta clase y solo se accede
	 * mediante {@link #getPiece()}.
	 * 
	 */
	private Piece piece;

	/**
	 * This constructor should be used only for generating an instance to access
	 * the {@link #fromString(Piece, String)} method. E.g., it is used in
	 * {@link ConsolePlayer}. A move that is created using this constructor will
	 * have {@link #piece} equals to {@code null}, and thus cannot be executed.
	 * 
	 * <p>
	 * Este constructor solo se debe usar para generar instancias para acceder
	 * al metodo {@link #fromString(Piece, String)}. Por ejemplo, se utiliza
	 * desde {@link ConsolePlayer}. Un movimiento que utilice este constructor
	 * tiene como ficha asociada el valor {@code null}, por lo que no se puede
	 * ejecutar.
	 */
	public GameMove() {
	}

	/**
	 * Abstract constructor. Must be called by subclasses (and only by them).
	 * 
	 * <p>
	 * Constructor abstracto. Solo debe llamarse desde las subclases.
	 * 
	 * @param piece
	 *            The piece to which the move belongs.
	 * 
	 *            <p>
	 *            Ficha a la que pertenece el movimiento
	 */
	protected GameMove(Piece piece) {
		this.piece = piece;
	}

	/**
	 * Consults the piece to which this move belongs.
	 * 
	 * <p>
	 * Proporciona la ficha a la que pertenece este movimiento.
	 * 
	 * @return A piece to which the move belongs.
	 * 
	 *         <p>
	 *         Ficha a la que pertenece el movimiento.
	 */
	public Piece getPiece() {
		return this.piece;
	}

	/**
	 * An abstract method for executing the move on the given {@code board} and
	 * a list of {@code pieces}.
	 * 
	 * <p>
	 * Ejecuta el movimiento en el tablero {@code board} con la lista de fichas
	 * {@code pieces}.
	 * 
	 * @param board
	 *            A board on which the move operates.
	 * 
	 *            <p>
	 *            Tablero sobre el que se realiza el movimiento.
	 * @param pieces
	 *            A list of pieces that are involved in the game.
	 * 
	 *            <p>
	 *            Lista de fichas de todos los jugadores
	 */
	public abstract void execute(Board board, List<Piece> pieces);

	/**
	 * An abstract method for generating a game move from a string. The string
	 * format depends on the actual move (@see {@link #help()}).
	 * 
	 * <p>
	 * Genera un movimiento a partir de un string. El formato del string depende
	 * del movimiento implementado (@see {@link #help()}).
	 * 
	 * @param str
	 *            a string that represents a move.
	 * 
	 *            <p>
	 *            String que representa un movimiento.
	 * 
	 * @return An instance of (a subclass of) {@link GameMove} that corresponds
	 *         to the move as described by {@code cmd}.
	 * 
	 *         <p>
	 *         un objeto de (una subclase de) {@link GameMove} que se
	 *         corresponde con el movimiento descrito por {@code cmd}.
	 */
	public abstract GameMove fromString(Piece p, String str);

	/**
	 * Generates a string that describes the move. It should describe the format
	 * supported by {@link #fromString(Piece, String)}.
	 * 
	 * <p>
	 * Genera un string que describe el movimiento. Debe describir el formato
	 * soportado por {@link #fromString(Piece, String)}.
	 * 
	 * @return A help message.
	 * 
	 *         <p>
	 *         Mensaje de ayuda.
	 */
	public abstract String help();
}
