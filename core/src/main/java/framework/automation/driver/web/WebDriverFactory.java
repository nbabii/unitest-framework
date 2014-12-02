package framework.automation.driver.web;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import framework.automation.driver.AutomationDriver;
import framework.automation.driver.web.WebBrowser.BrowserType;
import framework.utils.listeners.WebDriverListener;

/**
 * A factory that returns a singleton of  web driver object.
 */
public class WebDriverFactory extends AutomationDriver {

	private static WebDriver webDriver;

	private WebDriverFactory() {

	}

	/**
	 * Gets the single instance of WebDriverFactory.
	 * Starts browser with proxy server if it is enabled in test
	 * @param browser
	 *            the browser set in properties
	 * @return single instance of WebDriverFactory
	 * @throws Exception
	 *             the exception of invalid browser property
	 */
	public static WebDriver getWebDriver(WebBrowser webBrowser)
			throws Exception {

		if (eventDriver == null) {

			DesiredCapabilities desiredCaps = new DesiredCapabilities();

			if (webBrowser.isProxyEnabled()) {
				int proxyPort = Integer.valueOf(propertiesUtil
						.getProperty("proxy-port"));
				ProxyServerForBrowser.getServerInstance().startProxyServer(
						desiredCaps, proxyPort);
			}

			BrowserType browserType = BrowserType.get(webBrowser
					.getCurrentExecutionEnvironmentName());
			StringBuffer driversBinaryPath = new StringBuffer(
					"src/main/resources/drivers/");

			if (browserType.equals(BrowserType.CHROME)) {
				setChromeDriver(driversBinaryPath);
				setChromeOptions(desiredCaps);
				webDriver = new ChromeDriver(desiredCaps);
			} else if (browserType.equals(BrowserType.FIREFOX)) {
				setFFProfile(desiredCaps);
				webDriver = new FirefoxDriver();
			} else if (browserType.equals(BrowserType.IE)) {
				setIEDriver(driversBinaryPath);
				webDriver = new InternetExplorerDriver();
			} else
				throw new RuntimeException(
						"Invalid browser property set in configuration file");

			eventDriver = new EventFiringWebDriver(webDriver);
			eventDriver.register(new WebDriverListener());
		}

		eventDriver.manage().window().maximize();
		setImplicitTimeOut(eventDriver);

		// start HAR capturing
		if (webBrowser.isProxyEnabled()) {
			ProxyServerForBrowser.getServerInstance().startHarCapturing(
					webBrowser.getUrl());
		}

		return eventDriver;
	}

	/**
	 * @return pre-created web driver object
	 */
	public static WebDriver getCreatedWebDriver() {
		if (eventDriver == null) {
			throw new RuntimeException("Web Driver was not created");
		} else
			return eventDriver;
	}

	/**
	 * Kill web driver instance. Stop proxy server if it is started 
	 */
	public static void killDriverInstance() {
		webDriver.quit();
		webDriver = null;
		eventDriver.quit();
		eventDriver = null;

		try {
			if (WebBrowser.getSetWebBrowser().isProxyEnabled()) {
				ProxyServerForBrowser.getServerInstance().stopProxyServer();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		WebBrowser.getSetWebBrowser().resetBrowser();
	}

	/**
	 * Sets the chrome driver path for specific OS.
	 * @throws Exception
	 *             the exception if current OS does not support Chrome 
	 */
	private static void setChromeDriver(StringBuffer driversBinaryPath)
			throws Exception {
		if (getExecutionOsName().startsWith("win")) {
			driversBinaryPath.append("chrome-win/chromedriver.exe");
		} else if (getExecutionOsName().startsWith("lin")) {
			driversBinaryPath.append("chrome-lin/chromedriver");
		} else if (getExecutionOsName().startsWith("mac")) {
			driversBinaryPath.append("chrome-mac/chromedriver");
		} else
			throw new Exception("Your OS is invalid for webdriver tests");

		System.setProperty("webdriver.chrome.driver",
				driversBinaryPath.toString());
	}

	/**
	 * Set chrome options to web driver
	 * @param desiredCaps	web driver desired capabilites
	 */
	private static void setChromeOptions(DesiredCapabilities desiredCaps) {
		ChromeOptions options = new ChromeOptions();
		options.addArguments("test-type");
		desiredCaps.setCapability(ChromeOptions.CAPABILITY, options);
	}

	/**
	 * set firefox profile to web driver
	 * @param desiredCaps	desired capabilities
	 */
	private static void setFFProfile(DesiredCapabilities desiredCaps) {
		FirefoxProfile fp = new FirefoxProfile();
		desiredCaps.setCapability(FirefoxDriver.PROFILE, fp);
	}

	/**
	 * Sets the ie driver path for specific win OS-x.
	 *
	 * @throws Exception
	 *             the exception if OS doesn't support IE
	 */
	private static void setIEDriver(StringBuffer driversBinaryPath)
			throws Exception {
		if (getExecutionOsName().startsWith("win")) {
			if (getExecutionOsArch().indexOf("32") != -1) {
				driversBinaryPath.append("ie-win32/chromedriver.exe");
			} else {
				driversBinaryPath.append("ie-win64/chromedriver.exe");
			}
		} else
			throw new Exception(
					"Your OS is invalid for Internet Explorer webdriver tests");

		System.setProperty("webdriver.ie.driver", driversBinaryPath.toString());
	}

	/**
	 * @return current OS name
	 */
	private static String getExecutionOsName() {
		return System.getProperty("os.name").toLowerCase();
	}

	/**
	 * @return current OS architecture
	 */
	private static String getExecutionOsArch() {
		return System.getProperty("os.arch").toLowerCase();
	}

}