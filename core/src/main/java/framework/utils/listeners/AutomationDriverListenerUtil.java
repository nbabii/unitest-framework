package framework.utils.listeners;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import ru.yandex.qatools.allure.annotations.Attachment;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.coordinates.WebDriverCoordsProvider;
import ru.yandex.qatools.ashot.cropper.indent.BlurFilter;
import ru.yandex.qatools.ashot.cropper.indent.IndentCropper;
import framework.automation.utils.js.JSExecution;

public class AutomationDriverListenerUtil {

	@Attachment(value = "Element action was performed \"{0}\" description: ")
	static String getElementDescription(String description, WebDriver driver, WebElement element) {
		return "Element performed action was: " + description + "\n"
				+ "Element absolute xpath was: "
				+ getElementDescriptorXPATH(driver, element) + "\n"
				+ "Element tag name: " + element.getTagName() + "\n"
				+ "Element text: " + "'" + element.getText() + "'";
	}

	@Attachment(value = "Element action was performed \"{0}\" description: ")
	static String getNativeMobileElementDescription(String description, WebDriver driver,
			WebElement element) {
		return "Element performed action was: " + description + "\n"
				+ "Element tag name: " + element.getTagName() + "\n"
				+ "Element text: " + "'" + element.getText() + "'";
	}

	@Attachment(value = "A step screenshot with highlighted element: ")
	static byte[] takeWebElementScreenshotImage(WebDriver driver,
			WebElement element, String screenShotStepFilter) {
		return TestListenerUtil
				.getByteArrayFromImage(
						takeWebElementScreenshot(driver, element,
								screenShotStepFilter), "png");
	}

	@Attachment(value = "After step screenshot: ")
	static byte[] takeWebBrowserScreenshotImageAfterStep(WebDriver driver) {
		return TestListenerUtil.getByteArrayFromImage(
				takeWebBrowserScreenshot(driver), "png");
	}

	@Attachment(value = "Script that was executed: ")
	static String getScriptExecuted(String script) {
		return script;
	}

	@Attachment(value = "Esception description: ")
	static String getExceptionDescription(Throwable throwable) {
		return throwable.getMessage();
	}

	private static String getElementDescriptorXPATH(WebDriver driver,
			WebElement element) {
		JSExecution jsExecutor = new JSExecution(driver);
		return (String) jsExecutor.execScript("gPt = function(c) {"
				+ "if(c.id!=='') {" + "return'id(\"'+c.id+'\")' }"
				+ "if(c===document.body) {" + "return c.tagName }"
				+ "var a=0; var e=c.parentNode.childNodes;"
				+ "for (var b=0; b < e.length; b++) {" + "var d=e[b];"
				+ "if(d===c) { "
				+ "return gPt(c.parentNode)+ '/' + c.tagName + '['+(a+1)+']' }"
				+ "if(d.nodeType===1&&d.tagName===c.tagName){" + "a++ } } };"
				+ "return gPt(arguments[0]).toLowerCase();", element);
	}

	static String getElementString(WebElement element) {
		return element.toString().substring(
				element.toString().indexOf("->") + 3,
				element.toString().length() - 1);
	}

	private static BufferedImage takeWebBrowserScreenshot(WebDriver driver) {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		Screenshot screenImage = new AShot().coordsProvider(
				new WebDriverCoordsProvider()).takeScreenshot(driver);

		return screenImage.getImage();
	}

	private static BufferedImage takeWebElementScreenshot(WebDriver driver,
			WebElement element, String screenShotStepFilter) {
		BufferedImage elementScreenShotOnStep = null;

		if (screenShotStepFilter.equals("blur")) {
			elementScreenShotOnStep = blurElementOnScreenShot(driver, element);
		} else if (screenShotStepFilter.equals("redline")) {
			BufferedImage pageScreenShot = takeWebBrowserScreenshot(driver);
			elementScreenShotOnStep = cropElementOnScreenShotWithRectangle(
					pageScreenShot, element);
		} else {
			throw new RuntimeException(
					"Invalid parameter for screenshot filter is set");
		}
		return elementScreenShotOnStep;

	}

	private static BufferedImage cropElementOnScreenShotWithRectangle(
			BufferedImage pageScreenShot, WebElement element) {
		Point point = element.getLocation();
		int eleWidth = element.getSize().getWidth();
		int eleHeight = element.getSize().getHeight();

		int xLeftCropRectangleMove = point.getX() / 2;
		int xRightCropRectangleMove = (pageScreenShot.getWidth() - eleWidth - point
				.getX()) / 2;

		int yTopCropRectangleMove = point.getY() / 2;
		int yBottomCropRectangleMove = (pageScreenShot.getHeight() - eleHeight - point
				.getY()) / 2;

		// setting the aligned rectangle parameters
		Graphics2D graph = pageScreenShot.createGraphics();
		graph.setColor(Color.RED);
		graph.setStroke(new BasicStroke(2));

		// draw red rectangle
		graph.drawRect(point.getX() - 5, point.getY() - 5, eleWidth + 10,
				eleHeight + 10);
		graph.dispose();

		// crop
		BufferedImage elementScreenshot = pageScreenShot.getSubimage(
				point.getX() - xLeftCropRectangleMove, point.getY()
						- yTopCropRectangleMove, eleWidth
						+ xRightCropRectangleMove + xLeftCropRectangleMove,
				eleHeight + yBottomCropRectangleMove + yTopCropRectangleMove);

		return elementScreenshot;
	}

	private static BufferedImage blurElementOnScreenShot(WebDriver driver,
			WebElement element) {
		Screenshot imageScreen = new AShot()
				.coordsProvider(new WebDriverCoordsProvider())
				.imageCropper(new IndentCropper() // overwriting cropper
						.addIndentFilter(new BlurFilter()))
				.takeScreenshot(driver, element); // adding filter for indent

		return imageScreen.getImage();
	}

}
