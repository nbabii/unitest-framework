package framework.automation.page;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

import org.openqa.selenium.WebDriver;

import framework.automation.utils.js.JSExecution;
import framework.automation.utils.wait.DriverWaitUtil;

public abstract class Page<T extends Page<T>> implements IPageLoadedCriteria{
	
	protected Class<T> pageType;
	protected WebDriver webDriver;
	protected JSExecution webDriverJSExecutor;
	protected DriverWaitUtil driverWaitUtil;
	
	@SuppressWarnings("unchecked")
	protected Page(WebDriver webDriver) {
		Type genericSuperClass = getClass().getGenericSuperclass();
		if (genericSuperClass instanceof ParameterizedType) {
			// we want to make sure that generics were specified before trying
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
	

	public WebDriver getWebDriver() {
		return webDriver;
	}

	public JSExecution getJsExecutor() {
		return webDriverJSExecutor;
	}

	public DriverWaitUtil getDriverWaitUtil() {
		return driverWaitUtil;
	}

}