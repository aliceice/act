package de.aice.act.http;

import com.jcabi.log.Logger;
import de.aice.act.misc.UnsafeConsumer;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.SocketTimeoutException;
import java.time.Duration;

import static de.aice.act.misc.Structures.tryWith;

/**
 * Basic stage. Starts server on port 8080.
 *
 * @author Eléna Ihde-Simon (elena.ihde-simon@posteo.de)
 * @version $Id$
 */
final class StageBasic implements Stage {

	private static final int PORT = 8080;

	private final BackStage backStage;

	StageBasic(final BackStage backStage) {
		this.backStage = backStage;
	}

	@Override
	public void start(final Exit exit) throws Exception {
		tryWith(StageBasic::socket, acceptUntil(exit));
	}

	private static ServerSocket socket() throws IOException {
		ServerSocket socket = new ServerSocket(PORT);
		socket.setSoTimeout((int) Duration.ofSeconds(1).toMillis());
		return socket;
	}

	private UnsafeConsumer<ServerSocket> acceptUntil(final Exit exit) throws Exception {
		return socket -> {
			Logger.info(Stage.class, "Basic stage started!");
			do {
				accept(socket);
			} while (!exit.ready());
		};
	}

	private void accept(final ServerSocket socket) throws Exception {
		try {
			this.backStage.accept(socket.accept());
		} catch (final SocketTimeoutException ex) {
			assert ex != null;
		}
	}
}
