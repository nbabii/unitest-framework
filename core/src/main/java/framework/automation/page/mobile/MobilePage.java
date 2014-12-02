package framework.automation.page.mobile;

import org.openqa.selenium.WebDriver;

import framework.automation.page.Page;

public abstract class MobilePage<T extends MobilePage<T>> extends Page<T>{

	public MobilePage(WebDriver webDriver) {
		super(webDriver);
	}

}
