package de.aice.act;

import de.aice.act.misc.Strings;
import java.nio.charset.Charset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import static de.aice.act.Status.NOT_FOUND;
import static de.aice.act.Status.OK;
import static de.aice.act.misc.Strings.CR_LF;
import static de.aice.act.misc.Strings.joinOn;

/**
 * HTTP Response value object.
 *
 * @author Eléna Ihde-Simon (elena.ihde-simon@posteo.de)
 * @version $Id$
 */
public final class Response {

	public final Status  status;
	public final Headers headers;
	public final String  body;

	/**
	 * 200 OK response.
	 *
	 * @return new response.
	 */
	public static Response ok() {
		return status(OK);
	}

	/**
	 * 200 OK response with body.
	 *
	 * @param body body.
	 * @return new response.
	 */
	public static Response ok(final Object body) {
		return ok().body(body.toString());
	}

	/**
	 * 200 OK response with JSON header (application/json;charset=utf-8).
	 *
	 * @param json JSON body
	 * @return new response.
	 */
	public static Response json(final String json) {
		return ok().header(Headers.CONTENT_TYPE,
		                   String.format("application/json;charset=%s", Charset.defaultCharset().displayName()))
		           .body(json);
	}

	/**
	 * 404 Not Found response.
	 *
	 * @return new response.
	 */
	public static Response notFound() {
		return status(NOT_FOUND).body("404 not found");
	}

	/**
	 * Response with status.
	 *
	 * @param status status to use.
	 * @return new response with given status.
	 */
	public static Response status(final Status status) {
		return new Response(status).header(Headers.DATE,
		                                   ZonedDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME))
		                           .header(Headers.CONTENT_TYPE, "text/plain")
		                           .header(Headers.CONTENT_LENGTH, 0);
	}

	/**
	 * Create response with header.
	 *
	 * @param name  header name.
	 * @param value header value.
	 * @return new response with appended header.
	 */
	public Response header(final String name, final Object value) {
		return new Response(this.status, this.headers.with(name, value), this.body);
	}

	/**
	 * Create response with body.
	 *
	 * @param body response body.
	 * @return new response with given body.
	 */
	public Response body(final String body) {
		return new Response(this.status, this.headers.with(Headers.CONTENT_LENGTH, body.length()), body);
	}

	private Response(final Status status) {
		this(status, Strings.EMPTY);
	}

	private Response(final Status status, final String body) {
		this(status, Headers.headers(), body);
	}

	private Response(final Status status, final Headers headers, final String body) {
		this.status = status;
		this.headers = headers;
		this.body = body;
	}

	@Override
	public String toString() {
		return joinOn(CR_LF,
		              String.format("HTTP/1.1 %s", this.status),
		              this.headers.toString(),
		              Strings.EMPTY,
		              this.body);
	}

}
