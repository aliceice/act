package de.aice.act;

import java.util.regex.Matcher;

/**
 * Act used in {@link Fork#path(String, RegexAct)}.
 * It additionally provides a Matcher for the path regex.
 *
 * @author Eléna Ihde-Simon (elena.ihde-simon@posteo.de)
 * @version $Id$
 */
public interface RegexAct {

	/**
	 * Act on request to create a response.
	 *
	 * @param request HTTP request
	 * @param matcher Path regex matcher
	 * @return HTTP Response
	 * @throws ActException if something goes wrong.
	 */
	Response on(Request request, Matcher matcher) throws ActException;

}
