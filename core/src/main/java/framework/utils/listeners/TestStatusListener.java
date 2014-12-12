package framework.utils.listeners;

import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.internal.TestResult;

import framework.utils.log.FrameworkLogger;
import framework.utils.log.LogFactory;

/**
 * Listens for TestNG's test and configuration methods' execution status and
 * logs it.
 *
 */
public class TestStatusListener implements IInvokedMethodListener {
	private static final FrameworkLogger LOG = LogFactory
			.getLogger(TestStatusListener.class);

	public void beforeInvocation(IInvokedMethod method, ITestResult result) {
		if (method.isTestMethod()) {
			synchronized (TestStatusListener.class) {
				LOG.info("::::::::::::::::::::::::::::::::::::::::::::");
				LOG.info("Begin executing Test Method: "
						+ TestListenerUtil.getTestName(result));
				LOG.info("::::::::::::::::::::::::::::::::::::::::::::");
			}
		}
	}

	public void afterInvocation(IInvokedMethod method, ITestResult result) {
		boolean isTestMethod = method.isTestMethod();

		if (result.getThrowable() != null) {
			if (result.getStatus() != TestResult.SUCCESS) {
				// there is an exception and it's not an expected exception
				// (test did not pass)
				// so print out the exception
				LOG.error("Encountered exception", result.getThrowable());
			} else {
				LOG.info("Encountered expected exception");
			}
		}

		if (isTestMethod) {
			String status = "INVALID";
			// only if we have a test method, we should print out the status
			synchronized (TestStatusListener.class) {
				switch (result.getStatus()) {
				case TestResult.SUCCESS:
					status = "PASS";
					break;
				case TestResult.FAILURE:
				case TestResult.SUCCESS_PERCENTAGE_FAILURE:
					status = "FAIL";
					break;
				case TestResult.SKIP:
					status = "SKIP";
				}
				LOG.info("::::::::::::::::::::::::::::::::::::::::::::");
				LOG.info("Finished executing the Test Method: "
						+ TestListenerUtil.getTestName(result));
				LOG.info("Test: {} - status: {}",
						new Object[] { TestListenerUtil.getTestName(result), status });
				LOG.info("::::::::::::::::::::::::::::::::::::::::::::");
			}
		}
	}
}
