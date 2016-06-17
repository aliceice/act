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

	/**
	 * Exit on signal.
	 *
	 * @author Eléna Ihde-Simon (elena.ihde-simon@posteo.de)
	 * @version $Id$
	 */
	final class OnSignal implements Exit {

		private boolean ready;

		@Override
		public boolean ready() {
			return this.ready;
		}

		/**
		 * Stop signal.
		 */
		public void stop() {
			this.ready = true;
		}
	}
}
