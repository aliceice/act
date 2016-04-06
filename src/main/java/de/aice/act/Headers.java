package de.aice.act;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * HTTP Headers.
 *
 * @author Eléna Ihde-Simon (elena.ihde-simon@posteo.de)
 * @version $Id$
 */
public interface Headers {

	String ACCEPT         = "Accept";
	String CONTENT_LENGTH = "Content-Length";
	String CONTENT_TYPE   = "Content-Type";
	String DATE           = "Date";
	String HOST           = "Host";

	Optional<String> getSingle(String name);

	Map<String,List<String>> toMap();

	boolean has(String name, String value);
}
