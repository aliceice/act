package de.aice.act.misc;

import static de.aice.act.misc.Exceptions.unchecked;

/**
 * Code structure utilities.
 *
 * @author Eléna Ihde-Simon (elena.ihde-simon@posteo.de)
 * @version $Id$
 */
public interface Structures {

	/**
	 * Try consumer with auto closeable resource.
	 *
	 * @param r   resource supplier.
	 * @param c   consumer request resource.
	 * @param <R> types request resource (implements AutoCloseable).
	 * @throws Exception if something goes wrong.
	 */
	static <R extends AutoCloseable> void tryWith(final UnsafeSupplier<R> r,
	                                              final UnsafeConsumer<R> c) throws Exception {
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
	 * @param <R> Resource types (must implement AutoCloseable).
	 * @param <T> Result types.
	 * @return Result value.
	 * @throws Exception if something goes wrong.
	 */
	static <R extends AutoCloseable, T> T tryWith(final UnsafeSupplier<R> r,
	                                              final IOFunction<R, T> f) throws Exception {
		R resource = r.get();
		try {
			return f.apply(resource);
		} finally {
			unchecked(resource::close);
		}
	}

}
