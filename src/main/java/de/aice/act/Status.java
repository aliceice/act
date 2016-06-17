package de.aice.act;

/**
 * HTTP status.
 *
 * @author Eléna Ihde-Simon (elena.ihde-simon@posteo.de)
 * @version $Id$
 */
public enum Status {

	OK(200, "OK"),
	NOT_FOUND(404, "Not Found"),
	INTERNAL_SERVER_ERROR(500, "Internal Server Error");

	public final int    code;
	public final String reason;

	Status(final int code, final String reason) {
		this.code = code;
		this.reason = reason;
	}

	@Override
	public String toString() {
		return String.format("%d %s", this.code, this.reason);
	}
}
