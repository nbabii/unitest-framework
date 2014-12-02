package framework.automation.page;

import org.openqa.selenium.WebDriver;

import com.google.common.base.Function;

public interface IPageLoadedCriteria {
	
	public Function<WebDriver, ?> loadedCriteria();

}