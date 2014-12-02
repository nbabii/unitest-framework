package framework.automation.utils.wait;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.support.ui.FluentWait;

import com.google.common.base.Function;
import com.google.common.base.Predicate;

import framework.utils.log.FrameworkLogger;
import framework.utils.log.LogFactory;

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

	private FluentWait<T> getDefaultFluentWait() {
		return new FluentWait<T>(condition)
				.withTimeout(timeout, TimeUnit.SECONDS)
				.pollingEvery(1, TimeUnit.SECONDS)
				.ignoring(InterruptedException.class);
	}

	public void forCondition(Predicate<T> predicate) {
		wait.until(predicate);
	}

	public <V> V forCondition(Function<T, V> function) {
		return wait.until(function);
	}

}
