package de.aice.act.misc;

import java.io.IOException;

import static de.aice.act.misc.Exceptions.unchecked;

/**
 * Code structure utilities.
 *
 * @author Eléna Ihde-Simon (elena.ihde-simon@posteo.de)
 * @version $Id$
 */
public final class Structures {

	private Structures() {

	}

	/**
	 * Try consumer with auto closeable resource.
	 *
	 * @param r   resource supplier.
	 * @param c   consumer of resource.
	 * @param <R> type of resource (implements AutoCloseable).
	 * @throws IOException if something goes wrong.
	 */
	public static <R extends AutoCloseable> void tryWith(final IOSupplier<R> r,
	                                                     final IOConsumer<R> c) throws IOException {
		R resource = r.get();
		try {
			c.accept(resource);
		} finally {
			unchecked(resource::close);
		}
	}

	/**
	 * Try function with auto closeable resource.
	 *
	 * @param r   resource supplier.
	 * @param f   function to execute.
	 * @param <R> Resource type (must implement AutoCloseable).
	 * @param <T> Result type.
	 * @return Result value.
	 * @throws IOException if something goes wrong.
	 */
	public static <R extends AutoCloseable, T> T tryWith(final IOSupplier<R> r,
	                                                     final IOFunction<R, T> f) throws IOException {
		R resource = r.get();
		try {
			return f.apply(resource);
		} finally {
			unchecked(resource::close);
		}
	}

}
