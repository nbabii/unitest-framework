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

public class JSExecution {

	private static final FrameworkLogger LOG = LogFactory
			.getLogger(JSExecution.class);

	protected WebDriver driver;
	protected JavascriptExecutor js;

	public JSExecution(WebDriver driver) {
		this.driver = driver;
		this.js = (JavascriptExecutor) driver;
	}

	public Object execScript(final String expr, Object... arguments) {
		return js.executeScript(expr, arguments);
	}

	public void clickOnElement(WebElement element) {
		execScript(clickSimulationScript()
				+ "; simulate(arguments[0], \"click\")", element);
	}

	public void typeTextToTextBox(WebElement element, String text) {
		execScript("arguments[0].setAttribute('value','" + text + "');",
				element);
	}

	public void scrollVertical(int pixel) {
		execScript("scroll(0," + pixel + ")");
	}

	public void scrollHorizontal(int pixel) {
		execScript("scroll(" + pixel + ",0)");
	}

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

	private boolean checkJQueryDefined() {
		String jqueryResult = (String) execScript("return window.jQuery");
		if (jqueryResult != null) {
			return true;
		} else
			return false;
	}

	private void delay(final long amount) {
		try {
			Thread.sleep(amount);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

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
