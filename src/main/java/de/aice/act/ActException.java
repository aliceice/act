package de.aice.act;

/**
 * Act specific exception.
 *
 * @author Eléna Ihde-Simon (elena.ihde-simon@posteo.de)
 * @version $Id$
 */
public final class ActException extends RuntimeException {

	public ActException() {
		super();
	}

	public ActException(final String message) {
		super(message);
	}

	public ActException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public ActException(final Throwable cause) {
		super(cause);
	}

}