package framework.automation.driver.web;

import java.util.HashMap;
import java.util.Map;

import framework.automation.driver.ExecutionEnvironment;

/**
 * Class that represents Web Browser environment
 * 
 * @author Taras.Lytvyn
 *
 */
public class WebBrowser extends ExecutionEnvironment {

	private boolean proxyEnabled;
	private String url;

	private static WebBrowser webBrowser = null;

	private WebBrowser() {

	}

	private WebBrowser(String browserName, String url, String proxyEnabled) {
		this.currentExecutionEnvironmentName = browserName;
		this.proxyEnabled = Boolean.parseBoolean(proxyEnabled);
	}

	/**
	 * singleton that get the instance of Web Browse object
	 * 
	 * @param browserName
	 *            browser name (Chrome, FF, etc)
	 * @param proxyEnabled
	 *            proxy is enabled or not
	 * @return Web Browser instance object
	 */
	public static WebBrowser getInstance(String browserName, String url,
			String proxyEnabled) {
		if (webBrowser == null) {
			webBrowser = new WebBrowser(browserName, url, proxyEnabled);
		}
		return webBrowser;
	}

	/**
	 * resets the Browser object
	 */
	public void resetBrowser() {
		webBrowser = null;
	}

	/**
	 * 
	 * @return pre-created Web Browser object
	 */
	public static WebBrowser getSetWebBrowser() {
		if (webBrowser == null) {
			throw new RuntimeException("Web browser is not set");
		}
		return webBrowser;
	}

	/**
	 * 
	 * @return is proxy enabled ?
	 */
	public boolean isProxyEnabled() {
		return proxyEnabled;
	}

	/**
	 * 
	 * @return url of the web site
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * Enum that represents
	 * 
	 * @author Taras.Lytvyn
	 *
	 */
	public enum BrowserType {
		FIREFOX("firefox"), CHROME("chrome"), IE("ie");

		private String browserKey;
		private static Map<String, BrowserType> browserMap = new HashMap<String, BrowserType>();

		static {
			for (BrowserType bt : BrowserType.values()) {
				browserMap.put(bt.key(), bt);
			}
		}

		private BrowserType(String key) {
			browserKey = key;
		}

		private String key() {
			return this.browserKey;
		}

		public static BrowserType get(String key) {
			if (browserMap.containsKey(key)) {
				return browserMap.get(key);
			}
			return FIREFOX;
		}

	}

}
