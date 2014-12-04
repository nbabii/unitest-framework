package framework.automation.page.web;

import org.openqa.selenium.WebDriver;

import ru.yandex.qatools.allure.annotations.Step;
import framework.automation.driver.web.imagerecognition.ImageRecognitionDriver;
import framework.automation.page.CriteriaPageFactory;
import framework.automation.page.Page;

/**
 * Abstract class to represent Web Page for web tests
 * @author Taras.Lytvyn
 *
 * @param <T> web page Page Object that will extend that class
 */
public abstract class WebPage<T extends WebPage<T>> extends Page<T> {

	protected ImageRecognitionDriver<T> imageDriver;

	public WebPage(WebDriver webDriver) {
		super(webDriver);
		// Each time when constructor will be called from Page Object it will
		// call super() constructor and we will get needed generic type to
		// imageDriver
		this.imageDriver = new ImageRecognitionDriver<T>(pageType);
	}
	
	/**
	 * gets Image recognition Driver instance
	 * @return
	 */
	public ImageRecognitionDriver<T> getImageDriver(){
		return imageDriver;
	}

	/**
	 * Navigates to url and return Page Object from navigation url
	 * @param url navigation url
	 * @param pageClass Page Object class
	 * @return Page Object instance after navigation
	 */
	@Step(value = "Navigate to page: \"{0}\"")
	public <V extends WebPage<V>> V navigateToPageUrl(String url,
			Class<V> pageClass) {
		webDriver.get(url);
		return CriteriaPageFactory.criteriaInitWebElements(webDriver, pageClass);
	}

	/**
	 * Refreshing current Page Object (like F5 in browser)
	 * @return current Page Object instance
	 */
	@Step(value = "Refreshing current page")
	public T pageRefresh() {
		if (pageType == null) {
			throw new RuntimeException(
					"pageType is null. Can't use this method unless Page is initialized correctly");
		}
		webDriver.navigate().refresh();
		return CriteriaPageFactory.criteriaInitWebElements(webDriver, pageType);
	}

	/**
	 * gets web page title
	 * @return title of current web page
	 */
	public String getPageTitle() {
		return webDriver.getTitle();
	}

}
