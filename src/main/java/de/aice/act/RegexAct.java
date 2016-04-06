package de.aice.act;

import java.io.IOException;
import java.util.regex.Matcher;

/**
 * XXX
 *
 * @author Eléna Ihde-Simon (elena.ihde-simon@posteo.de)
 * @version $Id$
 */
public interface RegexAct {

	Response on(Request request, Matcher matcher) throws IOException;

}
