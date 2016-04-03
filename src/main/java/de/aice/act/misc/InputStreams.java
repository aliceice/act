package de.aice.act.misc;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import static de.aice.act.misc.Structures.tryWith;

/**
 * InputStream utilities.
 *
 * @author Eléna Ihde-Simon (elena.ihde-simon@posteo.de)
 * @version $Id$
 */
@SuppressWarnings("checkstyle:javancss")
public final class InputStreams {

	private InputStreams() {

	}

	/**
	 * Read line from input stream. Uses CR_LF as line end.
	 *
	 * @param stream stream to read from.
	 * @return line.
	 * @throws IOException if something goes wrong.
	 */
	public static String readLine(final InputStream stream) throws IOException {
		return tryWith(ByteArrayOutputStream::new, baos -> {
			while (true) {
				int data = stream.read();
				if (data == '\r') {
					if (stream.read() != '\n')
						throw new IllegalArgumentException("No LF after CR");
					break;
				}
				baos.write(data);
			}
			return new String(baos.toByteArray(), Charset.defaultCharset());
		});
	}

	/**
	 * Read from stream until stopSignal is reached.
	 *
	 * @param stopSignal stop signal to look for.
	 * @param stream     stream to use.
	 * @return lines till stop signal.
	 * @throws IOException if something goes wrong.
	 */
	public static List<String> readUntil(final String stopSignal, final InputStream stream) throws IOException {
		List<String> lines = new ArrayList<>();
		while (true) {
			String line = readLine(stream);
			if (line.equals(stopSignal))
				break;
			lines.add(line);
		}
		return lines;
	}

	/**
	 * Read exact number of bytes from stream.
	 *
	 * @param bytes       number of bytes to read.
	 * @param inputStream stream to use.
	 * @return string.
	 * @throws IOException if something goes wrong.
	 */
	public static String readExactly(final long bytes, final InputStream inputStream) throws IOException {
		return tryWith(ByteArrayOutputStream::new, baos -> {
			long length = bytes;
			while (length-- > 0) {
				baos.write(inputStream.read());
			}

			return new String(baos.toByteArray(), Charset.defaultCharset());
		});
	}

	/**
	 * Create InputStream from given input.
	 *
	 * @param input input to use.
	 * @return InputStream.
	 */
	public static InputStream stream(final String input) {
		return new ByteArrayInputStream(input.getBytes(Charset.defaultCharset()));
	}
}
