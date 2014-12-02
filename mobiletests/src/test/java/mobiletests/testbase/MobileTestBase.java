package mobiletests.testbase;

import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import framework.automation.setuptest.TestBase;
import framework.utils.log.FrameworkLogger;
import framework.utils.log.LogFactory;

public class MobileTestBase extends TestBase {

	private static final FrameworkLogger LOG = LogFactory
			.getLogger(MobileTestBase.class);

	@BeforeClass(dependsOnMethods = { "setupTestBase" })
	public void setupMobileTests(ITestContext context) {
		LOG.info("Before Suite");

	}

	@AfterClass(dependsOnMethods = { "tearDownTestBase" })
	public void tearDownMobileTests(ITestContext context) {
		LOG.info("After Suite");
	}

}
