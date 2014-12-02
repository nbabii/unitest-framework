package mobiletests.nativepages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import ru.yandex.qatools.allure.annotations.Step;

import com.google.common.base.Function;

import framework.automation.page.CriteriaPageFactory;
import framework.automation.page.mobile.MobileNativePage;

public class ColorNoteHomePage extends MobileNativePage<ColorNoteHomePage>{
	
	@FindBy (how = How.XPATH, using = "//android.widget.ImageButton[contains(@resource-id,'main_btn1')]")
	public WebElement addButton;
	
	@FindBy (how = How.XPATH, using = "//android.widget.TextView[contains(@text,'Text')]")
	public WebElement textButton;
	
	@FindBy (how = How.XPATH, using = "//android.widget.TextView[contains(@resource-id,'id/text')][1]")
	public WebElement firstNote;
	
	public ColorNoteHomePage(WebDriver webDriver) {
		super(webDriver);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public Function<WebDriver, Boolean> loadedCriteria() {
		//Own Function usage example
		Function<WebDriver, Boolean> isElementEnabled = new Function<WebDriver, Boolean>() {
			@Override
			public Boolean apply(WebDriver input) {
				return addButton.isEnabled();
			}
		};
		
		return isElementEnabled;
		
		//Expected Condition usage example
		//return ExpectedConditions.textToBePresentInElement(resultCount, "00");
	}
	
	@Step(value = "Opening add note")
	public NewNoteEditPage openAddNote() {
		addButton.click();
		textButton.click();
		return CriteriaPageFactory
				.criteriaInitNativeMobileElements(webDriver, NewNoteEditPage.class);
	}
	
	@Step(value = "Getting first note from the list text value")
	public String getFirstNoteText(){
		return firstNote.getAttribute("text");
	}

}
