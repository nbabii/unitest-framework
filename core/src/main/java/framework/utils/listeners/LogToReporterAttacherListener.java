package framework.utils.listeners;

import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import framework.utils.log.FrameworkLogger;
import framework.utils.log.LogFactory;

public class LogToReporterAttacherListener extends TestListenerAdapter {

	private static final FrameworkLogger LOG = LogFactory
			.getLogger(LogToReporterAttacherListener.class);

	public LogToReporterAttacherListener() {
		LOG.info("Initialized Pre & Post Conditions Listener.");
		LOG.info("All test logs will be attached to test report.");
	}

	@Override
	public void onTestSuccess(ITestResult tr) {
		TestListenerUtil.attachLogToReporter(
				System.getProperty("logTestFolder"),
				TestListenerUtil.getFullTestName(tr));
	}

	@Override
	public void onTestFailure(ITestResult tr) {
		TestListenerUtil.attachLogToReporter(
				System.getProperty("logTestFolder"),
				TestListenerUtil.getFullTestName(tr));
	}

	@Override
	public void onTestSkipped(ITestResult tr) {
		TestListenerUtil.attachLogToReporter(
				System.getProperty("logTestFolder"),
				TestListenerUtil.getFullTestName(tr));
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult tr) {
		TestListenerUtil.attachLogToReporter(
				System.getProperty("logTestFolder"),
				TestListenerUtil.getFullTestName(tr));
	}

}
