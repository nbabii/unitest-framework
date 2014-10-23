package framework.utils.tests.testbase;

import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import framework.test.TestBase;
import framework.utils.log.FrameworkLogger;
import framework.utils.log.LogFactory;
import framework.utils.parsers.CSVFileReader;
import framework.utils.parsers.JsonFileReader;
import framework.utils.parsers.PropertyFileReader;
import framework.utils.parsers.XMLFileReader;

public class FileReaderTestBase extends TestBase{
	
	private static final FrameworkLogger LOG = LogFactory.getLogger(JsonFileReader.class); 

	private final String propertiesFileLocation = "/file.reader.test.resources/test-properties.properties";
	private final String jsonFileLocation = "/file.reader.test.resources/driver.json";
	private final String xmlFileLocation = "/file.reader.test.resources/data.xml";
	private final String csvFileLocation = "/file.reader.test.resources/employees.csv";

	protected PropertyFileReader propertyFileReader;
	protected JsonFileReader jsonFileReader;
	protected XMLFileReader xmlFileReader;
	protected CSVFileReader csvFileReader;

	@BeforeClass(dependsOnMethods = {"setupTestBase"})
	public void setupFileReaderTests(ITestContext context) throws Exception {
		LOG.info("Before Suite");
		propertyFileReader = new PropertyFileReader(propertiesFileLocation);
		jsonFileReader = new JsonFileReader(jsonFileLocation);
		xmlFileReader = new XMLFileReader(xmlFileLocation);
		csvFileReader = new CSVFileReader(csvFileLocation);
	}
	
	@AfterClass(dependsOnMethods = {"tearDownTestBase"})
	public void tearDownFileReaderTests(ITestContext context) throws Exception {
		LOG.info("After Suite");
	}

}
