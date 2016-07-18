package de.aice.act.misc;

/**
 * Supplier which might throw an IOException.
 *
 * @author Eléna Ihde-Simon (elena.ihde-simon@posteo.de)
 * @version $Id$
 * @param <T> Type request result.
 */
public interface UnsafeSupplier<T> {

	/**
	 * Get value.
	 *
	 * @return value.
	 * @throws Exception if something goes wrong.
	 */
	T get() throws Exception;

}
