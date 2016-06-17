package de.aice.act;

import java.util.Optional;
import org.junit.Test;

import static de.aice.act.Fork.method;
import static de.aice.act.Fork.path;
import static de.aice.act.Fork.select;
import static de.aice.act.Fork.type;
import static de.aice.act.Headers.CONTENT_TYPE;
import static de.aice.act.Headers.header;
import static de.aice.act.Request.request;
import static de.aice.act.Response.ok;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public final class ForkTest {

	@Test
	public void fork_on_type_should_return_empty_response_if_headers_do_not_contain_given_type() throws Exception {
		Request request = request(header(CONTENT_TYPE, "application/json"));
		Optional<Response> response = type("application/xml", r -> ok()).route(request);
		assertFalse(response.isPresent());
	}

	@Test
	public void fork_on_type_should_call_act_if_headers_contain_given_type() throws Exception {
		Request request = request(header(CONTENT_TYPE, "application/json"));
		Optional<Response> response = type("application/json", r -> ok()).route(request);
		assertTrue(response.isPresent());
	}

	@Test
	public void fork_on_method_should_return_empty_response_if_method_does_not_match() throws Exception {
		Request request = request("POST");
		Optional<Response> response = method("GET", r -> ok()).route(request);
		assertFalse(response.isPresent());
	}

	@Test
	public void fork_on_method_should_call_act_if_method_matches() throws Exception {
		Request request = request("GET");
		Optional<Response> response = method("GET", r -> ok()).route(request);
		assertTrue(response.isPresent());
	}

	@Test
	public void fork_on_method_should_call_act_if_any_method_matches() throws Exception {
		Request request = request("UPDATE");
		Optional<Response> response = method("GET,POST,UPDATE", r -> ok(r.method)).route(request);
		assertTrue(response.isPresent());
		assertEquals(request.method, response.get().body);
	}

	@Test
	public void fork_on_path_should_return_empty_response_if_path_does_not_match_regex() throws Exception {
		Request request = request("GET", "/not-matching");
		Optional<Response> response = path("/matching-path", r -> ok()).route(request);
		assertFalse(response.isPresent());
	}

	@Test
	public void fork_on_path_should_call_act_when_path_matches() throws Exception {
		Request request = request("POST", "/without-matcher");
		Optional<Response> response = path(request.path, r -> ok()).route(request);
		assertTrue(response.isPresent());
	}

	@Test
	public void fork_on_path_should_call_act_with_matcher_if_path_matches() throws Exception {
		Request request = request("GET", "/matching");
		Optional<Response> response = path(request.path, (r, m) -> ok(m.matches())).route(request);
		assertTrue(response.isPresent());
		assertTrue(Boolean.valueOf(response.get().body));
	}

	@Test
	public void select_should_return_not_found_if_no_fork_matched_the_request() throws Exception {
		Request request = request("POST");
		Response response = select(
			type("application/json", r -> ok()), type("application/xml", r -> ok())
		).on(request);
		assertEquals(404, response.status.code);
	}

	@Test
	public void select_should_call_act_if_one_of_the_forks_matches() throws Exception {
		Request request = request("POST");
		Response response = select(
			method("GET", r -> ok()), method(request.method, Act.logged(r -> ok(request.method)))
		).on(request);
		assertEquals(200, response.status.code);
		assertEquals(request.method, response.body);
	}
}