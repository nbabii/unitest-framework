package framework.automation.page;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;

import framework.automation.page.mobile.MobileHybridPage;
import framework.automation.page.mobile.MobileNativePage;
import framework.automation.page.web.WebPage;
import framework.automation.utils.js.JSExecution;
import framework.automation.utils.wait.DriverWaitUtil;

/**
 * Page Factory wrapper class used to get Page Object using Page Factory
 * approach but with criteria loading parameter that will be set in each Page
 * Object
 * 
 * @author Taras.Lytvyn
 *
 */
public class CriteriaPageFactory extends PageFactory {

	private static DriverWaitUtil driverWaitUtil;

	/**
	 * should be used instead of initElements when we get the Page Factory
	 * approach with Page Objects in Web tests
	 * 
	 * @param driver web driver
	 * @param pageClassToProxy Page Object class
	 * @return instance of Page Object we pass to class parameter
	 */
	public static <T extends WebPage<T>> T criteriaInitWebElements(
			WebDriver driver, Class<T> pageClassToProxy) {
		T page = criteriaInitElements(driver, pageClassToProxy);
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		return page;
	}

	/**
	 * should be used instead of initElements when we get the Page Factory
	 * approach with Page Objects in Mobile Hybrid tests
	 * 
	 * @param driver web driver
	 * @param pageClassToProxy Page Object class
	 * @return instance of Page Object we pass to class parameter
	 */
	public static <T extends MobileHybridPage<T>> T criteriaInitHybridMobileElements(
			WebDriver driver, Class<T> pageClassToProxy) {
		return criteriaInitElements(driver, pageClassToProxy);
	}

	/**
	 * should be used instead of initElements when we get the Page Factory
	 * approach with Page Objects in Mobile Native tests
	 * 
	 * @param driver web driver
	 * @param pageClassToProxy Page Object class
	 * @return instance of Page Object we pass to class parameter
	 */
	public static <T extends MobileNativePage<T>> T criteriaInitNativeMobileElements(
			WebDriver driver, Class<T> pageClassToProxy) {
		T page = instantiatePage(driver, pageClassToProxy);
		initElements(driver, page);
		waitForPageObjectCriteria(driver, page);
		return page;
	}

	/**
	 * Initialize the Page Object after waiting for loaded criteria to be pass
	 * @param driver web driver
	 * @param pageClassToProxy Page Object class
	 * @return Page Object instance 
	 */
	private static <T extends IPageLoadedCriteria> T criteriaInitElements(
			WebDriver driver, Class<T> pageClassToProxy) {
		T page = instantiatePage(driver, pageClassToProxy);
		initElements(driver, page);
		waitForNextPageToLoad(driver);
		waitForPageObjectCriteria(driver, page);
		return page;
	}

	/**
	 * Page Factory duplicate method because it is private in Page Factory
	 * @param driver
	 * @param pageClassToProxy
	 * @return
	 */
	private static <T> T instantiatePage(WebDriver driver,
			Class<T> pageClassToProxy) {
		try {
			try {
				Constructor<T> constructor = pageClassToProxy
						.getConstructor(WebDriver.class);
				return constructor.newInstance(driver);
			} catch (NoSuchMethodException e) {
				return pageClassToProxy.newInstance();
			}
		} catch (InstantiationException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Wait until Page is loaded in DOM
	 * @param driver web driver
	 */
	public static void waitForNextPageToLoad(WebDriver driver) {
		driverWaitUtil = new DriverWaitUtil(driver);
		driverWaitUtil.forCondition(new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				JSExecution jsExecutor = new JSExecution(driver);
				return jsExecutor.execScript("return document.readyState")
						.equals("complete");
			}
		});
	}

	/**
	 * Wait for page is loaded by it's loaded criteria
	 * @param driver web driver
	 * @param page Page Object
	 */
	private static <T extends IPageLoadedCriteria> void waitForPageObjectCriteria(
			WebDriver driver, T page) {
		driverWaitUtil = new DriverWaitUtil(driver);
		driverWaitUtil.forCondition(page.loadedCriteria());
	}

}
