package framework.automation.page.mobile;

import org.openqa.selenium.WebDriver;

public abstract class MobileNativePage<T extends MobilePage<T>> extends MobilePage<T>{

	public MobileNativePage(WebDriver webDriver) {
		super(webDriver);
		// TODO Auto-generated constructor stub
	}

}
