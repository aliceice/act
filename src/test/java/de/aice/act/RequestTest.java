package de.aice.act;

import org.junit.Test;

import static de.aice.act.Headers.HOST;
import static de.aice.act.Headers.header;
import static de.aice.act.Request.request;
import static org.junit.Assert.assertEquals;

public final class RequestTest {

	@Test
	public void toString_uses_method_host_and_path() throws Exception {
		Request request = request("GET", "/", header(HOST, "localhost"));
		assertEquals("GET localhost /", request.toString());
	}

	@Test
	public void toString_can_leave_host_empty_if_not_set() throws Exception {
		Request request = request("GET", "/");
		assertEquals("GET  /", request.toString());
	}
}