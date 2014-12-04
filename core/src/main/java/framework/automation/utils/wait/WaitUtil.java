package framework.automation.utils.wait;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.support.ui.FluentWait;

import com.google.common.base.Function;
import com.google.common.base.Predicate;

import framework.utils.log.FrameworkLogger;
import framework.utils.log.LogFactory;

/**
 * Utility class to wait any condition to appear
 * @author Taras.Lytvyn
 *
 * @param <T> Type of condition we are expecting
 */
public class WaitUtil<T> {

	private static final FrameworkLogger LOG = LogFactory
			.getLogger(WaitUtil.class);

	private T condition;
	private FluentWait<T> wait;
	private final int DEFAULT_TIME = 60; // in seconds
	private int timeout;

	public WaitUtil(T condition) {
		this.condition = condition;
		this.timeout = DEFAULT_TIME;
		this.wait = getDefaultFluentWait();
		LOG.info("Initialized Wait Utility");
	}

	/**
	 * creates new instance
	 * @return new instance of Fluent Wait
	 */
	private FluentWait<T> getDefaultFluentWait() {
		return new FluentWait<T>(condition)
				.withTimeout(timeout, TimeUnit.SECONDS)
				.pollingEvery(1, TimeUnit.SECONDS)
				.ignoring(InterruptedException.class);
	}

	/**
	 * Wait for condition with Guava Predicate
	 * @param predicate
	 */
	public void forCondition(Predicate<T> predicate) {
		wait.until(predicate);
	}

	/**
	 * Wait for condition with Guava Fucntion
	 * @param predicate
	 */
	public <V> V forCondition(Function<T, V> function) {
		return wait.until(function);
	}

}
