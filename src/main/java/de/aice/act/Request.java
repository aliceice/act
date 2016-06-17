package de.aice.act;

import de.aice.act.misc.Strings;

import static de.aice.act.Headers.headers;

/**
 * HTTP Request value object.
 *
 * @author Eléna Ihde-Simon (elena.ihde-simon@posteo.de)
 * @version $Id$
 */
public final class Request {

	private static final String DEFAULT_PATH = "/";

	public final String  method;
	public final String  path;
	public final Headers headers;
	public final String  body;

	/**
	 * Request with given method.
	 *
	 * @param method method to use
	 * @return new Request
	 */
	public static Request request(final String method) {
		return request(method, DEFAULT_PATH);
	}

	/**
	 * Request with given path and method.
	 *
	 * @param method method to use
	 * @param path   path to use.
	 * @return new Request
	 */
	public static Request request(final String method, final String path) {
		return request(method, path, headers());
	}

	/**
	 * Request with given headers.
	 *
	 * @param headers headers to use
	 * @return new Request
	 */
	public static Request request(final Headers headers) {
		return request("GET", DEFAULT_PATH, headers, Strings.EMPTY);
	}

	/**
	 * Request with given method, path and headers.
	 *
	 * @param method  method to use
	 * @param path    path to use
	 * @param headers headers to use
	 * @return new Request.
	 */
	public static Request request(final String method, final String path, final Headers headers) {
		return request(method, path, headers, Strings.EMPTY);
	}

	/**
	 * Full request.
	 *
	 * @param method  method
	 * @param path    path
	 * @param headers headers
	 * @param body    body
	 * @return new Request
	 */
	public static Request request(final String method, final String path, final Headers headers, final String body) {
		return new Request(method, path, headers, body);
	}

	private Request(final String method, final String path, final Headers headers, final String body) {
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
