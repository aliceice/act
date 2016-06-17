package de.aice.act.misc;

import java.io.IOException;

/**
 * Consumer which might throw an IOException.
 *
 * @author Eléna Ihde-Simon (elena.ihde-simon@posteo.de)
 * @version $Id$
 * @param <T> Type request input.
 */
public interface IOConsumer<T> {

	/**
	 * Consume t value.
	 *
	 * @param t value to consume.
	 * @throws IOException if something goes wrong.
	 */
	void accept(T t) throws IOException;
}
