package framework.automation.page.mobile;

import org.openqa.selenium.WebDriver;

/**
 * Abstract class to represent Mobile Native Page for mobile Hybrid Page Objects
 * @author Taras.Lytvyn
 *
 * @param <T> mobile native Page Object that will be extended from that class
 */
public abstract class MobileNativePage<T extends MobilePage<T>> extends MobilePage<T>{

	public MobileNativePage(WebDriver webDriver) {
		super(webDriver);
		// TODO Auto-generated constructor stub
	}

}
