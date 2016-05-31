package es.ucm.fdi.tp.basecode.bgame;

import java.awt.Color;
import java.util.Iterator;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * A class with some (static) miscellaneous methods.
 * 
 * <p>
 * Miscelanea: una clase con algunos metodos utiles.
 *
 */
public class Utils {

	private static Random randGeneratorDiffSeed = new Random();

	/**
	 * An {@link Executor} created using
	 * {@link Executors#newCachedThreadPool()}.  
	 */
	public final static ExecutorService worker = Executors.newCachedThreadPool();
	
	/**
	 * Returns a random integer between 0 (inclusive) and n (exclusive). It
	 * simply delegates to {@link Random#nextInt(int)}.
	 * 
	 * <p>
	 * Devuelve un numero entero entre 0 (incluido) y n (excluido). Utiliza
	 * {@link Random#nextInt(int)}.
	 * 
	 * @param n
	 *            The upper limit (exclusive) of the generated random integer.
	 *            <p>
	 *            Limite superior (excluido) del numero aleatorio generado.
	 * @return A random integer between 0 (inclusive) and n (exclusive).
	 *         <p>
	 *         Numero entero aleatorio entre 0 (incluido) y n (excluido).
	 */
	public static int randomInt(int n) {
		return randGeneratorDiffSeed.nextInt(n);
	}

	/**
	 * Generates a random {@link Color}.
	 * 
	 * <p>
	 * Genera un color aleatorio de tipo {@link Color}.
	 * 
	 * @return A random color
	 *         <p>
	 *         Un color aleatorio.
	 */
	public static Color randomColor() {
		return new Color(Utils.randomInt(256), Utils.randomInt(256), Utils.randomInt(256), Utils.randomInt(256));
	}

	/**
	 * Generates an iterator for generating random colors. It generates the same
	 * sequences of colors over different runs since it always uses the same
	 * seed.
	 * 
	 * <p>
	 * Genera un iterador para generar colores aleatorios. Genera las mismas
	 * secuencias de colores en diferentes ejecuciones porque siempre utiliza la
	 * misma semilla.
	 * 
	 * @return An iterator for generating random colors.
	 */
	public static Iterator<Color> colorsGenerator() {

		Iterator<Color> i = new Iterator<Color>() {
			// since we use a fixed seed, we always get the same sequence of
			// colors
			//
			private Random r = new Random(23061973);

			@Override
			public Color next() {
				return new Color(r.nextInt(256), r.nextInt(256), r.nextInt(256));
			}

			@Override
			public boolean hasNext() {
				return true;
			}

			@Override
			public void remove() {
				throw new UnsupportedOperationException("read-only iterator");
			}
		};

		return i;

	}

	/**
	 * Sleeps {@link delay} milliseconds.
	 * 
	 * <p>
	 * Para la ejecucion (de la hebra) durante {@link delay} milisegundos.
	 * 
	 * @param delay
	 *            milliseconds
	 */
	public static void sleep(int delay) {
		try {
			Thread.sleep(delay);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}

}
