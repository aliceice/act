package de.aice.act.misc;

import de.aice.act.ActException;

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
	 * @param <T>      types request value.
	 * @return value.
	 */
	static <T> T unchecked(final UnsafeSupplier<T> supplier) {
		try {
			return supplier.get();
		} catch (final Exception e) {
			throw new ActException(e.getMessage(), e);
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
