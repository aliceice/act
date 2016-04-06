package de.aice.act;

import com.jcabi.log.Logger;
import java.net.HttpURLConnection;
import java.util.Optional;

/**
 * XXX
 *
 * @author Eléna Ihde-Simon (elena.ihde-simon@posteo.de)
 * @version $Id$
 */
public interface Fallback {

	Optional<Response> on(Request request, Exception exception);

	static Act fallback(Act act, Fallback... chain) {
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
				return Response.status(HttpURLConnection.HTTP_INTERNAL_ERROR)
				               .body(e.getMessage());
			}
		};
	}
}
