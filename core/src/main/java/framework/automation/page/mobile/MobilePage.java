package framework.automation.page.mobile;

import org.openqa.selenium.WebDriver;

import framework.automation.page.Page;

/**
 * Abstract class to represent Mobile Page for mobile Hybrid and Native Page Objects
 * @author Taras.Lytvyn
 *
 * @param <T> mobile hybrid and native class that will be extended from that class
 */
public abstract class MobilePage<T extends MobilePage<T>> extends Page<T>{

	public MobilePage(WebDriver webDriver) {
		super(webDriver);
	}

}
