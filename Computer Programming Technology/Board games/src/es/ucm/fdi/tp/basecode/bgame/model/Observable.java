package es.ucm.fdi.tp.basecode.bgame.model;

/**
 * 
 * Generic interface for the Observable pattern.
 * 
 * <p>
 * Interfaz genérico para el patrón Observable
 * 
 * @param <T>
 *            Type of observers
 * 
 *            <p>
 *            Tipo de observadores
 */
public interface Observable<T> {
	/**
	 * Adds an observer.
	 * 
	 * <p>
	 * Añade un observador
	 * 
	 * @param o
	 *            An observer
	 * 
	 *            <p>
	 *            Un observador
	 */
	public void addObserver(T o);

	/**
	 * Removes an observer.
	 * 
	 * <p>
	 * Elimina un observador
	 * 
	 * @param o
	 *            An observer
	 * 
	 *            <p>
	 *            Un observador
	 */
	public void removeObserver(T o);
}
