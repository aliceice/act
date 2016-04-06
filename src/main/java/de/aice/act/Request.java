package de.aice.act;

/**
 * HTTP Request value object.
 *
 * @author Eléna Ihde-Simon (elena.ihde-simon@posteo.de)
 * @version $Id$
 */
public final class Request {

	public final String  method;
	public final String  path;
	public final Headers headers;
	public final String  body;

	public Request(final String method, final String path, final Headers headers, final String body) {
		this.method = method;
		this.path = path;
		this.headers = headers;
		this.body = body;
	}

	@Override
	public String toString() {
		return String.format("%s %s %s",
		                     this.method,
		                     this.headers.getSingle(Headers.HOST).orElse(""),
		                     this.path);
	}
}
