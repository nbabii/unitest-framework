package webtests.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;

import ru.yandex.qatools.allure.annotations.Step;
import framework.automation.page.web.WebPage;

public class EleksTestingQAPage extends WebPage<EleksTestingQAPage> {

	public EleksTestingQAPage(WebDriver webDriver) {
		super(webDriver);
	}

	@FindBy(how = How.XPATH, using = "//*[contains(@class,'copyright_and_min_menu_wrap')]")
	public WebElement copyrightElement;

	@FindBy(how = How.XPATH, using = "//*[contains(@class,'book') and parent::node()[contains(@class,'content')]]")
	public WebElement bookBanner;

	@Override
	public ExpectedCondition<WebElement> loadedCriteria() {
		return ExpectedConditions.visibilityOf(copyrightElement);
	}

	@Step(value = "Check Book Banner is present")
	public boolean isBookBannerDisplayed() {
		return bookBanner.isEnabled() && bookBanner.isDisplayed();
	}

}
