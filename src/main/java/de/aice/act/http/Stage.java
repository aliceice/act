package de.aice.act.http;

import de.aice.act.Act;
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
	 * @return basic Stage.
	 * @throws IOException if something goes wrong.
	 */
	static Stage basic(final Act act) throws IOException {
		return new StageBasic(BackStage.basic(act));
	}

	static Stage safe(final Act act) {
		return new StageBasic(BackStage.safe(act));
	}

}
