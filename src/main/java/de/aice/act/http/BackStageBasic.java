package de.aice.act.http;

import de.aice.act.Act;
import de.aice.act.Request;
import de.aice.act.Response;
import java.net.Socket;

import static de.aice.act.http.BackStageSupport.readRequest;

/**
 * Basic backstage.
 *
 * @author Eléna Ihde-Simon (elena.ihde-simon@posteo.de)
 * @version $Id$
 */
final class BackStageBasic implements BackStage {

	private final Act act;

	BackStageBasic(final Act act) {
		this.act = act;
	}

	@Override
	public void accept(final Socket socket) throws Exception {
		try {
			handleRequest(socket);
		} finally {
			socket.close();
		}
	}

	private void handleRequest(final Socket socket) throws Exception {
		Request request = readRequest(socket.getInputStream());
		Response response = this.act.on(request);
		BackStageSupport.writeResponse(response, socket.getOutputStream());
	}
}
