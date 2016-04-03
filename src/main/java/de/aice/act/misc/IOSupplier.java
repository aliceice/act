package de.aice.act.misc;

import java.io.IOException;

/**
 * Supplier which might throw an IOException.
 *
 * @author Eléna Ihde-Simon (elena.ihde-simon@posteo.de)
 * @version $Id$
 * @param <T> Type of result.
 */
public interface IOSupplier<T> {

	/**
	 * Get value.
	 *
	 * @return value.
	 * @throws IOException if something goes wrong.
	 */
	T get() throws IOException;

}
