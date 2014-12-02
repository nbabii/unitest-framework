package mobiletests.demotest;

import mobiletests.nativepages.ColorNoteHomePage;
import mobiletests.testbase.MobileTestBase;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import framework.automation.driver.mobile.MobileDriverFactory;
import framework.automation.driver.mobile.MobilePlatform;
import framework.automation.page.CriteriaPageFactory;
import framework.utils.log.FrameworkLogger;
import framework.utils.log.LogFactory;

public class MobileNativeDemoTest extends MobileTestBase {

	private static final FrameworkLogger LOG = LogFactory
			.getLogger(MobileNativeDemoTest.class);

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
	public void mobileNativeDemoTest1() throws InterruptedException {
		ColorNoteHomePage colorNoteHomePage = CriteriaPageFactory
				.criteriaInitNativeMobileElements(mobileDriver,
						ColorNoteHomePage.class);
		colorNoteHomePage = colorNoteHomePage.openAddNote().addNewNoteWithText(
				"This is new note!");
		Assert.assertEquals(colorNoteHomePage.getFirstNoteText(), "This is new note!");
	}
}
