package de.aice.act.misc;

import java.io.IOException;

/**
 * Exception utilities.
 *
 * @author Eléna Ihde-Simon (elena.ihde-simon@posteo.de)
 * @version $Id$
 */
public final class Exceptions {

	private Exceptions() {

	}

	/**
	 * Retrieve value from supplier and rethrow exceptions as a RuntimeException.
	 *
	 * @param supplier value supplier.
	 * @param <T>      type of value.
	 * @return value.
	 */
	public static <T> T unchecked(final IOSupplier<T> supplier) {
		try {
			return supplier.get();
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Execute activity and rethrow exceptions as a RuntimeException.
	 *
	 * @param activity activity to execute.
	 */
	public static void unchecked(final UnsafeActivity activity) {
		try {
			activity.execute();
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
	}

}
