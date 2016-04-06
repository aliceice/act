package de.aice.act.http;

import de.aice.act.Request;
import de.aice.act.Response;
import de.aice.act.misc.Strings;
import java.io.ByteArrayOutputStream;
import java.nio.charset.Charset;
import org.junit.Test;

import static de.aice.act.Headers.ACCEPT;
import static de.aice.act.Headers.HOST;
import static de.aice.act.misc.InputStreams.stream;
import static de.aice.act.misc.Strings.CR_LF;
import static de.aice.act.misc.Strings.joinOn;
import static de.aice.act.misc.Structures.tryWith;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public final class BackStageSupportTest {

	private static final String HTTP_REQUEST = joinOn(CR_LF,
	                                                  "GET /path/file.html HTTP/1.1",
	                                                  "Accept: text/plain",
	                                                  "Host: localhost",
	                                                  "",
	                                                  "Some body content");

	@Test
	public void readValidRequestHasCorrectMethod() throws Exception {
		Request request = readRequest();
		assertNotNull(request);
		assertEquals("GET", request.method);
	}

	@Test
	public void readValidRequestHasCorrectPath() throws Exception {
		Request request = readRequest();
		assertNotNull(request);
		assertEquals("/path/file.html", request.path);
	}

	@Test
	public void readValidRequestHasCorrectHeaders() throws Exception {
		Request request = readRequest();
		assertNotNull(request);
		assertNotNull(request.headers);
		assertTrue(request.headers.has(HOST, "localhost"));
		assertTrue(request.headers.has(ACCEPT, "text/plain"));
	}

	@Test
	public void readValidRequestHasCorrectBody() throws Exception {
		Request request = readRequest();
		assertNotNull(request);
		assertNotNull(request.body);
		assertEquals("Some body content", request.body);
	}

	@Test
	public void writeValidResponseWithBody() throws Exception {
		Response response = Response.ok().body("Hello World!");
		tryWith(ByteArrayOutputStream::new, baos -> {
			BackStageSupport.writeResponse(response, baos);
			String result = new String(baos.toByteArray(), Charset.defaultCharset());
			assertTrue(result.startsWith("HTTP/1.1 200"));
			assertTrue(result.contains("Content-Length: 12"));
			assertTrue(result.contains("Date: "));
			assertTrue(result.contains(Strings.EMPTY + Strings.CR_LF));
			assertTrue(result.endsWith("Hello World!"));
		});

	}

	private static Request readRequest() throws Exception {
		return BackStageSupport.readRequest(stream(HTTP_REQUEST));
	}

}