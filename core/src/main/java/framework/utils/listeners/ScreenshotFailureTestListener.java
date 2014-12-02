package framework.utils.listeners;

import java.awt.AWTException;
import java.awt.HeadlessException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.imageio.ImageIO;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.Augmenter;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import ru.yandex.qatools.allure.annotations.Attachment;
import ru.yandex.qatools.allure.annotations.Step;
import framework.automation.driver.ExecutionEnvironment;
import framework.automation.driver.mobile.MobileDriverFactory;
import framework.automation.driver.mobile.MobilePlatform;
import framework.automation.driver.web.WebBrowser;
import framework.automation.driver.web.WebDriverFactory;
import framework.utils.log.FrameworkLogger;
import framework.utils.log.LogFactory;

public class ScreenshotFailureTestListener extends TestListenerAdapter {

	private static final FrameworkLogger LOG = LogFactory
			.getLogger(ScreenshotFailureTestListener.class);

	private final String TEST_LOG_PATH = "target/test-logs";
	private final String SCREENSHOT_FOLDER_NAME = "Screenshots for test failures";
	private final String SCREENSHOT_FILE_FORMAT = "png";

	private enum ScreenShot {
		DesktopScreen, BrowserScreen, MobileScreen;
	}

	public ScreenshotFailureTestListener() {
		LOG.info("Initialized Screenshot Failure Test Listener.");
		LOG.info("Screehshots that will be created on test failures are desktop and web browser and mobile screen screenshots.");
		LOG.info("Screehshots extension is: " + "." + SCREENSHOT_FILE_FORMAT);
		LOG.info("Test log path is: " + TEST_LOG_PATH);
	}

	@Attachment(value = "Desktop screenshot after test failed", type = "image/png")
	private byte[] createDesktopScreenShot(ITestResult result) {
		String currentTestName = TestListenerUtil.getTestName(result);
		BufferedImage image = null;
		try {
			LOG.debug("Writing out desktop screenshot on {} test failure",
					currentTestName);
			image = new Robot().createScreenCapture(new Rectangle(Toolkit
					.getDefaultToolkit().getScreenSize()));
			LOG.info("Captured desktop screenshot for test: " + currentTestName);
		} catch (HeadlessException e) {
			LOG.error("Problem with screen detection on test: "
					+ currentTestName, e);
		} catch (AWTException e) {
			LOG.error("AWT error occured for test: " + currentTestName, e);
		}
		saveScreenshotFileToLog(image, currentTestName,
				ScreenShot.DesktopScreen.name(), WebBrowser.getSetWebBrowser());

		return TestListenerUtil.getByteArrayFromImage(image,
				SCREENSHOT_FILE_FORMAT);
	}

	@Attachment(value = "Browser screenshot after test failed", type = "image/png")
	private byte[] createWebBrowserScreenShot(ITestResult result) {
		String currentTestName = TestListenerUtil.getTestName(result);
		BufferedImage image = null;
		LOG.debug("Writing out web browser screenshot on {} test failure",
				currentTestName);
		WebDriver driver = WebDriverFactory.getCreatedWebDriver();
		try {
			// we add this, because sometimes webdriver takes screenshots little
			// bit earlier then needed
			Thread.sleep(1500);
		} catch (InterruptedException e1) {
			LOG.error(e1.getMessage());
		}
		File imageFile = ((TakesScreenshot) driver)
				.getScreenshotAs(OutputType.FILE);
		try {
			image = ImageIO.read(imageFile);
		} catch (IOException e) {
			LOG.error(
					"Cannot read browser screenshot created file and convert it to Buffered Image object for test: "
							+ currentTestName, e);
		}
		saveScreenshotFileToLog(image, currentTestName,
				ScreenShot.BrowserScreen.name(), WebBrowser.getSetWebBrowser());

		return TestListenerUtil.getByteArrayFromImage(image,
				SCREENSHOT_FILE_FORMAT);
	}

	@Attachment(value = "Mobile screenshot after test failed", type = "image/png")
	private byte[] createMobileScreenShot(ITestResult result) {
		String currentTestName = TestListenerUtil.getTestName(result);
		BufferedImage image = null;
		LOG.debug("Writing out mobile screenshot on {} test failure",
				currentTestName);
		WebDriver augmentedDriver = new Augmenter().augment(MobileDriverFactory
				.getCreatedAppiumDriver());
		try {
			// we add this, because sometimes webdriver takes screenshots little
			// bit earlier then needed
			Thread.sleep(1500);
		} catch (InterruptedException e1) {
			LOG.error(e1.getMessage());
		}
		File imageFile = ((TakesScreenshot) augmentedDriver)
				.getScreenshotAs(OutputType.FILE);
		try {
			image = ImageIO.read(imageFile);
		} catch (IOException e) {
			LOG.error(
					"Cannot read mobile screenshot created file and convert it to Buffered Image object for test: "
							+ currentTestName, e);
		}
		saveScreenshotFileToLog(image, currentTestName,
				ScreenShot.MobileScreen.name(),
				MobilePlatform.getSetMobilePlatform());

		return TestListenerUtil.getByteArrayFromImage(image,
				SCREENSHOT_FILE_FORMAT);
	}

	private void saveScreenshotFileToLog(BufferedImage image, String testName,
			String screenshotType, ExecutionEnvironment environment) {
		String screenShotNamePath = screenShotNamePath(testName,
				screenshotType, environment);
		LOG.info("Screenshot path for test: " + testName + " is: "
				+ screenShotNamePath);
		try {
			File screenShotFile = new File(screenShotNamePath);
			ImageIO.write(image, SCREENSHOT_FILE_FORMAT, screenShotFile);

			LOG.info("Successfully wrote " + screenshotType
					+ " screenshot to {}", screenShotFile.getAbsolutePath());
		} catch (IOException e) {
			LOG.error("Cannot write screenshot to path: ", screenShotNamePath);
		}
	}

	private String screenShotNamePath(String testName, String screenShotType,
			ExecutionEnvironment environment) {
		SimpleDateFormat formater = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");
		String imageFileName = testName + "_"
				+ formater.format(Calendar.getInstance().getTime()) + "_at_"
				+ screenShotType + "_on_"
				+ environment.getCurrentExecutionEnvironmentName() + "."
				+ SCREENSHOT_FILE_FORMAT;
		String filePath = getScreenShotDirectoryForSeparateTestSuite() + "/"
				+ imageFileName;

		return filePath;
	}

	private String getScreenShotDirectoryForSeparateTestSuite() {
		String currentTestLogPath = TEST_LOG_PATH + "/"
				+ System.getProperty("logTestFolder").toString();
		String screenShotFolderName = currentTestLogPath + "/"
				+ SCREENSHOT_FOLDER_NAME;

		File screenShotFolder = new File(screenShotFolderName);
		if (screenShotFolder.exists() && screenShotFolder.isDirectory()) {
			LOG.debug("Screenshot path for current test is: "
					+ screenShotFolderName);
			return screenShotFolderName;
		} else {
			LOG.debug("Creating test logs screenshot folder for current test for path: "
					+ screenShotFolderName);
			new File(screenShotFolderName).mkdirs();
			LOG.debug("Screenshot path for current test is: "
					+ screenShotFolderName);
			return screenShotFolderName;
		}
	}

	@Step("Screenshots on Test Failure:")
	@Override
	public void onTestFailure(ITestResult result) {
		String testType = result.getTestContext().getCurrentXmlTest()
				.getParameter("testType");
		LOG.debug("Test type is: " + testType);
		if (testType.equals("browser")) {
			createDesktopScreenShot(result);
			createWebBrowserScreenShot(result);
		} else if (testType.equals("mobile")) {
			createMobileScreenShot(result);
		} else {
			// Do nothing
		}
	}

}
