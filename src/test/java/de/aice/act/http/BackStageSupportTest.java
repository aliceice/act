package de.aice.act.http;

import de.aice.act.Request;
import de.aice.act.Response;
import de.aice.act.misc.Strings;
import java.io.ByteArrayOutputStream;
import java.lang.reflect.Constructor;
import java.nio.charset.Charset;
import org.junit.Test;

import static de.aice.act.Headers.ACCEPT;
import static de.aice.act.Headers.HOST;
import static de.aice.act.misc.InputStreams.stream;
import static de.aice.act.misc.Strings.CR;
import static de.aice.act.misc.Strings.CR_LF;
import static de.aice.act.misc.Strings.joinOn;
import static de.aice.act.misc.Structures.tryWith;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public final class BackStageSupportTest {

	private static final String HTTP_REQUEST = joinOn(CR_LF,
	                                                  "GET /path/file.html HTTP/1.1",
	                                                  "Accept: text/plain",
	                                                  "Content-Length: 17",
	                                                  "Host: localhost",
	                                                  "",
	                                                  "Some body content");

	@Test
	public void test_private_constructor() throws Exception {
		Constructor<BackStageSupport> constructor = BackStageSupport.class.getDeclaredConstructor();
		assertFalse(constructor.isAccessible());
		constructor.setAccessible(true);
		constructor.newInstance();
	}

	@Test
	public void read_valid_request_has_correct_method() throws Exception {
		Request request = readRequest();
		assertNotNull(request);
		assertEquals("GET", request.method);
	}

	@Test
	public void read_valid_request_has_correct_path() throws Exception {
		Request request = readRequest();
		assertNotNull(request);
		assertEquals("/path/file.html", request.path);
	}

	@Test
	public void read_valid_request_has_correct_headers() throws Exception {
		Request request = readRequest();
		assertNotNull(request);
		assertNotNull(request.headers);
		assertTrue(request.headers.has(HOST, "localhost"));
		assertTrue(request.headers.has(ACCEPT, "text/plain"));
	}

	@Test
	public void read_valid_request_has_correct_body() throws Exception {
		Request request = readRequest();
		assertNotNull(request);
		assertNotNull(request.body);
		assertEquals("Some body content", request.body);
	}

	@Test
	public void read_valid_request_without_content_length_has_correct_body() throws Exception {
		Request request = BackStageSupport.readRequest(stream(HTTP_REQUEST.replace(CR_LF + "Content-Length: 17", "")));
		assertNotNull(request);
		assertEquals("Some body content", request.body);
	}

	@Test
	public void read_invalid_request_should_throw_exception() throws Exception {
		try {
			String request = joinOn(CR,
			                        "GET /path/file.html HTTP/1.1",
			                        "Accept: text/plain",
			                        "Content-Length: 17",
			                        "Host: localhost",
			                        "",
			                        "Some body content");
			BackStageSupport.readRequest(stream(request));
			fail("Should throw IllegalArgumentException");
		} catch (IllegalArgumentException e) {
			assertEquals("No LF after CR", e.getMessage());
		}
	}

	@Test
	public void write_valid_response_with_body() throws Exception {
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