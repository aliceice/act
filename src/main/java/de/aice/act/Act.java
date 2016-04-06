package de.aice.act;

import com.jcabi.log.Logger;
import java.io.IOException;

/**
 * Act.
 *
 * @author Eléna Ihde-Simon (elena.ihde-simon@posteo.de)
 * @version $Id$
 */
public interface Act {

	/**
	 * Act on request to create a response.
	 *
	 * @param request HTTP request
	 * @return HTTP response
	 * @throws IOException if something goes wrong.
	 */
	Response on(Request request) throws IOException;

	static Act logged(Act act) {
		return request -> {
			final Response response = act.on(request);
			Logger.info(Act.class, "%s %s -> %s", request.method, request.path, response.status);
			return response;
		};
	}
}
