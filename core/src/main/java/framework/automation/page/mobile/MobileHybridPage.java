package framework.automation.page.mobile;

import org.openqa.selenium.WebDriver;

/**
 * Abstract class to represent Mobile Hybrid Page for mobile Hybrid Page Objects
 * @author Taras.Lytvyn
 *
 * @param <T> mobile hybrid Page Object that will be extended from that class
 */
public abstract class MobileHybridPage<T extends MobilePage<T>> extends MobilePage<T>{

	public MobileHybridPage(WebDriver webDriver) {
		super(webDriver);
		// TODO Auto-generated constructor stub
	}

}
