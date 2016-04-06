package de.aice.act.misc;

import java.util.Collection;
import java.util.function.BinaryOperator;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;

/**
 * String utilities.
 *
 * @author Eléna Ihde-Simon (elena.ihde-simon@posteo.de)
 * @version $Id$
 */
public final class Strings {

	public static final String CR_LF      = "\r\n";
	public static final String EMPTY      = "";
	public static final String WHITESPACE = " ";

	private Strings() {

	}

	/**
	 * Join input on given delimiter.
	 *
	 * @param delimiter input delimiter.
	 * @param input     array of CharSequences.
	 * @return joined string.
	 */
	public static String joinOn(final CharSequence delimiter, final CharSequence... input) {
		return Stream.of(input).collect(joining(delimiter));
	}

	/**
	 * Join input on given delimiter.
	 *
	 * @param delimiter input delimiter.
	 * @param input     Collection of objects.
	 * @return joined string.
	 */
	public static String joinOn(final CharSequence delimiter, final Collection<?> input) {
		return input.stream().map(Object::toString).collect(joining(delimiter));
	}

	public static BinaryOperator<String> joinOn(final CharSequence delimiter) {
		return (s1, s2) -> s1 + delimiter + s2;
	}
}
