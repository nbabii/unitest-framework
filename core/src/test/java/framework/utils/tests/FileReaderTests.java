package framework.utils.tests;

import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.JsonNode;

import framework.utils.tests.dto.parsers.csv.Employee;
import framework.utils.tests.dto.parsers.json.Drive;
import framework.utils.tests.dto.parsers.xml.Ban;
import framework.utils.tests.dto.parsers.xml.Data;
import framework.utils.tests.dto.parsers.xml.Person;
import framework.utils.tests.testbase.FileReaderTestBase;

public class FileReaderTests extends FileReaderTestBase {

	@Test
	public void readPropertyValueFromFile() throws Exception {
		Assert.assertEquals("value1",
				propertyFileReader.getPropertyValue("property1"),
				"property1 value is not value1");
		Assert.assertEquals("value2",
				propertyFileReader.getPropertyValue("property2"),
				"property1 value is not value2");
		Assert.assertEquals("value3",
				propertyFileReader.getPropertyValue("property3"),
				"property1 value is not value3");
	}

	@Test
	public void readPropertysToMap() throws Exception {
		Assert.assertEquals("value1",
				propertyFileReader.getPropertyValue("property1"),
				"property1 value is not value1");
		Assert.assertEquals(3, propertyFileReader.readPropertiesToMap()
				.entrySet().size(), "Map size is not equal to 3");
	}

	@Test
	public void readJsonToDTOTest() throws Exception {
		Drive drive = jsonFileReader.readJsonToObjectFromFile(Drive.class);
		Assert.assertEquals(16, drive.getDrivers().get(0).getAge(),
				"Json parsing failed for item 1");
		Assert.assertEquals(17, drive.getDrivers().get(1).getAge(),
				"Json parsing failed for item 2");

	}

	@SuppressWarnings("unchecked")
	@Test
	public void readJsonSinglePropertyTest() throws Exception {
		List<JsonNode> cars = (List<JsonNode>) jsonFileReader
				.readJsonFileSingleProperty("car");
		Assert.assertEquals("Peugeout1111", cars.get(1).get("name").asText(),
				"Json parsing failed for property car");

		String secondName = (String) jsonFileReader
				.readJsonFileSingleProperty("second name");
		Assert.assertEquals("Lyyyttt", secondName,
				"Json parsing failed for property name");
	}

	@Test
	public void readXMLToObjectTest() throws Exception {
		Data data = xmlFileReader.getObjectFromXmlDocumentFile(Data.class,
				Data.class, Ban.class, Person.class);
		Assert.assertEquals("JOHN", data.getBans().get(0).getPerson()
				.getFirstName(), "XML parsing failed for property first name");
	}

	@Test
	public void readXMLSinglePropertyTest() throws Exception {
		Assert.assertEquals("2012-03-10", xmlFileReader
				.getXmlNodeValueByXpathFromFile("DATA/BAN[2]/UPDATED_AT"),
				"XML parsing failed for xpath expression");
		Assert.assertEquals(
				"ANNA",
				xmlFileReader
						.getXmlNodeValueByXpathFromFile("DATA/BAN[2]/TROUBLEMAKER/NAME1"),
				"XML parsing failed for xpath expression");
	}

	@Test
	public void readCSVToObjectTest() throws Exception {
		List<Employee> employee = csvFileReader
				.getObjectFromCSV(Employee.class);
		Assert.assertEquals("Manager", employee.get(1).getRole(),
				"CSV parsing failed for employee 2");
	}

	@Test
	public void readCSVToListOfMapsTest() throws Exception {
		List<Map<String, String>> employees = csvFileReader
				.getListOfMapsFromCSV();
		Assert.assertEquals("Manager", employees.get(1).get("Role"),
				"CSV parsing failed for employee 2");
	}
}
