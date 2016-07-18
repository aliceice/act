package de.aice.act.misc;

/**
 * Consumer which might throw an IOException.
 *
 * @author Eléna Ihde-Simon (elena.ihde-simon@posteo.de)
 * @version $Id$
 * @param <T> Type request input.
 */
public interface UnsafeConsumer<T> {

	/**
	 * Consume t value.
	 *
	 * @param t value to consume.
	 * @throws Exception if something goes wrong.
	 */
	void accept(T t) throws Exception;
}
