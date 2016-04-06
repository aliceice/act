package de.aice.act;

import de.aice.act.misc.Strings;
import java.net.HttpURLConnection;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import static de.aice.act.misc.Strings.CR_LF;
import static de.aice.act.misc.Strings.joinOn;

/**
 * HTTP Response value object.
 *
 * @author Eléna Ihde-Simon (elena.ihde-simon@posteo.de)
 * @version $Id$
 */
public final class Response {

	public final int     status;
	public final Headers headers;
	public final String  body;

	/**
	 * 200 OK response.
	 *
	 * @return new response.
	 */
	public static Response ok() {
		return status(HttpURLConnection.HTTP_OK);
	}

	public static Response ok(String body) {
		return Response.ok().body(body);
	}

	public static Response json(String json) {
		return Response.ok()
		               .header(Headers.CONTENT_TYPE, "application/json;charset=utf-8")
		               .body(json);
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

	private Response(final int status, final String body) {
		this(status, new MapHeaders(), body);
	}

	private Response(final int status, final Headers headers, final String body) {
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
		return new Response(this.status, new MapHeaders(this.headers, header, value), this.body);
	}

	/**
	 * Create response with body.
	 *
	 * @param body response body.
	 * @return new response with given body.
	 */
	public Response body(final String body) {
		return new Response(this.status, new MapHeaders(this.headers, Headers.CONTENT_LENGTH, body.length()), body);
	}

	@Override
	public String toString() {
		return joinOn(CR_LF,
		              String.format("HTTP/1.1 %d", this.status),
		              this.headers.toString(),
		              Strings.EMPTY,
		              this.body);
	}

}
