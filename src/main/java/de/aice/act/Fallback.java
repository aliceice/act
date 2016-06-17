package de.aice.act;

import com.jcabi.log.Logger;
import de.aice.act.misc.Strings;
import java.net.HttpURLConnection;
import java.util.Optional;

/**
 * Fallback on Act exceptions.
 *
 * @author Eléna Ihde-Simon (elena.ihde-simon@posteo.de)
 * @version $Id$
 */
public interface Fallback {

	/**
	 * Fallback on request and exception.
	 *
	 * @param request   failed request
	 * @param exception thrown exception
	 * @return Response if handled, or else header Optional.
	 */
	Optional<Response> on(Request request, Exception exception);

	/**
	 * Fallback chain.
	 *
	 * @param act   Act to wrap
	 * @param chain chain request fallbackChain
	 * @return Act with fallbackChain chain
	 */
	@SuppressWarnings({"checkstyle:returncount", "checkstyle:javancss"})
	static Act fallbackChain(Act act, Fallback... chain) {
		return request -> {
			try {
				return act.on(request);
			} catch (Exception e) {
				for (Fallback f : chain) {
					Optional<Response> response = f.on(request, e);
					if (response.isPresent())
						return response.get();
				}
				Logger.error(Fallback.class, "%s %s -> %s: %[exception]s", request.method, request.path,
				             HttpURLConnection.HTTP_INTERNAL_ERROR, e);
				return Response.status(Status.INTERNAL_SERVER_ERROR)
				               .body(Strings.defaultString(e.getMessage()));
			}
		};
	}
}
