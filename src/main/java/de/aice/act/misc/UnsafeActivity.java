package de.aice.act.misc;

/**
 * Activity which might throw an Exception.
 *
 * @author Eléna Ihde-Simon (elena.ihde-simon@posteo.de)
 * @version $Id$
 */
public interface UnsafeActivity {

	/**
	 * Execute activity.
	 *
	 * @throws Exception if something goes wrong.
	 */
	void execute() throws Exception;

}