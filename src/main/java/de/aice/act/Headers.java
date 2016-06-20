package de.aice.act;

import de.aice.act.misc.Strings;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static de.aice.act.misc.Strings.joinOn;

/**
 * Headers implementation based on a map.
 *
 * @author Eléna Ihde-Simon (elena.ihde-simon@posteo.de)
 * @version $Id$
 */
public final class Headers {
	
	public static final String ACCEPT         = "Accept";
	public static final String CONTENT_LENGTH = "Content-Length";
	public static final String CONTENT_TYPE   = "Content-Type";
	public static final String DATE           = "Date";
	public static final String HOST           = "Host";
	
	/**
	 * Empty Headers.
	 *
	 * @return new Headers
	 */
	public static Headers headers() {
		return headers(new HashMap<>());
	}
	
	/**
	 * Headers with single header.
	 *
	 * @param name  header name
	 * @param value header value
	 * @return Headers with single entry.
	 */
	public static Headers header(final String name, final Object value) {
		Map<String, List<String>> headersByName = new HashMap<>();
		headersByName.put(name, Collections.singletonList(value.toString()));
		return headers(headersByName);
	}
	
	/**
	 * Headers request a map request header by name.
	 *
	 * @param headersByName map request header.
	 * @return new Headers based on Map.
	 */
	public static Headers headers(final Map<String, List<String>> headersByName) {
		return new Headers(headersByName);
	}
	
	private final Map<String, List<String>> headersByName;
	
	private Headers(final Map<String, List<String>> map) {
		this.headersByName = map;
	}
	
	/**
	 * Get single header value.
	 *
	 * @param name name of header
	 * @return value or empty optional.
	 */
	public Optional<String> getSingle(final String name) {
		return this.headersByName.containsKey(name)
		       ? Optional.of(this.headersByName.get(name).get(0))
		       : Optional.empty();
	}
	
	/**
	 * Create new headers with given header.
	 *
	 * @param name  header name
	 * @param value header value
	 * @return new Headers
	 */
	public Headers with(final String name, final Object value) {
		Map<String, List<String>> newHeadersByName = new HashMap<>(this.headersByName);
		newHeadersByName.put(name, Collections.singletonList(value.toString()));
		return Headers.headers(newHeadersByName);
	}
	
	/**
	 * Check whether a given header exists and has given value.
	 *
	 * @param name  header name
	 * @param value header value
	 * @return true if header with value exists, false otherwise.
	 */
	public boolean has(final String name, final String value) {
		return this.headersByName.containsKey(name)
		       && this.headersByName.get(name).contains(value);
	}
	
	@Override
	public String toString() {
		return this.headersByName.entrySet()
		                         .stream()
		                         .map(entry -> String.format("%s: %s", entry.getKey(), joinOn(", ", entry.getValue())))
		                         .reduce(joinOn(Strings.CR_LF))
		                         .orElse(Strings.EMPTY);
	}
	
}
