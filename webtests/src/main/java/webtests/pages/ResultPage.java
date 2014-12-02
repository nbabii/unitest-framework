package webtests.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;

import ru.yandex.qatools.allure.annotations.Step;
import framework.automation.page.web.WebPage;

public class ResultPage extends WebPage<ResultPage> {

	public ResultPage(WebDriver webDriver) {
		super(webDriver);
	}

	@Step(value = "Checking if result page is correct with correct title: \"{0}\"")
	public boolean isResultPageCorrectAndContainsTextInTitle(String text) {
		return webDriver.getTitle().contains(text);
	}

	@Override
	public ExpectedCondition<Boolean> loadedCriteria() {
		return ExpectedConditions.titleContains("");
	}

}
