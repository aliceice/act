package de.aice.act.http;

import de.aice.act.Act;
import java.io.IOException;
import java.net.Socket;

/**
 * Backend.
 *
 * @author Eléna Ihde-Simon (elena.ihde-simon@posteo.de)
 * @version $Id$
 */
interface BackStage {

	/**
	 * Accept incoming socket.
	 *
	 * @param socket Socket.
	 * @throws IOException if something goes wrong.
	 */
	void accept(Socket socket) throws IOException;

	/**
	 * Basic BackStage without error handling.
	 *
	 * @param act Act to call.
	 * @return BackStage.
	 */
	static BackStage basic(final Act act) {
		return new BackStageBasic(act);
	}

}
