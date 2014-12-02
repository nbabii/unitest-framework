package framework.utils.listeners;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.io.FileUtils;
import org.testng.IExecutionListener;

import framework.utils.log.FrameworkLogger;
import framework.utils.log.LogFactory;

public class ConfigurationsToReportListener implements IExecutionListener {

	private static final FrameworkLogger LOG = LogFactory
			.getLogger(ConfigurationsToReportListener.class);

	private String xmlReporterFolderTextPath = System
			.getProperty("xml-report-folder");
	private String environmentConfigurationFileTextPath = System
			.getProperty("environment.configuration");

	@Override
	public void onExecutionStart() {
		LOG.info("Initialized Test Execution Listener.");
		LOG.info("Environment configurations will be copied to xml report folder after execution: "
				+ xmlReporterFolderTextPath);
		LOG.info("Environment configurations file is: "
				+ environmentConfigurationFileTextPath);
	}

	@Override
	public void onExecutionFinish() {
		copyConfigurationsForReport();
	}

	private void copyConfigurationsForReport() {
		String errorMessage;
		Path xmlReporterFolderPath = Paths.get(xmlReporterFolderTextPath);
		if (Files.exists(xmlReporterFolderPath)) {
			if (new File(environmentConfigurationFileTextPath).exists()) {
				LOG.info("Copying configurations file...");
				try {
					FileUtils.copyFileToDirectory(new File(
							environmentConfigurationFileTextPath), new File(
							xmlReporterFolderTextPath));
					LOG.info("Configuration file was copied.");
				} catch (IOException e) {
					LOG.error("Copying configurations file failed. Environment configurations file path is: "
							+ environmentConfigurationFileTextPath
							+ ". Xml report folder path is: "
							+ xmlReporterFolderTextPath);
				}
			} else {
				errorMessage = "Environment configuration file "
						+ environmentConfigurationFileTextPath
						+ " doesn't exist";
				LOG.error(errorMessage);
				throw new RuntimeException(errorMessage);
			}
		} else {
			errorMessage = "Path for xml report files: "
					+ xmlReporterFolderPath.toAbsolutePath() + " doesn't exist";
			LOG.error(errorMessage);
			throw new RuntimeException(errorMessage);
		}
	}

}
