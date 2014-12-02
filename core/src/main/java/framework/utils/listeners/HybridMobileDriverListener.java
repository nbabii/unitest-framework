package framework.utils.listeners;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.WebDriverEventListener;

import ru.yandex.qatools.allure.annotations.Step;
import framework.utils.log.FrameworkLogger;
import framework.utils.log.LogFactory;

public class HybridMobileDriverListener implements WebDriverEventListener {

	private static final FrameworkLogger LOG = LogFactory
			.getLogger(HybridMobileDriverListener.class);

	public HybridMobileDriverListener() {
		LOG.info("Initializing MobileDriver listener");
		LOG.info("Initialized MobileDriver listener");
	}

	public void beforeNavigateTo(String url, WebDriver driver) {
		
	}

	public void afterNavigateTo(String url, WebDriver driver) {

	}

	public void beforeNavigateBack(WebDriver driver) {
		// TODO Auto-generated method stub
	}

	public void afterNavigateBack(WebDriver driver) {

	}

	public void beforeNavigateForward(WebDriver driver) {

	}

	public void afterNavigateForward(WebDriver driver) {

	}

	public void beforeFindBy(By by, WebElement element, WebDriver driver) {

	}

	public void afterFindBy(By by, WebElement element, WebDriver driver) {

	}

	@Step(value = "Clicking on web element")
	public void beforeClickOn(WebElement element, WebDriver driver) {
		AutomationDriverListenerUtil.getElementDescription(
				AutomationDriverListenerUtil.getElementString(element), driver,
				element);
	}

	@Step(value = "After web element was clicked")
	public void afterClickOn(WebElement element, WebDriver driver) {
		AutomationDriverListenerUtil
				.takeWebBrowserScreenshotImageAfterStep(driver);
	}

	@Step(value = "Changing value of textbox")
	public void beforeChangeValueOf(WebElement element, WebDriver driver) {
		AutomationDriverListenerUtil.getElementDescription(
				AutomationDriverListenerUtil.getElementString(element), driver,
				element);
	}

	@Step(value = "Changed value of textbox")
	public void afterChangeValueOf(WebElement element, WebDriver driver) {
		AutomationDriverListenerUtil
				.takeWebBrowserScreenshotImageAfterStep(driver);
	}

	@Step(value = "Executing script")
	public void beforeScript(String script, WebDriver driver) {
		AutomationDriverListenerUtil.getScriptExecuted(script);
	}

	@Step(value = "After script execution")
	public void afterScript(String script, WebDriver driver) {
		AutomationDriverListenerUtil
				.takeWebBrowserScreenshotImageAfterStep(driver);
	}

	@Step(value = "Some WebDriver exception appeared")
	public void onException(Throwable throwable, WebDriver driver) {
		AutomationDriverListenerUtil
				.takeWebBrowserScreenshotImageAfterStep(driver);
		AutomationDriverListenerUtil.getExceptionDescription(throwable);
	}

}
