package webtests.pages;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import ru.yandex.qatools.allure.annotations.Step;

import com.google.common.base.Function;
import com.google.common.base.Predicate;

import framework.automation.page.CriteriaPageFactory;
import framework.automation.page.web.WebPage;
import framework.automation.utils.wait.WaitUtil;

public class SearchResultPage extends WebPage<SearchResultPage> {

	public SearchResultPage(WebDriver webDriver) {
		super(webDriver);
	}

	@FindBy(how = How.ID, using = "resultStats")
	public WebElement resultCount;

	@FindBy(how = How.XPATH, using = "//div[contains(@class,'rc')]/h3/a")
	public List<WebElement> resultUrls;

	@Override
	public Function<WebDriver, Boolean> loadedCriteria() {
		//Predicate usage example
		final Boolean match = resultCount.getText().matches(".*[/d]*.*");
		WaitUtil<Boolean> waitUtil = new WaitUtil<Boolean>(match);
		waitUtil.forCondition(new Predicate<Boolean>() {
			@Override
			public boolean apply(Boolean o) {
				return match;
			}
		});
		
		//Own Function usage example
		Function<WebDriver, Boolean> titleContainFunction = new Function<WebDriver, Boolean>() {
			@Override
			public Boolean apply(WebDriver input) {
				return resultCount.getText().matches(".*[/d]*.*");
			}
		};
		
		return titleContainFunction;
		
		//Expected Condition usage example
		//return ExpectedConditions.textToBePresentInElement(resultCount, "00");
	}

	@Step(value = "Opening result page # \"{0}\"")
	public ResultPage openResultPage(int i) {
		//testing page refresh
		pageRefresh();
		resultUrls.get(i - 1).click();
		return CriteriaPageFactory.criteriaInitWebElements(webDriver, ResultPage.class);
	}

}
