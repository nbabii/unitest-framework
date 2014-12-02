package framework.utils.listeners;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

import org.testng.ITestResult;

import ru.yandex.qatools.allure.annotations.Attachment;
import framework.utils.log.FrameworkLogger;
import framework.utils.log.LogFactory;

public class TestListenerUtil {

	private static final FrameworkLogger LOG = LogFactory
			.getLogger(TestListenerUtil.class);

	private final static String LOG_FILE_EXTENSION = "log";

	static String getFullTestName(ITestResult result) {
		String testName = result.getTestClass().getRealClass().getName() + "."
				+ getTestName(result);
		if (result.getParameters() != null && result.getParameters().length > 0) {
			testName += result.getParameters()[0];
		}

		return testName;
	}
	
	static String getTestName(ITestResult result) {
		String testName = result.getMethod().getMethodName();
		return testName;
	}

	@Attachment(value = "Test log during execution for \"{1}\"", type="text/plain")
	static String attachLogToReporter(String testClassName, String testName) {
		String currentLogTextPath = System.getProperty("test-logs") + "/"
				+ testClassName + "/" + testName + "." + LOG_FILE_EXTENSION;
		String stringFileContentToAttach = null;
		Path currentLogPath = Paths.get(currentLogTextPath);
		if (Files.exists(currentLogPath)) {
			byte[] encodedFileToByte;
			try {
				encodedFileToByte = Files.readAllBytes(currentLogPath);
			} catch (IOException e) {
				String errorMessage = "Fail to read log file from path "
						+ currentLogPath.toString();
				LOG.error(errorMessage);
				throw new RuntimeException(errorMessage);
			}
			stringFileContentToAttach = new String(encodedFileToByte,
					StandardCharsets.UTF_8);
		} else {
			String errorMessage = "Fail to attach log to report. Log file for "
					+ testClassName + "/" + testName + " doesn't exist.";
			LOG.error(errorMessage);
			throw new RuntimeException(errorMessage);
		}
		return stringFileContentToAttach;
	}
	
	static byte[] getByteArrayFromImage(BufferedImage image, String format) {
		//saveFileToLog(image, testName, screenshotType);
		LOG.debug("Converting screenshot to byte array for report attachment");
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			ImageIO.write(image, format, baos);
			LOG.info("Successfully wrote screenshot to byte array output stream");
		} catch (IOException e) {
			LOG.error("Cannot write screenshot to ByteArrayOutputStream");
		}
		byte[] imageBytes = baos.toByteArray();

		if (imageBytes.length == 0) {
			String errorMessage = "Converted byte array for screenshot is empty.";
			LOG.error(errorMessage);
			throw new RuntimeException(errorMessage);
		}
		LOG.info("Converted image screenshot to byte array. Byte array size is: "
				+ imageBytes.length);
		return imageBytes;
	}

}
