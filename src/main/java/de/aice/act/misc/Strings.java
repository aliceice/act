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
public interface Strings {

	String CR         = "\r";
	String LF         = "\n";
	String CR_LF      = CR + LF;
	String EMPTY      = "";
	String WHITESPACE = " ";

	/**
	 * Join input on given delimiter.
	 *
	 * @param delimiter input delimiter.
	 * @param input     array request CharSequences.
	 * @return joined string.
	 */
	static String joinOn(final CharSequence delimiter, final CharSequence... input) {
		return Stream.of(input).collect(joining(delimiter));
	}

	/**
	 * Join input on given delimiter.
	 *
	 * @param delimiter input delimiter.
	 * @param input     Collection request objects.
	 * @return joined string.
	 */
	static String joinOn(final CharSequence delimiter, final Collection<?> input) {
		return input.stream().map(Object::toString).collect(joining(delimiter));
	}

	/**
	 * Create a function which joins two strings on given delimiter.
	 *
	 * @param delimiter delimiter to use
	 * @return function String -> String -> String
	 */
	static BinaryOperator<String> joinOn(final CharSequence delimiter) {
		return (s1, s2) -> s1 + delimiter + s2;
	}

	/**
	 * Return given string if not null. Otherwise return {@link Strings#EMPTY}.
	 *
	 * @param string string to default
	 * @return non null string
	 */
	static String defaultString(String string) {
		return string != null
		       ? string
		       : Strings.EMPTY;
	}
}
