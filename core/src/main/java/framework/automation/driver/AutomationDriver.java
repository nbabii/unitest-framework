package framework.automation.driver;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import framework.utils.configurations.FrameworkComponentsClientFactory;
import framework.utils.configurations.FrameworkPropertiesUtil;

/**
 * Abstract class for Automation Driver, parent for all type of drivers
 * 
 * @author Taras.Lytvyn
 */
public abstract class AutomationDriver {

	protected static FrameworkPropertiesUtil propertiesUtil;
	protected static EventFiringWebDriver eventDriver;

	static {
		propertiesUtil = FrameworkComponentsClientFactory.getPropertiesUtil();
	}

	/**
	 * Set the implicit timeout for web driver test execution
	 * 
	 * @param driver
	 *            selenium webdriver
	 */
	protected static void setImplicitTimeOut(WebDriver driver) {
		int timeout = Integer.valueOf(propertiesUtil
				.getProperty("implicit-timeout"));
		driver.manage().timeouts().implicitlyWait(timeout, TimeUnit.SECONDS);
	}

}
