package framework.automation.driver.mobile;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;

import java.net.URL;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import com.google.common.base.Predicate;

import framework.automation.driver.AutomationDriver;
import framework.automation.driver.mobile.MobilePlatform.MobileAppType;
import framework.automation.driver.mobile.MobilePlatform.MobileOsType;
import framework.automation.utils.wait.WaitUtil;
import framework.utils.listeners.HybridMobileDriverListener;
import framework.utils.listeners.NativeMobileDriverListener;

/**
 * A factory that returns a singleton of mobile driver object.
 * @author Taras.Lytvyn
 *
 */
public class MobileDriverFactory extends AutomationDriver{

	private static AppiumDriver appiumDriver;
	
	private MobileDriverFactory() {

	}
	
	/**
	 * get Driver method
	 * @param mobilePlatform	mobile platform object
	 * @return	driver for mobile execution
	 * @throws Exception if invalid mobile property platform is set
	 */
	public static WebDriver getAppiumDriver(MobilePlatform mobilePlatform)
			throws Exception {
		if (eventDriver == null) {
			
			DesiredCapabilities desiredCaps = new DesiredCapabilities();
			
			MobileOsType osType = MobileOsType.get(mobilePlatform.getCurrentExecutionEnvironmentName());
			MobileAppType appType = MobileAppType.get(mobilePlatform.getMobileApplicationType());
			
			if (osType.equals(MobileOsType.ANDROID)) {
				desiredCaps.setCapability(MobileCapabilityType.PLATFORM_NAME, "android");
				desiredCaps.setCapability(MobileCapabilityType.DEVICE_NAME, mobilePlatform.getMobileDeviceName());
				if (appType.equals(MobileAppType.NATIVE)){
					
					desiredCaps.setCapability(MobileCapabilityType.APP, propertiesUtil.getProperty("application.path"));
					appiumDriver = new AndroidDriver(new URL(propertiesUtil.getProperty("appium.server.url")), desiredCaps);
					eventDriver = new EventFiringWebDriver(appiumDriver);
					eventDriver.register(new NativeMobileDriverListener());
					
				} else if (appType.equals(MobileAppType.HYBRID)) {
					desiredCaps.setCapability(MobileCapabilityType.AUTOMATION_NAME, "selendroid");
					desiredCaps.setCapability(MobileCapabilityType.APP_PACKAGE, propertiesUtil.getProperty("application.package"));
					desiredCaps.setCapability(MobileCapabilityType.APP_ACTIVITY, propertiesUtil.getProperty("application.activity"));
					desiredCaps.setCapability(MobileCapabilityType.APP_WAIT_PACKAGE, propertiesUtil.getProperty("application.package"));
					desiredCaps.setCapability(MobileCapabilityType.APP_WAIT_ACTIVITY, propertiesUtil.getProperty("application.activity"));
					
					appiumDriver = new AndroidDriver(new URL(propertiesUtil.getProperty("appium.server.url")), desiredCaps);
					switchtoWebView(appiumDriver, Integer.valueOf(propertiesUtil.getProperty("webView.appearance.timeout")));
					eventDriver = new EventFiringWebDriver(appiumDriver);
					eventDriver.register(new HybridMobileDriverListener());
					
				} else throw new RuntimeException ("Invalid type of Android application set - should be Hybrid or Native");
				
			} else if (osType.equals(MobileOsType.IOS)) {
				desiredCaps.setCapability(MobileCapabilityType.PLATFORM_NAME, "IOS");
				if (appType.equals(MobileAppType.NATIVE)){
					
				} else if (appType.equals(MobileAppType.HYBRID)) {
					
				} else throw new RuntimeException ("Invalid type of IOS application set - should be Hybrid or Native");
			} else
				throw new RuntimeException(
						"Invalid mobile OS property set in configuration file. Should be IOS or Android.");
			
		}
		
		setImplicitTimeOut(eventDriver);
		
		return eventDriver;
	}
/*
	public static WebDriver getCreatedAppiumDriver() {
		if (eventDriver == null) {
			throw new RuntimeException("Appium Driver was not created");
		} else
			return eventDriver;
	}
*/
	/**
	 * 
	 * @return pre-created mobile automation driver
	 */
	public static AppiumDriver getCreatedAppiumDriver() {
		if (appiumDriver == null) {
			throw new RuntimeException("Appium Driver was not created");
		} else
			return appiumDriver;
	}
	
	/**
	 * Kill mobile driver instance.
	 */
	public static void killDriverInstance() {
		appiumDriver.quit();
		appiumDriver = null;
		eventDriver.quit();
		eventDriver = null;
		MobilePlatform.getSetMobilePlatform().resetMobilePlatform();
	}
	
	/**
	 * Switch to mobile web view if such view has appeared
	 * @param driver	mobile driver 
	 * @param webViewAppearanceTimeout	timeout - maximum time for webview appearence waiting
	 * @throws InterruptedException
	 */
	private static void switchtoWebView(final AppiumDriver driver, int webViewAppearanceTimeout) throws InterruptedException{
		Thread.sleep(webViewAppearanceTimeout * 1000);
		final int contextQuantity = driver.getContextHandles().toArray().length;
    	WaitUtil<Integer> waitUtil = new WaitUtil<Integer>(contextQuantity);
		waitUtil.forCondition(new Predicate<Integer>() {
			@Override
			public boolean apply(Integer o) {
				return contextQuantity == 2;
			}
		});
    	driver.context((String)driver.getContextHandles().toArray()[1]);
	}
}