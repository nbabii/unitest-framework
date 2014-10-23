package framework.utils.log;

public interface FrameworkLogger {
	boolean isDebugEnabled();

	boolean isInfoEnabled();

	boolean isTraceEnabled();

	void debug(String arg0, Throwable arg1);

	void debug(String arg0, Object... args);

	/**
	 * Starts a timer with the specified message. A call to timerStop() will
	 * stop the timer, and log the original message along with the elapsed time.
	 */
	Timestamp timerStart(String arg0);

	Timestamp timerStart(String arg0, Throwable arg1);

	Timestamp timerStart(String arg0, Object... arg1);

	void timerStop(Timestamp timestamp);

	void timerStop(Timestamp timestamp, Throwable arg1);

	void error(String arg0, Object... arg1);

	void info(String arg0, Object... arg1);

	void trace(String arg0, Object... arg1);

	void warn(String arg0, Object... arg1);

	void debug(String arg0);

	void error(String arg0, Throwable arg1);

	void error(String arg0);

	String getName();

	void info(String arg0, Throwable arg1);

	void info(String arg0);

	void trace(String arg0, Throwable arg1);

	void trace(String arg0);

	void warn(String arg0, Throwable arg1);

	void warn(String arg0);

	static class Timestamp {
		long timestamp;
		String message;

		public Timestamp(String message) {
			this.message = message;
			this.timestamp = System.currentTimeMillis();
		}
	}
}
