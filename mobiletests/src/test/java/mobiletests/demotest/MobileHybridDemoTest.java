package mobiletests.demotest;

import mobiletests.hybridpages.OmobileLoginPage;
import mobiletests.testbase.MobileTestBase;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import framework.automation.driver.mobile.MobileDriverFactory;
import framework.automation.driver.mobile.MobilePlatform;
import framework.automation.page.CriteriaPageFactory;
import framework.utils.log.FrameworkLogger;
import framework.utils.log.LogFactory;

public class MobileHybridDemoTest extends MobileTestBase {

	private static final FrameworkLogger LOG = LogFactory
			.getLogger(MobileHybridDemoTest.class);

	protected WebDriver mobileDriver;

	@Parameters({ "osType", "appType", "mobileDeviceName" })
	@BeforeMethod(dependsOnMethods = { "setupTestBaseMethod" })
	public void setUp(String osType, String appType, String mobileDeviceName)
			throws Exception {
		LOG.info("Before Test MobileDriver Demo precondtion");
		MobilePlatform platform = MobilePlatform.getInstance(osType, appType,
				mobileDeviceName);
		mobileDriver = MobileDriverFactory.getAppiumDriver(platform);

	}

	@AfterMethod
	// (dependsOnMethods = { "tearDownTestBaseMethod" })
	public void tearDown() {
		LOG.info("After Test MobileDriver Demo killing driver");
		if (mobileDriver != null) {
			MobileDriverFactory.killDriverInstance();
		}
	}

	@Test
	public void mobileHybridDemoTest1() {
		OmobileLoginPage omobileLoginPage = CriteriaPageFactory
				.criteriaInitHybridMobileElements(mobileDriver, OmobileLoginPage.class);
		omobileLoginPage = omobileLoginPage.failLoginToApp().tryLoginAgain()
				.failLoginToApp().tryLoginAgain();
	}
}
