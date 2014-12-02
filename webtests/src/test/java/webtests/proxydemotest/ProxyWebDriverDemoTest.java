package webtests.proxydemotest;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import webtests.pages.EleksHomePage;
import webtests.pages.EleksTestingQAPage;
import webtests.testbase.WebTestBase;
import framework.automation.driver.web.WebBrowser;
import framework.automation.driver.web.WebDriverFactory;
import framework.automation.page.web.WebPage;
import framework.utils.log.FrameworkLogger;
import framework.utils.log.LogFactory;

public class ProxyWebDriverDemoTest extends WebTestBase {

	private static final FrameworkLogger LOG = LogFactory
			.getLogger(ProxyWebDriverDemoTest.class);

	protected String url = "http://www.eleks.com/";
	protected WebDriver webDriver;
	protected EleksHomePage eleksPage;

	@Parameters({ "browserName", "proxyEnabled" })
	@BeforeMethod(dependsOnMethods = { "setupTestBaseMethod" })
	public void setup(String browserName, String proxyEnabled) throws Exception {
		LOG.info("Before Test WebDriver Demo precondtion");

		WebBrowser browser = WebBrowser.getInstance(browserName, url, 
				proxyEnabled);
		webDriver = WebDriverFactory.getWebDriver(browser);

		WebPage<EleksHomePage> page = new EleksHomePage(webDriver);
		eleksPage = page.navigateToPageUrl(url, EleksHomePage.class);

	}

	@AfterMethod
	// (dependsOnMethods = { "tearDownTestBaseMethod" })
	public void tearDown() {
		LOG.info("After Test WebDriver Demo killing driver");
		if (webDriver != null) {
			WebDriverFactory.killDriverInstance();
		}
	}

	@Test
	public void demoTest1() {
		EleksTestingQAPage qaPage = eleksPage.openTestingQA();

		Assert.assertTrue(qaPage.isBookBannerDisplayed(),
				"Book banner is not displayed");

	}

}
