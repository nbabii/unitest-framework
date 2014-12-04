package framework.automation.utils.js;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;

import framework.utils.log.FrameworkLogger;
import framework.utils.log.LogFactory;

/**
 * Js executor class that wraps the web driver in constructor and can execute
 * java script helpfull features
 * 
 * @author Taras.Lytvyn
 *
 */
public class JSExecution {

	private static final FrameworkLogger LOG = LogFactory
			.getLogger(JSExecution.class);

	protected WebDriver driver;
	protected JavascriptExecutor js;

	public JSExecution(WebDriver driver) {
		this.driver = driver;
		this.js = (JavascriptExecutor) driver;
	}

	/**
	 * execute the script with parameters
	 * @param expr	js expression
	 * @param arguments	parameters
	 * @return	Object result of execution
	 */
	public Object execScript(final String expr, Object... arguments) {
		return js.executeScript(expr, arguments);
	}

	/**
	 * clicks on elements with JS executor
	 * @param element	what WebElement to click
	 */
	public void clickOnElement(WebElement element) {
		execScript(clickSimulationScript()
				+ "; simulate(arguments[0], \"click\")", element);
	}

	/**
	 * Types text to WebElement with JS
	 * @param element	what element to type text in
	 * @param text	what text to type
	 */
	public void typeTextToTextBox(WebElement element, String text) {
		execScript("arguments[0].setAttribute('value','" + text + "');",
				element);
	}

	/**
	 * scroll vertically the page/screen
	 * @param pixel	how many pixels to scroll
	 */
	public void scrollVertical(int pixel) {
		execScript("scroll(0," + pixel + ")");
	}

	/**
	 * scroll horizontally the page/screen
	 * @param pixel	how many pixels to scroll
	 */
	public void scrollHorizontal(int pixel) {
		execScript("scroll(" + pixel + ",0)");
	}

	/**
	 * Wait for all ajax calls to be finished
	 * @param timeoutInSeconds limit timeout - how many time to wait
	 */
	public void waitForAjax(int timeoutInSeconds) {
		if (checkJQueryDefined()) {
			LOG.info("Checking active ajax calls by calling jquery.active");
			for (int i = 0; i < timeoutInSeconds; i++) {
				Object numberOfAjaxConnections = execScript("return jQuery.active");
				// return should be a number
				if (numberOfAjaxConnections instanceof Long) {
					Long n = (Long) numberOfAjaxConnections;
					LOG.info("Number of active jquery ajax calls: " + n);
					if (n.longValue() == 0L) {
						break;
					}
				}
				delay(1000);
			}
		} else {
			LOG.info("JQuery is undefined for page");
		}
	}

	/**
	 * ExpectedCondition that is waiting until active ajax 
	 * calls will be finished with time limit
	 * Ex.: Can be used in Page load criteria 
	 * @param timeout	limit timeout to wait
	 * @return	ExpectedCondition
	 */
	public ExpectedCondition<Boolean> waitForAjax(final long timeout) {
		return new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				if (checkJQueryDefined()) {
					final long startTime = System.currentTimeMillis();

					while ((startTime + timeout) >= System.currentTimeMillis()) {
						final Boolean scriptResult = (Boolean) execScript("return jQuery.active == 0");

						if (scriptResult) {
							return true;
						}
						delay(100);
					}
				}
				return false;
			}
		};
	}

	/**
	 * checks whether the jQuery is defined on Page
	 * @return
	 */
	private boolean checkJQueryDefined() {
		String jqueryResult = (String) execScript("return window.jQuery");
		if (jqueryResult != null) {
			return true;
		} else
			return false;
	}

	/**
	 * set delay
	 * @param amount delay in ms
	 */
	private void delay(final long amount) {
		try {
			Thread.sleep(amount);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * touch the click event to the page with click script
	 * @return	click event script
	 */
	private String clickSimulationScript() {
		byte[] encoded = null;
		try {
			encoded = Files.readAllBytes(Paths
					.get("src/main/resources/firing-js-click-script.txt"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new String(encoded, StandardCharsets.UTF_8);
	}

}
