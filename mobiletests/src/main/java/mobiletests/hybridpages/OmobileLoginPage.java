package mobiletests.hybridpages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;

import ru.yandex.qatools.allure.annotations.Step;
import framework.automation.page.CriteriaPageFactory;
import framework.automation.page.mobile.MobileHybridPage;

public class OmobileLoginPage extends MobileHybridPage<OmobileLoginPage>{
	
	@FindBy (how = How.XPATH, using = "//*[@name='email']")
	public WebElement loginTextBox;
	
	@FindBy (how = How.XPATH, using = "//*[@name='password']")
	public WebElement passwordTextBox;
	
	@FindBy (how = How.XPATH, using = "//button[@id='loginButton']")
	public WebElement signInButton;

	public OmobileLoginPage(WebDriver webDriver) {
		super(webDriver);
		// TODO Auto-generated constructor stub
	}
	
	@SuppressWarnings("serial")
	@Override
	public ExpectedCondition<List<WebElement>> loadedCriteria() {
		return ExpectedConditions.visibilityOfAllElements(new ArrayList<WebElement>() {{
		    add(loginTextBox);
		    add(passwordTextBox);
		    add(signInButton);
		}});
	}
	
	@Step(value = "Login to omobile app with incorrect credentials")
	public OmobileWrongLoginPage failLoginToApp() {
		loginTextBox.clear();
		loginTextBox.sendKeys("wrong login !!!");
		passwordTextBox.clear();
		passwordTextBox.sendKeys("wrong pass !!!");
		signInButton.click();
		return CriteriaPageFactory.criteriaInitHybridMobileElements(webDriver, OmobileWrongLoginPage.class);
	}

}
