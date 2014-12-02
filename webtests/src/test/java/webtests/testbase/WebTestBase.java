package webtests.testbase;

import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import framework.automation.setuptest.TestBase;
import framework.utils.log.FrameworkLogger;
import framework.utils.log.LogFactory;

public class WebTestBase extends TestBase {

	private static final FrameworkLogger LOG = LogFactory
			.getLogger(WebTestBase.class);

	@BeforeClass(dependsOnMethods = { "setupTestBase" })
	public void setupWebTests(ITestContext context) {
		LOG.info("Before Suite");

	}

	@AfterClass(dependsOnMethods = { "tearDownTestBase" })
	public void tearDownWebTests(ITestContext context) {
		LOG.info("After Suite");
	}

}
