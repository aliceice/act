package de.aice.act;

import de.aice.act.misc.Strings;
import java.net.HttpURLConnection;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static de.aice.act.misc.Strings.CR_LF;
import static de.aice.act.misc.Strings.joinOn;
import static java.util.Collections.emptyMap;
import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.joining;

/**
 * HTTP Response value object.
 *
 * @author Eléna Ihde-Simon (elena.ihde-simon@posteo.de)
 * @version $Id$
 */
public final class Response {

	public final int                       status;
	public final Map<String, List<Object>> headers;
	public final String                    body;

	public Response(final int status, final String body) {
		this(status, emptyMap(), body);
	}

	public Response(final int status, final Map<String, List<Object>> headers, final String body) {
		this.status = status;
		this.headers = headers;
		this.body = body;
	}

	/**
	 * Create response with headers.
	 *
	 * @param header header name.
	 * @param value  headers value.
	 * @return new response with appended headers.
	 */
	public Response header(final String header, final Object value) {
		final Map<String, List<Object>> newHeaders = new HashMap<>(this.headers);
		newHeaders.put(header, singletonList(value));
		return new Response(this.status, newHeaders, this.body);
	}

	/**
	 * Create response with body.
	 *
	 * @param body response body.
	 * @return new response with given body.
	 */
	public Response body(final String body) {
		final Map<String, List<Object>> newHeaders = new HashMap<>(this.headers);
		newHeaders.put(Headers.CONTENT_LENGTH, singletonList(body.length()));
		return new Response(this.status, newHeaders, body);
	}

	@Override
	public String toString() {
		return joinOn(CR_LF,
		              String.format("HTTP/1.1 %d", this.status),
		              headersString(),
		              Strings.EMPTY,
		              this.body);
	}

	private String headersString() {
		return this.headers.entrySet()
		                   .stream()
		                   .map(entry -> String.format("%s: %s", entry.getKey(), joinOn(",", entry.getValue())))
		                   .collect(joining(CR_LF));
	}

	/**
	 * 200 OK response.
	 *
	 * @return new response.
	 */
	public static Response ok() {
		return status(HttpURLConnection.HTTP_OK);
	}

	/**
	 * 404 Not Found response.
	 *
	 * @return new response.
	 */
	public static Response notFound() {
		return status(HttpURLConnection.HTTP_NOT_FOUND).body("404 not found");
	}

	/**
	 * Response with status.
	 *
	 * @param status status to use.
	 * @return new response with given status.
	 */
	public static Response status(final int status) {
		return new Response(status, Strings.EMPTY).header(Headers.DATE,
		                                                  ZonedDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME))
		                                          .header(Headers.CONTENT_TYPE, "text/plain")
		                                          .header(Headers.CONTENT_LENGTH, 0);
	}

}
