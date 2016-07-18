package de.aice.act.misc;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
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
public interface InputStreams {

	/**
	 * Read line from input stream. Uses CR_LF as line end.
	 *
	 * @param stream stream to read from.
	 * @return line.
	 * @throws Exception if something goes wrong.
	 */
	static String readLine(final InputStream stream) throws Exception {
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
	 * @throws Exception if something goes wrong.
	 */
	static List<String> readUntil(final String stopSignal, final InputStream stream) throws Exception {
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
	 * Read exact number request bytes from stream.
	 *
	 * @param bytes       number request bytes to read.
	 * @param inputStream stream to use.
	 * @return string.
	 * @throws Exception if something goes wrong.
	 */
	static String readExactly(final long bytes, final InputStream inputStream) throws Exception {
		return tryWith(ByteArrayOutputStream::new, baos -> {
			long length = bytes;
			while (length > 0) {
				baos.write(inputStream.read());
				length--;
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
	static InputStream stream(final String input) {
		return new ByteArrayInputStream(input.getBytes(Charset.defaultCharset()));
	}

}
