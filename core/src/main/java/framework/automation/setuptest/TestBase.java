package framework.automation.setuptest;

import java.lang.reflect.Method;

import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import framework.utils.log.FrameworkLogger;
import framework.utils.log.LogFactory;
import framework.utils.log.TestLogHelper;

public class TestBase {

	private static final FrameworkLogger LOG = LogFactory
			.getLogger(TestBase.class);

	@BeforeClass
	public void setupTestBase(ITestContext context) throws Exception {
		System.setProperty("logTestFolder", getTestClassName());
		TestLogHelper.startTestLogging(getTestClassName()
				+ " suite pre condition");
	}

	@AfterClass
	public void tearDownTestBase(ITestContext context) throws Exception {
		System.setProperty("logTestFolder", getTestClassName());
		TestLogHelper.startTestLogging(getTestClassName()
				+ " suite post condition");
	}

	@BeforeMethod
	public void setupTestBaseMethod(Method method) throws Exception {
		System.setProperty("currentTestName", method.getName());
		TestLogHelper.startTestLogging(method.getName());		
		LOG.info("Precondition SETUP for test method: " + method.getName());
	}

	@AfterMethod
	public void tearDownTestBaseMethod(Method method) {
		LOG.info("Post Condition TEAR DOWN for method: " + method.getName());
		TestLogHelper.stopTestLoggingSeparateMethod();
		//TestListenerUtil.attachLogToReporter(System.getProperty("logTestFolder"), method.getName());
	}

	private String getTestClassName() {
		return this.getClass().getSimpleName();
	}

}
