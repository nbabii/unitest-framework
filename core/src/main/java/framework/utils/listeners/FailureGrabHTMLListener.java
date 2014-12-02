package framework.utils.listeners;

import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import framework.automation.driver.web.WebDriverFactory;
import framework.utils.log.FrameworkLogger;
import framework.utils.log.LogFactory;

/**
 * TestNG listener that grabs HTML content of a page on test failures
 *
 */
public class FailureGrabHTMLListener extends TestListenerAdapter {
	private static final FrameworkLogger LOG = LogFactory
			.getLogger(FailureGrabHTMLListener.class);

	public FailureGrabHTMLListener() {
		LOG.info("Initialized Grab HTML listener.");
	}

	@Override
	public void onTestFailure(ITestResult result) {
		try {
			WebDriver driver = WebDriverFactory.getCreatedWebDriver();
			LOG.error("Page HTML content because OF TEST FAILURE for test {}",
					TestListenerUtil.getFullTestName(result));
			LOG.error(
					"--------------------------------html grab source start block--------------------------------\n{}",
					driver.getPageSource());
			LOG.error("-------------------------------html grab source end block---------------------------------\n");
		} catch (Exception ex) {
			LOG.error("Failed to grab HTML source.", ex);
		}
	}

}