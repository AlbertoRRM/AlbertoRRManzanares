package es.ucm.fdi.tp.basecode.bgame.model;

/**
 * This class represents a game piece (counter) that players can use to place on
 * a board. It also can be used by the different games to mark obstacles, etc.
 * Each piece has an identifier (String) that does not include whitespace.
 * Pieces with the same identifier are equal. Games usually requires the
 * identifiers of the pieces involved in the game to be different.
 *
 * <b>Important:</b> Two pieces with the same identifier are equal and have the
 * same hash code. (see {@link #equals(Object)} and {@link #hashCode()}.
 *
 * <p>
 * Esta clase representa una ficha de juego (un contador) que los jugadores
 * pueden utilizar para colocar en el tablero de juego. También se puede
 * utilizar para indicar obstáculos, etc. en el tablero. Cada ficha tiene un
 * identificador (string) que no debe incluir espacios en blanco. Las fichas con
 * el mismo identificador se consideran identicas. Los juegos normalmente
 * necesitan que los identificadores de las fichas sean diferentes.
 * 
 * <b>Importante:</b> Dos fichas con el mismo identificador son iguales y tienen
 * el mismo codigo hash. (vease {@link #equals(Object)} y {@link #hashCode()}.
 * 
 *
 */
public class Piece implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The piece identifier.
	 * <p>
	 * Identificador de la ficha
	 */
	private String id;

	/**
	 * A counter to be used for automatically assigning an identifier to a
	 * piece.
	 * <p>
	 * Contador que se utiliza para asignar automaticamente un identificador a
	 * una ficha.
	 */
	private static int autoIdMajor = 0;

	/**
	 * A counter to be used for automatically assigning an identifier to a
	 * piece.
	 * <p>
	 * Contador que se utiliza para asignar automaticamente un identificador a
	 * una ficha.
	 */
	private static int autoIdMinor = 0;

	/**
	 * This constructor creates a piece with an automatically assigned
	 * identifier ({@see #generateId()}).
	 * 
	 * <p>
	 * Este constructor crea una ficha con un nombre automatico de un conjunto
	 * predefinido. Distintos objetos creado por estre constructor tendrán
	 * identificadores distintos ({@see #generateId()}).
	 * 
	 */
	public Piece() {
		id = generateId();
	}

	/**
	 * This constructor creates a piece with identifier {@code id}.
	 * 
	 * <p>
	 * Este constructor crea una pieza con identificador {@code id}.
	 * 
	 * @param id
	 *            Whitespace free string.
	 * 
	 *            <p>
	 *            string sin espacios en blanco
	 */
	public Piece(String id) {
		if (id == null) {
			throw new GameError("A Piece cannot ha a 'null' identifier");
		}

		if (validPieceId(id)) {
			this.id = id;
		} else {
			throw new GameError("Invalid Piece identifier '" + id + "'. It must be whitespace free.");
		}
	}

	/**
	 * Generates a piece identifier. It first generates A,B,...,Z. Then
	 * A1,....,Z1. Then A2,...,Z2. Etc.
	 * 
	 * <p>
	 * Genera un identificador de ficha. Primero utiliza las letras mayusculas
	 * A,B,...,Z. A continuacion, A1,....,Z1,A2,...,Z2. Etc.
	 * 
	 * @return A piece identifier.
	 *         <p>
	 *         Un identificador de ficha.
	 */
	private String generateId() {
		String id = String.valueOf((char) (autoIdMajor + 65));
		id = "" + id + autoIdMinor;

		autoIdMajor = (autoIdMajor + 1) % 26;

		if (autoIdMajor == 0)
			autoIdMinor++;

		return id;
	}

	/**
	 * Checks if a given string is a valid piece identifier, i.e., does not
	 * include white spaces.
	 * 
	 * <p>
	 * Comprueba si un string es un identificador valido, es decir, si no
	 * incluye espacios en blanco.
	 * 
	 * @param id
	 *            A string representing a piece identifier.
	 *            <p>
	 *            Un string que representa un identificador de ficha.
	 * @return {@code true} if {@code id} is a valid identifier, otherwise
	 *         {@code false}.
	 *         <p>
	 *         {@code true} si {@code id} es un identificador valido,
	 *         {@code false} en caso contrario.
	 */
	private boolean validPieceId(String id) {
		for (int i = 0; i < id.length(); i++) {
			if (Character.isWhitespace(id.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Consults the piece identifier.
	 * 
	 * <p>
	 * Proporciona el identificador de ficha.
	 * 
	 * @return The piece identifier.
	 *         <p>
	 *         Identificador de ficha.
	 */
	public String getId() {
		return id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Piece other = (Piece) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return id;
	}

}
