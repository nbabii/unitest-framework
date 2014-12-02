package mobiletests.nativepages;

import io.appium.java_client.android.AndroidKeyCode;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import ru.yandex.qatools.allure.annotations.Step;

import com.google.common.base.Function;

import framework.automation.driver.mobile.MobileDriverFactory;
import framework.automation.page.CriteriaPageFactory;
import framework.automation.page.mobile.MobileNativePage;

public class NewNoteEditPage extends MobileNativePage<NewNoteEditPage> {

	@FindBy(how = How.XPATH, using = "//android.widget.EditText[contains(@resource-id,'edit_note')]")
	public WebElement editField;

	@FindBy(how = How.XPATH, using = "//android.widget.ImageButton[contains(@resource-id,'color_btn')]")
	public WebElement colorButton;

	@FindBy(how = How.XPATH, using = "//android.widget.ImageButton[contains(@resource-id,'btn5')]")
	public WebElement colorButtonBlue;

	public NewNoteEditPage(WebDriver webDriver) {
		super(webDriver);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Function<WebDriver, Boolean> loadedCriteria() {
		// Own Function usage example
		Function<WebDriver, Boolean> isElementEnabled = new Function<WebDriver, Boolean>() {
			@Override
			public Boolean apply(WebDriver input) {
				return editField.isEnabled();
			}
		};

		return isElementEnabled;

	}

	@Step(value = "Adding new note with text \"{0}\"")
	public ColorNoteHomePage addNewNoteWithText(String text)
			throws InterruptedException {
		editField.click();
		editField.sendKeys(text);
		MobileDriverFactory.getCreatedAppiumDriver().hideKeyboard();
		colorButton.click();
		colorButtonBlue.click();
		MobileDriverFactory.getCreatedAppiumDriver().sendKeyEvent(
				AndroidKeyCode.BACK);
		Thread.sleep(2000);
		MobileDriverFactory.getCreatedAppiumDriver().sendKeyEvent(
				AndroidKeyCode.BACK);
		return CriteriaPageFactory.criteriaInitNativeMobileElements(webDriver,
				ColorNoteHomePage.class);
	}

}
