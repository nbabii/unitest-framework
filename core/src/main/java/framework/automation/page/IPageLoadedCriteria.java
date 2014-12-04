package framework.automation.page;

import org.openqa.selenium.WebDriver;

import com.google.common.base.Function;

/**
 * Interface to represent loadedCriteria for all the Pages
 * @author Taras.Lytvyn
 *
 */
public interface IPageLoadedCriteria {
	
	/*
	 * loaded criteria that will be implemented by each page
	 */
	public Function<WebDriver, ?> loadedCriteria();

}