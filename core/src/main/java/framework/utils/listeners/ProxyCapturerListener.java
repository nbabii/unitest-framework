package framework.utils.listeners;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import ru.yandex.qatools.allure.annotations.Attachment;
import ru.yandex.qatools.allure.annotations.Step;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

import framework.automation.driver.ExecutionEnvironment;
import framework.automation.driver.web.ProxyServerForBrowser;
import framework.automation.driver.web.WebBrowser;
import framework.utils.log.FrameworkLogger;
import framework.utils.log.LogFactory;

//attachment is not working yet
//https://code.google.com/p/harviewer/issues/detail?id=101&thanks=101&ts=1416586702
public class ProxyCapturerListener extends TestListenerAdapter {

	private static final FrameworkLogger LOG = LogFactory
			.getLogger(ProxyCapturerListener.class);

	private final String TEST_LOG_PATH = "target/test-logs";
	private final String PROXY_HAR_FOLDER_NAME = "Proxy HAR";
	private final String HAR_FILE_FORMAT = "har";

	@Attachment(value = "HAR got during test execution")
	private String createHAR(ITestResult result) throws IOException {
		String currentTestName = TestListenerUtil.getTestName(result);
		return "Check har here: http://www.softwareishard.com/har/viewer/" + "\n\n"  
				+ saveHarFileToLog(currentTestName,
						WebBrowser.getSetWebBrowser());
	}

	private String saveHarFileToLog(String testName,
			ExecutionEnvironment environment) throws IOException {
		String harNamePath = harNamePath(testName, environment);
		LOG.info("Har path for test: " + testName + " is: " + harNamePath);
		File harFile = null;
		try {
			harFile = new File(harNamePath);
			ProxyServerForBrowser.getServerInstance().getWrittenHar()
					.writeTo(harFile);

			LOG.info("Successfully wrote har to {}", harFile.getAbsolutePath());
		} catch (IOException e) {
			LOG.error("Cannot write har to path: ", harNamePath);
		}

		return Files.toString(harFile, Charsets.UTF_8);
	}

	private String harNamePath(String testName, ExecutionEnvironment environment) {
		SimpleDateFormat formater = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");
		String imageFileName = testName + "_"
				+ formater.format(Calendar.getInstance().getTime()) + "_on_"
				+ environment.getCurrentExecutionEnvironmentName() + "."
				+ HAR_FILE_FORMAT;
		String filePath = getHarStoreDirectoryForSeparateTestSuite() + "/"
				+ imageFileName;

		return filePath;
	}

	private String getHarStoreDirectoryForSeparateTestSuite() {
		String currentTestLogPath = TEST_LOG_PATH + "/"
				+ System.getProperty("logTestFolder").toString();
		String harFolderName = currentTestLogPath + "/" + PROXY_HAR_FOLDER_NAME;

		File harFolder = new File(harFolderName);
		if (harFolder.exists() && harFolder.isDirectory()) {
			LOG.debug("Har folder for current test is: " + harFolder);
			return harFolderName;
		} else {
			LOG.debug("Creating test logs har folder for current test for path: "
					+ harFolderName);
			new File(harFolderName).mkdirs();
			LOG.debug("Har path for current test is: " + harFolderName);
			return harFolderName;
		}
	}

	@Step("Proxy HAR during test exectuion")
	@Override
	public void onTestFailure(ITestResult result) {
		if (WebBrowser.getSetWebBrowser().isProxyEnabled()) {
			try {
				createHAR(result);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Step("Proxy HAR during test exectuion")
	@Override
	public void onTestSuccess(ITestResult result) {
		if (WebBrowser.getSetWebBrowser().isProxyEnabled()) {
			try {
				createHAR(result);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Step("Proxy HAR during test exectuion")
	@Override
	public void onTestSkipped(ITestResult result) {
		if (WebBrowser.getSetWebBrowser().isProxyEnabled()) {
			try {
				createHAR(result);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Step("Proxy HAR during test exectuion")
	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		if (WebBrowser.getSetWebBrowser().isProxyEnabled()) {
			try {
				createHAR(result);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
