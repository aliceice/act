package de.aice.act;

import java.nio.charset.Charset;
import org.junit.Test;

import static de.aice.act.Headers.CONTENT_TYPE;
import static de.aice.act.Response.json;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public final class ResponseTest {

	@Test
	public void response_json_sets_content_type_header_to_application_json() throws Exception {
		Response response = json("[]");
		assertEquals(Status.OK, response.status);
		assertTrue(response.headers.has(CONTENT_TYPE,
		                                "application/json;charset=" + Charset.defaultCharset().displayName()));
	}
}