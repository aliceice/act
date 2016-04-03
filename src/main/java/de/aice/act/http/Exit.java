package de.aice.act.http;

/**
 * Exit condition.
 *
 * @author Eléna Ihde-Simon (elena.ihde-simon@posteo.de)
 * @version $Id$
 */
public interface Exit {

	/**
	 * Stage ready to exit?
	 *
	 * @return true if stage should exit.
	 */
	boolean ready();

	/**
	 * Exit returning always false.
	 *
	 * @return Exit
	 */
	static Exit never() {
		return () -> false;
	}
}
