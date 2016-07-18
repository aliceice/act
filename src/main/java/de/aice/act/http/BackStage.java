package de.aice.act.http;

import de.aice.act.Act;
import java.net.Socket;

import static de.aice.act.Fallback.fallbackChain;

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
	 * @throws Exception if something goes wrong.
	 */
	void accept(Socket socket) throws Exception;

	/**
	 * Basic BackStage without error handling.
	 *
	 * @param act Act to call.
	 * @return BackStage.
	 */
	static BackStage basic(final Act act) {
		return new BackStageBasic(act);
	}

	/**
	 * Safe backend which just logs exceptions.
	 *
	 * @param act Act to call
	 * @return Safe BackStage
	 */
	static BackStage safe(final Act act) {
		return basic(fallbackChain(act));
	}

}
