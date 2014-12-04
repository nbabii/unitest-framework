package framework.automation.page;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

import org.openqa.selenium.WebDriver;

import framework.automation.utils.js.JSExecution;
import framework.automation.utils.wait.DriverWaitUtil;

/**
 * Abstract class to represent the page or screen for tests
 * @author Taras.Lytvyn
 *
 * @param <T> Page Object that will extend that page
 */
public abstract class Page<T extends Page<T>> implements IPageLoadedCriteria{
	
	protected Class<T> pageType;
	protected WebDriver webDriver;
	protected JSExecution webDriverJSExecutor;
	protected DriverWaitUtil driverWaitUtil;
	
	/*
	 * sets the pageType the class value of current Page Object
	 * It is used in other functionality
	 */
	 
	@SuppressWarnings("unchecked")
	protected Page(WebDriver webDriver) {
		Type genericSuperClass = getClass().getGenericSuperclass();
		if (genericSuperClass instanceof ParameterizedType) {
			// we want to make sure that generic were specified before trying
			// to get that information
			ParameterizedType parameterizedType = (ParameterizedType) genericSuperClass;
			Type type = parameterizedType.getActualTypeArguments()[0];
			pageType = (Class<T>) (type instanceof TypeVariable ? Object.class
					: type);
		}
		this.webDriver = webDriver;
		this.webDriverJSExecutor = new JSExecution(webDriver);
		this.driverWaitUtil = new DriverWaitUtil(webDriver);
	}
	

	/**
	 * Get the web driver
	 * @return web driver
	 */
	public WebDriver getWebDriver() {
		return webDriver;
	}

	/**
	 * Get the JavaScript Executor for web Driver
	 * @return js executor
	 */
	public JSExecution getJsExecutor() {
		return webDriverJSExecutor;
	}

	/**
	 * Get Web Driver Wait Utility
	 * @return the web driver waiter utility
	 */
	public DriverWaitUtil getDriverWaitUtil() {
		return driverWaitUtil;
	}

}