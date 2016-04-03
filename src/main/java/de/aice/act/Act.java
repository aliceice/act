package de.aice.act;

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
}
