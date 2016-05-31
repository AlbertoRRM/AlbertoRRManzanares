package es.ucm.fdi.tp.basecode.bgame.model;

/**
 * 
 * An interface that represents a board in a board game (e.g., Tic-Tac-Toe). The
 * interface allows the following functionality:
 * <ul>
 * <li>Placing or removing a {@link Piece} from a board, where {@code null} is
 * typically used for representing an empty position.</li>
 * <li>Asking the value of a cell in the board, the size of the board, etc.</li>
 * <li>Maintain a piece-count for the different pieces. This typically depends
 * on the game, the actual implementation should only provide a way to associate
 * a number (the piece-count) with a piece, modify it, etc.</li>
 * <li>Make a fresh copy of the board, which is useful when we want simulate
 * what would happen when applying a given move, but without modifying the
 * current status of the game.
 * </ul>
 * 
 * <p>
 * Este interface representa un tablero de un juego. Permite la siguiente
 * funcionalidad:
 * <ul>
 * <li>Colocar o eliminar una ficha (de clase {@link Piece}) del tablero, en el
 * que {@code null} se utiliza normalmente para representar una posicion vacia.
 * </li>
 * <li>Preguntar por el valor de una casilla del tablero, el tamaño del tablero,
 * etc.</li>
 * <li>Mantener un contador de cada uno de los tipos de fichas. Esto normalmente
 * depende del juego, la implementacion concreta solo debe proporcionar una
 * forma de asociar un numero (el contador de fichas) con una ficha,
 * modificarlo, etc.</li>
 * <li>Hacer una copia del tablero, que es util para simular lo que ocurriria si
 * se realizara determinado movimiento, pero sin modificar el estado actual del
 * juego.
 * </ul>
 * 
 */
public interface Board extends java.io.Serializable {

	/**
	 * Consults the number of rows in the board. The interpretation of this
	 * number depends on the actual implementation/game.
	 * 
	 * <p>
	 * Proporciona el numero de filas del tablero. La interpretacion de este
	 * número depende del juego concreto.
	 * 
	 * @return The number of rows in the board.
	 *         <p>
	 *         El número de filas del tablero.
	 * 
	 */
	public int getRows();

	/**
	 * 
	 * Consults the number of columns in the board. The interpretation of this
	 * number depends on the actual implementation/game.
	 * 
	 * <p>
	 * Proporciona el numero de columnas del tablero. La interpretacion de este
	 * numero depende del juego.
	 * 
	 * @return The number of columns in the board.
	 *         <p>
	 *         El numero de columnas del tablero.
	 * 
	 */
	public int getCols();

	/**
	 * Consults the piece placed at position ({@code row}, {@code col}). The
	 * interpretation of ({@code row}, {@code col}) depends on the actual
	 * implementation/game. The value {@code null} typically represents an empty
	 * position.
	 * 
	 * <p>
	 * Proporciona la ficha situada en la posición ({@code row},{@code col}). La
	 * interpretacion de ({@code row},{@code col}) depende de la
	 * implementacion/juego concreto. El valor {@code null} normalmente
	 * representa una casilla vacia.
	 * 
	 * @param row
	 *            Row identifier.
	 * @param col
	 *            Column identifier.
	 * @return The {@link Piece} placed at position ({@code row}, {@code col}).
	 * 
	 * 
	 */
	public Piece getPosition(int row, int col);

	/**
	 * Places a piece {@code p} at position ({@code row}, {@code col}). The
	 * interpretation of ( {@code row}, {@code col}) depends on the actual
	 * implementation/game. The value {@code null} typically represents an empty
	 * cell.
	 * 
	 * <p>
	 * Coloca la ficha {@code p} en la posición ({@code row},{@code col}) del
	 * tablero. La interpretacion de ({@code row},{@code col}) depende de la
	 * implementacion/juego concreto. El valor {@code null} normalmente
	 * representa una casilla vacia.
	 * 
	 */
	public void setPosition(int row, int col, Piece p);

	/**
	 * Checks if the board if full. The interpretation of "full" depends on the
	 * actual game.
	 * 
	 * <p>
	 * Comprueba si el tablero esta lleno. La interpretacion de esta condicion
	 * depende de la implementacion concreta.
	 * 
	 * @return {@code true} if the board is full, {@code false} otherwise.
	 *         <p>
	 *         {@code true} si el tablero esta lleno, {@code false} si no.
	 * 
	 */
	public boolean isFull();

	/**
	 * Checks if the board is empty. The interpretation of "empty" depends on
	 * the actual game.
	 * 
	 * <p>
	 * Comprueba si el tablero esta vacio. La interpretacion de esta condicion
	 * depende de la implementacion concreta.
	 * 
	 * @return {@code true} if the board is empty, {@code false} otherwise.
	 *         <p>
	 *         {@code true} si el tablero esta vacio, {@code false} si no.
	 * 
	 */
	public boolean isEmpty();

	/**
	 * Sets the piece-count of {@code p} to {@code n}. When {@code n} is
	 * {@code null} the piece-count for {@code p} is deleted.
	 * 
	 * <p>
	 * Asigna un valor al contador de fichas de tipo {@code p} en el tablero.
	 * Los tableros disponen de un contador para cada tipo de fichas.
	 * 
	 * 
	 * @param p
	 *            The piece for which the counter is set.
	 *            <p>
	 *            Tipo de ficha que se asigna el contador.
	 * 
	 *
	 * @param n
	 *            The piece-count for {@code p}. The value {@code null} means no
	 *            piece-count.
	 *            <p>
	 *            Valor que se asigna al contador. {@code null} representa que
	 *            no hay contador para la ficha.
	 * 
	 */
	public void setPieceCount(Piece p, Integer n);

	/**
	 * Consults the piece-count for a given {@link Piece} {@code p}.
	 * 
	 * <p>
	 * Devuelve el valor del contador de fichas de tipo {@code p} en el tablero.
	 * 
	 * @param p
	 *            The piece whose piece-count is consulted.
	 *            <p>
	 *            Ficha para la que se quiere obtener el contador.
	 * 
	 * 
	 * @return The piece-count of {@code p}, where {@code null} means no
	 *         piece-count.
	 *         <p>
	 *         Valor del contador para {@code p}. Con {@code null} se indica que
	 *         no se dispone de contador para {@code p}.
	 */
	public Integer getPieceCount(Piece p);

	/**
	 * Generates a fresh copy of the board. This is used when we want simulate
	 * (in automatic players) what would happen when applying a given move, but
	 * without modifying the current board.
	 * 
	 * <p>
	 * Genera una copia del tablero. Se utiliza para simular (en los jugadores
	 * automaticos) que ocurriria cuando se realiza determinado movimiento, pero
	 * sin modificar el tablero actual.
	 * 
	 * @return A fresh copy of the board.
	 *         <p>
	 *         Una copia del tablero.
	 * 
	 */
	public Board copy();
}
