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
public final class MapHeaders implements Headers {

	private final Map<String, List<String>> headersByName;

	public MapHeaders() {
		this(new HashMap<>());
	}

	public MapHeaders(Headers headers, String header, Object value) {
		this(headers.toMap());
		this.headersByName.put(header, Collections.singletonList(value.toString()));
	}

	public MapHeaders(Map<String, List<String>> map) {
		this.headersByName = map;
	}

	@Override
	public Optional<String> getSingle(String name) {
		return this.headersByName.containsKey(name)
		       ? Optional.of(this.headersByName.get(name).get(0))
		       : Optional.empty();
	}

	@Override
	public Map<String, List<String>> toMap() {
		return new HashMap<>(this.headersByName);
	}

	@Override
	public boolean has(String name, String value) {
		return this.headersByName.containsKey(name)
		       && this.headersByName.get(name).contains(value);
	}

	@Override
	public String toString() {
		return this.headersByName.entrySet()
		                         .stream()
		                         .map(entry -> String.format("%s: %s", entry.getKey(), joinOn(", ", entry.getValue())))
		                         .reduce(joinOn(Strings.CR_LF))
		                         .orElseGet(String::new);
	}
}
