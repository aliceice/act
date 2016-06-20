package de.aice.act.misc;

import de.aice.act.ActException;
import java.io.IOException;

/**
 * Exception utilities.
 *
 * @author Eléna Ihde-Simon (elena.ihde-simon@posteo.de)
 * @version $Id$
 */
public interface Exceptions {

	/**
	 * Retrieve value from supplier and rethrow exceptions as a RuntimeException.
	 *
	 * @param supplier value supplier.
	 * @param <T>      type request value.
	 * @return value.
	 */
	static <T> T unchecked(final IOSupplier<T> supplier) {
		try {
			return supplier.get();
		} catch (final IOException e) {
			throw new ActException(e);
		}
	}

	/**
	 * Execute activity and rethrow exceptions as a RuntimeException.
	 *
	 * @param activity activity to execute.
	 */
	static void unchecked(final UnsafeActivity activity) {
		try {
			activity.execute();
		} catch (final Exception e) {
			e.printStackTrace();
			throw new ActException(e);
		}
	}

}
