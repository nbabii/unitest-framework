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

public class EleksHomePage extends WebPage<EleksHomePage>{
	
	public EleksHomePage(WebDriver webDriver) {
		super(webDriver);
	}

	@FindBy (how = How.XPATH, using = "//*[contains(text(),'Testing and QA')]")
	public WebElement testingQAButton;
	
	@FindBy (how = How.XPATH, using = "//*[contains(@class,'copyright_and_min_menu_wrap')]")
	public WebElement copyrightelement;
	
	@Override
	public ExpectedCondition<WebElement> loadedCriteria() {
		return ExpectedConditions.visibilityOf(copyrightelement);
	}
	
	@Step(value = "Open Testing and QA")
	public EleksTestingQAPage openTestingQA(){
		testingQAButton.click();
		return CriteriaPageFactory.criteriaInitWebElements(webDriver, EleksTestingQAPage.class);
	} 

}

