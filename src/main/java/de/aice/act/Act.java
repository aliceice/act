package de.aice.act;

import com.jcabi.log.Logger;

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
	 * @throws ActException if something goes wrong.
	 */
	Response on(Request request) throws ActException;

	/**
	 * Log calls to given act.
	 * @param act Act to wrap
	 * @return Act with logging
	 */
	static Act logged(Act act) {
		return request -> {
			final Response response = act.on(request);
			Logger.info(Act.class, "%s %s -> %s", request.method, request.path, response.status);
			return response;
		};
	}
}
