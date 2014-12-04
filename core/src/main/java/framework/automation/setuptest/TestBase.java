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

/**
 * Base class for all the tests. Created in Core specially for logger and
 * reporter functionality
 * 
 * @author Taras.Lytvyn
 *
 */
public class TestBase {

	private static final FrameworkLogger LOG = LogFactory
			.getLogger(TestBase.class);

	/**
	 * starts the capturing of test class logger. So test precondition from @BeforeClass
	 * will be logged to separate file
	 * 
	 * @param context
	 * @throws Exception
	 */
	@BeforeClass
	public void setupTestBase(ITestContext context) throws Exception {
		System.setProperty("logTestFolder", getTestClassName());
		TestLogHelper.startTestLogging(getTestClassName()
				+ " suite pre condition");
	}

	/**
	 * stops the capturing of test class logger. So test post condition from @AfterClass
	 * will be logged to separate file
	 * 
	 * @param context
	 * @throws Exception
	 */
	@AfterClass
	public void tearDownTestBase(ITestContext context) throws Exception {
		System.setProperty("logTestFolder", getTestClassName());
		TestLogHelper.startTestLogging(getTestClassName()
				+ " suite post condition");
	}

	/**
	 * starts capturing test method logger. So each test method will be logged to separate file.
	 * @param method
	 * @throws Exception
	 */
	@BeforeMethod
	public void setupTestBaseMethod(Method method) throws Exception {
		System.setProperty("currentTestName", method.getName());
		TestLogHelper.startTestLogging(method.getName());
		LOG.info("Precondition SETUP for test method: " + method.getName());
	}

	/**
	 * stops capturing test method logger.
	 * @param method
	 * @throws Exception
	 */
	@AfterMethod
	public void tearDownTestBaseMethod(Method method) {
		LOG.info("Post Condition TEAR DOWN for method: " + method.getName());
		TestLogHelper.stopTestLoggingSeparateMethod();
		// TestListenerUtil.attachLogToReporter(System.getProperty("logTestFolder"),
		// method.getName());
	}

	/**
	 * gets the name of Test Class
	 * 
	 * @return
	 */
	private String getTestClassName() {
		return this.getClass().getSimpleName();
	}

}
