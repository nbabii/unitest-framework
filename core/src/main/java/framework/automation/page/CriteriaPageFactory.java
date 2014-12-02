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

public class CriteriaPageFactory extends PageFactory {

	private static DriverWaitUtil driverWaitUtil;

	public static <T extends WebPage<T>> T criteriaInitWebElements(
			WebDriver driver, Class<T> pageClassToProxy) {
		T page = criteriaInitElements(driver, pageClassToProxy);
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		return page;
	}
	
	public static <T extends MobileHybridPage<T>> T criteriaInitHybridMobileElements(
			WebDriver driver, Class<T> pageClassToProxy) {
		return criteriaInitElements(driver, pageClassToProxy);
	}
	
	public static <T extends MobileNativePage<T>> T criteriaInitNativeMobileElements(
			WebDriver driver, Class<T> pageClassToProxy) {
		T page = instantiatePage(driver, pageClassToProxy);
		initElements(driver, page);
		waitForPageObjectCriteria(driver, page);
		return page;
	}
	
	private static <T extends IPageLoadedCriteria> T criteriaInitElements(WebDriver driver, Class<T> pageClassToProxy){
		T page = instantiatePage(driver, pageClassToProxy);
		initElements(driver, page);
		waitForNextPageToLoad(driver);
		waitForPageObjectCriteria(driver, page);
		return page;
	}

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

	public static void waitForNextPageToLoad(WebDriver driver) {
		driverWaitUtil = new DriverWaitUtil(driver);
		driverWaitUtil.forCondition(new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				JSExecution jsExecutor = new JSExecution(driver);
				return jsExecutor.execScript("return document.readyState").equals(
						"complete");
			}
		});
	}

	private static <T extends IPageLoadedCriteria> void waitForPageObjectCriteria(
			WebDriver driver, T page) {
		driverWaitUtil = new DriverWaitUtil(driver);
		driverWaitUtil.forCondition(page.loadedCriteria());
	}

}
