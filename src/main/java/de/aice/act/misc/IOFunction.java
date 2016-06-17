package de.aice.act.misc;

import java.io.IOException;

/**
 * Function which might throw an IOException.
 *
 * @author Eléna Ihde-Simon (elena.ihde-simon@posteo.de)
 * @version $Id$
 * @param <T> Type request input.
 * @param <R> Type request result.
 */
interface IOFunction<T, R> {

	/**
	 * Apply t onto function.
	 *
	 * @param t input value.
	 * @return result value.
	 * @throws IOException if something goes wrong.
	 */
	R apply(T t) throws IOException;

}
