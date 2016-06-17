package de.aice.act;

import java.io.IOException;
import java.util.Optional;
import org.junit.Test;

import static de.aice.act.Fallback.fallbackChain;
import static de.aice.act.Request.request;
import static de.aice.act.Response.ok;
import static org.junit.Assert.assertEquals;

public final class FallbackTest {

	@Test
	public void fallback_with_a_chain_returns_first_successful_result() throws Exception {
		String message = "It works!";

		Act fallback = fallbackChain(r -> { throw new IOException(); },
		                             (r, e) -> Optional.empty(),
		                             (r, e) -> Optional.of(ok(message)));
		Response response = fallback.on(request("GET"));
		assertEquals(message, response.body);
	}

	@Test
	public void fallback_returns_response_if_no_exception_occurs() throws Exception {
		Response response = fallbackChain(r -> ok()).on(request("GET"));
		assertEquals(Status.OK, response.status);
	}
}