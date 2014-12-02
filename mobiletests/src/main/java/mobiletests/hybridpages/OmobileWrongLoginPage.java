package mobiletests.hybridpages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;

import ru.yandex.qatools.allure.annotations.Step;
import framework.automation.page.CriteriaPageFactory;
import framework.automation.page.mobile.MobileHybridPage;

public class OmobileWrongLoginPage extends
		MobileHybridPage<OmobileWrongLoginPage> {

	@FindBy(how = How.XPATH, using = ".//button[contains(text(),'Try again')]")
	public WebElement tryAgainButton;

	public OmobileWrongLoginPage(WebDriver webDriver) {
		super(webDriver);
		// TODO Auto-generated constructor stub
	}

	@Override
	public ExpectedCondition<WebElement> loadedCriteria() {
		return ExpectedConditions.visibilityOf(tryAgainButton);
	}

	@Step(value = "Trying to login once again")
	public OmobileLoginPage tryLoginAgain() {
		tryAgainButton.click();
		return CriteriaPageFactory.criteriaInitHybridMobileElements(webDriver,
				OmobileLoginPage.class);
	}

}
