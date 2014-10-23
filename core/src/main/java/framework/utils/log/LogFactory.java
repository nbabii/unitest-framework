package framework.utils.log;

import java.io.InputStream;
import java.util.TimeZone;

import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;
import ch.qos.logback.core.util.StatusPrinter;
import framework.utils.parsers.FileReader;

/**
 * Wraps around the SLF4J factory in order to ensure our chosen log
 * configuration settings are applied to all created Loggers.
 * <p>
 * By default, the logging subsystem will be configured by searching the
 * classpath: first for a file called <i>logback-test.xml</i> and then, if not
 * found, for <i>logback.xml</i>.
 * <p>
 * If you wish, you can override this behaviour for testing purposes by
 * specifying the location of the default configuration file with the system
 * property <code>
 * logback.configurationFile</code> . The value of the this property can be a
 * URL, a resource on the class path or a path to a file external to the
 * application e.g.
 * <code>-Dlogback.configurationFile=/path/to/custom/logback.xml</code>
 */
public class LogFactory {
	private static LoggerContext factory = (LoggerContext) LoggerFactory
			.getILoggerFactory();
	private static FrameworkLogger logger;

	static {
		try {
			init(FileReader.class.getResourceAsStream("/logback.xml"));
		} catch (IllegalArgumentException e) {
			System.err
					.println("failed to load logging configuration from logback.xml");
			e.printStackTrace(System.err);
			throw new RuntimeException(
					"no file logback in resources folder found", e);
		}
	}

	/**
	 * Causes the logging subsystem to be (re-)initialized according to the
	 * passed properties.
	 * 
	 * @param logParams
	 *            - an InputStream containing a logback.xml config file
	 */
	public static void init(InputStream logParams) {
		try {
			JoranConfigurator configurator = new JoranConfigurator();
			configurator.setContext(factory);
			if (logParams != null) {
				factory.reset();
				configurator.doConfigure(logParams);
			}
		} catch (JoranException je) { // catch-22 situation here: can't log an
										// error if the logger can't be
										// created! Possibly the only valid use
										// case for printStackTrace()...
			je.printStackTrace();
		}
		StatusPrinter.printIfErrorsOccured(factory);
		logger = getLogger(LogFactory.class);
		logger.info("-------------------------Initializing logger-------------------------");
		logger.info("Logging (re-)initalized");
		logger.info("JVM timezone: {}", TimeZone.getDefault().getID());
	}

	/**
	 * @param clazz
	 * @return the associated Logger object
	 */
	public static FrameworkLogger getLogger(
			@SuppressWarnings("rawtypes") Class clazz) {
		return new FrameworkLoggerImpl(factory.getLogger(clazz));
	}

	/**
	 * @param name
	 * @return the associated Logger object
	 */
	public static FrameworkLogger getLogger(String name) {
		return new FrameworkLoggerImpl(factory.getLogger(name));
	}

	/**
	 * Gets the 'actual' factory
	 *
	 * @return
	 */
	public static LoggerContext getRawFactory() {
		return factory;
	}
}
