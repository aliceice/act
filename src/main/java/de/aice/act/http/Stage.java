package de.aice.act.http;

import de.aice.act.Act;
import de.aice.act.ActException;
import java.io.IOException;

/**
 * Stage.
 *
 * @author Eléna Ihde-Simon (elena.ihde-simon@posteo.de)
 * @version $Id$
 */
public interface Stage {

	/**
	 * Start until exit.
	 *
	 * @param exit exit.
	 * @throws IOException if something goes wrong.
	 */
	void start(Exit exit) throws IOException;

	/**
	 * Basic stage without exception handling.
	 *
	 * @param act Act to call.
	 * @return basicStage Stage.
	 * @throws ActException if something goes wrong.
	 */
	static Stage basicStage(final Act act) throws ActException {
		return new StageBasic(BackStage.basic(act));
	}

	/**
	 * Stage with exception handling.
	 *
	 * @param act Act to call.
	 * @return Safe Stage.
	 */
	static Stage safeStage(final Act act) {
		return new StageBasic(BackStage.safe(act));
	}

}
