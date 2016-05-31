package es.ucm.fdi.tp.basecode.bgame.model;

/**
 * A generic class for representing a pair of values.
 *
 * @param <A>
 *            The type of the first element.
 * @param <B>
 *            The type of the second element.
 * 
 *            <p>
 *            Clase generica para representar un par de valores.
 */
public class Pair<A, B> {

	/**
	 * The first element of the pair.
	 * 
	 * <p>
	 * Primer elemento del par.
	 */
	private A first;

	/**
	 * The second element of the pair.
	 * 
	 * <p>
	 * Segundo elemento del par.
	 */
	private B second;

	/**
	 * Construct a pair.
	 * 
	 * <p>
	 * Construye un par.
	 * 
	 * @param first
	 *            The first element of the pair.
	 *            <p>
	 *            Primer elemento del par.
	 * @param second
	 *            The second element of the pair.
	 *            <p>
	 *            Segundo elemento del par.
	 */
	public Pair(A first, B second) {
		this.first = first;
		this.second = second;
	}

	/**
	 * Consults the first element.
	 * 
	 * <p>
	 * Proporciona el primer elemento.
	 * 
	 * @return The first element of the pair.
	 *         <p>
	 *         El primer elemento del par.
	 */
	public A getFirst() {
		return first;
	}

	/**
	 * Consults the second element.
	 * 
	 * <p>
	 * Proporciona el segundo elemento.
	 * 
	 * @return The second element of the pair.
	 *         <p>
	 *         El segundo elemento del par.
	 */
	public B getSecond() {
		return second;
	}

}
