package de.aice.act.http;

import de.aice.act.Headers;
import de.aice.act.MapHeaders;
import de.aice.act.Request;
import de.aice.act.Response;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import static de.aice.act.misc.InputStreams.readExactly;
import static de.aice.act.misc.InputStreams.readLine;
import static de.aice.act.misc.InputStreams.readUntil;
import static de.aice.act.misc.Strings.EMPTY;
import static de.aice.act.misc.Strings.WHITESPACE;
import static java.nio.charset.Charset.defaultCharset;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toMap;

/**
 * BackStage support.
 *
 * @author Eléna Ihde-Simon (elena.ihde-simon@posteo.de)
 * @version $Id$
 */
final class BackStageSupport {

	private BackStageSupport() {

	}

	/**
	 * Read HTTP request from input stream.
	 *
	 * @param stream stream to use.
	 * @return HTTP request.
	 * @throws IOException if something goes wrong.
	 */
	static Request readRequest(final InputStream stream) throws IOException {
		String requestLine = readLine(stream);
		Map<String, List<String>> headers = headers(stream);
		String body = readExactly(contentLength(headers, stream.available()), stream);
		return new Request(method(requestLine), path(requestLine), new MapHeaders(headers), body);
	}

	private static Map<String, List<String>> headers(final InputStream stream) throws IOException {
		List<String> lines = readUntil(EMPTY, stream);
		return lines.stream()
		            .map(line -> line.split(": "))
		            .collect(toMap(parts -> parts[0], parts -> asList(parts[1].split(","))));
	}

	private static long contentLength(final Map<String, List<String>> headers, final int defaultValue) {
		return headers.containsKey(Headers.CONTENT_LENGTH)
		       ? Long.valueOf(headers.get(Headers.CONTENT_LENGTH).get(0))
		       : defaultValue;
	}

	private static String method(final String requestLine) {
		return requestLine.split(WHITESPACE)[0];
	}

	private static String path(final String requestLine) {
		return requestLine.split(WHITESPACE)[1];
	}

	/**
	 * Write HTTP response to output stream.
	 *
	 * @param response response to write.
	 * @param out      stream to use.
	 * @throws IOException if something goes wrong.
	 */
	static void writeResponse(final Response response, final OutputStream out) throws IOException {
		try {
			out.write(response.toString().getBytes(defaultCharset()));
		} finally {
			out.close();
		}
	}
}
