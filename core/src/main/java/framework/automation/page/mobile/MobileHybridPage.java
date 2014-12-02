package framework.automation.page.mobile;

import org.openqa.selenium.WebDriver;

public abstract class MobileHybridPage<T extends MobilePage<T>> extends MobilePage<T>{

	public MobileHybridPage(WebDriver webDriver) {
		super(webDriver);
		// TODO Auto-generated constructor stub
	}

}
