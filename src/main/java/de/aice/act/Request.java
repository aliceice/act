package de.aice.act;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * HTTP Request value object.
 *
 * @author Eléna Ihde-Simon (elena.ihde-simon@posteo.de)
 * @version $Id$
 */
public final class Request {

	public final String                    method;
	public final String                    path;
	public final Map<String, List<String>> headers;
	public final String                    body;

	public Request(final String method, final String path, final Map<String, List<String>> headers, final String body) {
		this.method = method;
		this.path = path;
		this.headers = headers;
		this.body = body;
	}

	@Override
	public String toString() {
		return String.format("%s %s %s",
		                     this.method,
		                     this.headers.getOrDefault(Headers.HOST, Collections.singletonList("")).get(0),
		                     this.path);
	}
}
