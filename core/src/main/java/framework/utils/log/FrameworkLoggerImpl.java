package framework.utils.log;

import org.slf4j.Logger;
import org.slf4j.helpers.MessageFormatter;

/**
 * A simple wrapper class which delegates the majority of it's work to a wrapped
 * SLF4J logger.
 */
public final class FrameworkLoggerImpl implements FrameworkLogger {
	public static final String STARTING_TIMER = "Starting timer: ";
	public static final String STOPPING_TIMER = "Stopped timer: ";

	private final Logger delegatee;

	FrameworkLoggerImpl(Logger delegatee) {
		this.delegatee = delegatee;
	}

	@Override
	public final boolean isDebugEnabled() {
		return delegatee.isDebugEnabled();
	}

	@Override
	public final boolean isInfoEnabled() {
		return delegatee.isInfoEnabled();
	}

	@Override
	public final boolean isTraceEnabled() {
		return delegatee.isTraceEnabled();
	}

	@Override
	public final void debug(String arg0, Throwable arg1) {
		delegatee.debug(arg0, arg1);
	}

	@Override
	public final void debug(String arg0, Object... arg1) {
		delegatee.debug(arg0, arg1);
	}

	@Override
	public Timestamp timerStart(String arg0) {
		if (isDebugEnabled()) {
			debug(STARTING_TIMER + arg0);
			return new Timestamp(arg0);
		} else {
			return null;
		}
	}

	@Override
	public Timestamp timerStart(String arg0, Throwable arg1) {
		if (isDebugEnabled()) {
			debug(STARTING_TIMER + arg0, arg1);
			return new Timestamp(arg0);
		} else {
			return null;
		}
	}

	@Override
	public Timestamp timerStart(String arg0, Object... arg1) {
		if (isDebugEnabled()) {
			String message = MessageFormatter.format(arg0, arg1).getMessage();
			debug(STARTING_TIMER + message);
			return new Timestamp(message);
		} else {
			return null;
		}
	}

	@Override
	public void timerStop(Timestamp timestamp) {
		if (isDebugEnabled()) {
			if (timestamp != null) {
				long now = System.currentTimeMillis();
				String message = STOPPING_TIMER + timestamp.message
						+ ". Elapsed time: " + (now - timestamp.timestamp)
						+ "ms. ";
				debug(message);
			} else {
				debug("Timestamp is null!");
			}
		}
	}

	@Override
	public void timerStop(Timestamp timestamp, Throwable arg0) {
		if (isDebugEnabled()) {
			if (timestamp != null) {
				long now = System.currentTimeMillis();
				String message = STOPPING_TIMER + timestamp.message
						+ " due to exception. Elapsed time: "
						+ (now - timestamp.timestamp) + "ms.: " + arg0
						+ " Exception message is [" + arg0.getMessage() + "]";
				debug(message);
			} else {
				debug("Timestamp is null!");
			}
		}
	}

	@Override
	public final void error(String arg0, Object... arg1) {
		delegatee.error(arg0, arg1);
	}

	@Override
	public final void info(String arg0, Object... arg1) {
		delegatee.info(arg0, arg1);
	}

	@Override
	public final void trace(String arg0, Object... arg1) {
		delegatee.trace(arg0, arg1);
	}

	@Override
	public final void warn(String arg0, Object... arg1) {
		delegatee.warn(arg0, arg1);
	}

	@Override
	public final void debug(String arg0) {
		delegatee.debug(arg0);
	}

	@Override
	public final void error(String arg0, Throwable arg1) {
		delegatee.error(arg0, arg1);
	}

	@Override
	public final void error(String arg0) {
		delegatee.error(arg0);
	}

	@Override
	public final String getName() {
		return delegatee.getName();
	}

	@Override
	public final void info(String arg0, Throwable arg1) {
		delegatee.info(arg0, arg1);
	}

	@Override
	public final void info(String arg0) {
		delegatee.info(arg0);
	}

	@Override
	public final void trace(String arg0, Throwable arg1) {
		delegatee.trace(arg0, arg1);
	}

	@Override
	public final void trace(String arg0) {
		delegatee.trace(arg0);
	}

	@Override
	public final void warn(String arg0, Throwable arg1) {
		delegatee.warn(arg0, arg1);
	}

	@Override
	public final void warn(String arg0) {
		delegatee.warn(arg0);
	}
}
