package framework.automation.page.web;

import org.openqa.selenium.WebDriver;

import ru.yandex.qatools.allure.annotations.Step;
import framework.automation.driver.web.imagerecognition.ImageRecognitionDriver;
import framework.automation.page.CriteriaPageFactory;
import framework.automation.page.Page;

public abstract class WebPage<T extends WebPage<T>> extends Page<T> {

	protected ImageRecognitionDriver<T> imageDriver;

	public WebPage(WebDriver webDriver) {
		super(webDriver);
		// Each time when constructor will be called from Page Object it will
		// call super() constructor and we will get needed generic type to
		// imageDriver
		this.imageDriver = new ImageRecognitionDriver<T>(pageType);
	}
	
	public ImageRecognitionDriver<T> getImageDriver(){
		return imageDriver;
	}

	@Step(value = "Navigate to page: \"{0}\"")
	public <V extends WebPage<V>> V navigateToPageUrl(String url,
			Class<V> pageClass) {
		webDriver.get(url);
		return CriteriaPageFactory.criteriaInitWebElements(webDriver, pageClass);
	}

	@Step(value = "Refreshing current page")
	public T pageRefresh() {
		if (pageType == null) {
			throw new RuntimeException(
					"pageType is null. Can't use this method unless Page is initialized correctly");
		}
		webDriver.navigate().refresh();
		return CriteriaPageFactory.criteriaInitWebElements(webDriver, pageType);
	}

	public String getPageTitle() {
		return webDriver.getTitle();
	}

}
