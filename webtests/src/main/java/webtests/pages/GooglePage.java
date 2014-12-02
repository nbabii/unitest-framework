package webtests.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;

import ru.yandex.qatools.allure.annotations.Step;
import framework.automation.page.CriteriaPageFactory;
import framework.automation.page.web.WebPage;

public class GooglePage extends WebPage<GooglePage>{
	
	@FindBy (how = How.ID, using = "gbqfq")
	public WebElement searchBox;

	@FindBy (how = How.ID, using = "gbqfbb")
	public WebElement testButton1;
	
	public GooglePage(WebDriver webDriver) {
		super(webDriver);
	}

	@Override
	public ExpectedCondition<WebElement> loadedCriteria() {
		return ExpectedConditions.visibilityOf(searchBox);
	}
	
	@Step(value = "Search for \"{0}\"")
	public SearchResultPage searchText(String searchFor){
		//testing js executor
		//webDriverJSExecutor.clickOnElement(testButton1);
		//webDriverJSExecutor.typeTextToTextBox(searchBox, "asdasdasdasdasd");
		//testing imageDriver
		//imageDriver.clickAndStayOnSamePageObject(webDriver, "button2.png");
		searchBox.sendKeys(searchFor + "\n");
		//webDriverJSExecutor.scrollVertical(350);
		//webDriverJSExecutor.scrollVertical(-250);
		//webDriverJSExecutor.waitForAjax(10);
		return CriteriaPageFactory.criteriaInitWebElements(webDriver, SearchResultPage.class);
	} 

}
