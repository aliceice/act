package de.aice.act;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Request fork.
 *
 * @author Eléna Ihde-Simon (elena.ihde-simon@posteo.de)
 * @version $Id$
 */
public interface Fork {

	/**
	 * Route request. If request is accepted it should act on it and return a response.
	 * Otherwise an header optional should be returned.
	 *
	 * @param request HTTP request
	 * @return Optional request act result or header.
	 * @throws ActException if something goes wrong.
	 */
	Optional<Response> route(Request request) throws ActException;

	/**
	 * Select act through conditional forks.
	 *
	 * @param forks available forks
	 * @return forking act.
	 */
	@SuppressWarnings("checkstyle:javancss")
	static Act select(final Fork... forks) {
		return request -> {
			for (Fork fork : forks) {
				Optional<Response> response = fork.route(request);
				if (response.isPresent())
					return response.get();
			}
			return Response.notFound();
		};
	}

	/**
	 * Fork on path matching regex.
	 *
	 * @param regex regex to use.
	 * @param act   act to call.
	 * @return Path matching fork.
	 */
	static Fork path(final String regex, final Act act) {
		return path(regex, (request, matcher) -> act.on(request));
	}

	/**
	 * Fork on path matching regex.
	 *
	 * @param regex regex to use.
	 * @param act   act to call.
	 * @return Path matching fork.
	 */
	static Fork path(final String regex, final RegexAct act) {
		Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
		return request -> {
			Matcher matcher = pattern.matcher(request.path);
			return matcher.matches()
			       ? Optional.of(act.on(request, matcher))
			       : Optional.empty();
		};
	}

	/**
	 * Fork on method.
	 *
	 * @param methods comma separated list request methods.
	 * @param act     act to call.
	 * @return Method matching fork.
	 */
	static Fork method(final String methods, final Act act) {
		List<String> list = Arrays.asList(methods.split(","));
		return request -> list.contains(request.method)
		                  ? Optional.of(act.on(request))
		                  : Optional.empty();
	}

	/**
	 * Fork on content type.
	 *
	 * @param type content type
	 * @param act  act to call.
	 * @return content type matching fork.
	 */
	static Fork type(String type, Act act) {
		return request -> request.headers.has(Headers.CONTENT_TYPE, type)
		                  ? Optional.of(act.on(request))
		                  : Optional.empty();
	}
}
