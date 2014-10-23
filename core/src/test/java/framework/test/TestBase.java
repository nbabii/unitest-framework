package framework.test;

import java.lang.reflect.Method;

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
	public void setupTestBase() throws Exception {
		System.setProperty("logTestFolder", getTestClassName());
		TestLogHelper.startTestLogging(getTestClassName() + " suite pre condition");
	}

	@AfterClass
	public void tearDownTestBase() throws Exception {
		System.setProperty("logTestFolder", getTestClassName());
		TestLogHelper.startTestLogging(getTestClassName() + " suite post condition");
	}

	@BeforeMethod
	public void setupTestBaseMethod(Method method) throws Exception {
		TestLogHelper.startTestLogging(method.getName());
		LOG.info("Executing test method: " + method.getName() + " - Precondition setup");
	}

	@AfterMethod
	public void tearDownTestBaseMethod(Method method) {
		LOG.info("Finishing executing test method: " + method.getName() + " - Post Condition tear down");
		TestLogHelper.stopTestLoggingSeparateMethod();
	}

	private String getTestClassName() {
		return this.getClass().getSimpleName();
	}

}
