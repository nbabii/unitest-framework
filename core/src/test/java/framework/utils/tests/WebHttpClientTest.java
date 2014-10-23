package framework.utils.tests;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import framework.test.TestBase;
import framework.utils.httpclient.HttpClient;
import framework.utils.httpclient.HttpResponseHandler;
import framework.utils.log.FrameworkLogger;
import framework.utils.log.LogFactory;
import framework.utils.tests.dto.httpclient.Elevation;
import framework.utils.tests.dto.httpclient.Location;
import framework.utils.tests.dto.httpclient.Result;

public class WebHttpClientTest extends TestBase{
	
	private static final FrameworkLogger LOG = LogFactory.getLogger(WebHttpClientTest.class); 

	HttpClient httpClient1;
	HttpClient httpClient2;

	@BeforeClass(dependsOnMethods = {"setupTestBase"})
	public void setup() throws Exception {
		LOG.info("Before Suite");
		httpClient1 = new HttpClient("https://maps.googleapis.com",
				"/maps/api/elevation");

		httpClient2 = new HttpClient("https://maps.googleapis.com", "/maps");
	}

	@AfterClass(dependsOnMethods = {"tearDownTestBase"})
	public void tearDown() throws Exception {
		LOG.info("After Suite");
		httpClient1.resetClientProps();
		httpClient2.resetClientProps();
	}

	@SuppressWarnings("unchecked")
	@Test
	public void getHttpMethodAndJsonSingleFieldParserTest() throws Exception {
		Map<String, String> params1 = new HashMap<String, String>();
		params1.put("locations", "39.7391536,-104.9847034");

		Assert.assertEquals(
				"4.771975994110107",
				(String) HttpResponseHandler.getJsonFieldSingleValue(
						httpClient1.callHttpGet("/json", params1), "resolution"));

		Map<String, String> params2 = new HashMap<String, String>();
		params2.put("locations",
				"39.7391536,-104.9847034|36.455556,-116.866667");
		List<String> resolutions = (List<String>) HttpResponseHandler
				.getJsonFieldSingleValue(
						httpClient1.callHttpGet("/json", params2), "resolution");

		Assert.assertEquals("19.08790397644043", resolutions.get(1));
	}

	@Test
	public void getHttpMethodAndJsonToObjectMapperTest() throws Exception {
		Map<String, String> params3 = new HashMap<String, String>();
		params3.put("path", "36.578581,-118.291994|36.23998,-116.83171");
		params3.put("samples", "3");

		Elevation elevation = HttpResponseHandler.getJsonResponseAsObject(
				httpClient1.callHttpGet("/json", params3), Elevation.class);

		Assert.assertEquals(-116.831710, elevation.getResults().get(2)
				.getLocation().getLng());
	}

	@Test
	public void getHttpMethodAndXMLToObjectMapperTest() throws Exception {
		Map<String, String> params4 = new HashMap<String, String>();
		params4.put("path", "36.578581,-118.291994|36.23998,-116.83171");
		params4.put("samples", "3");

		Elevation elevation = HttpResponseHandler.getXmlResponseAsObject(
				httpClient2.callHttpGet("/api/elevation/xml", params4),
				Elevation.class, Elevation.class, Result.class, Location.class);

		Assert.assertEquals(19.0879040, elevation.getResults().get(1)
				.getResolution());

	}

	@Test
	public void getHttpMethodAndXMLSingleValueTest() throws Exception {
		Map<String, String> params5 = new HashMap<String, String>();
		params5.put("path", "36.578581,-118.291994|36.23998,-116.83171");
		params5.put("samples", "3");

		Assert.assertEquals("36.2399800", HttpResponseHandler
				.getXmlNodeSingleValue(
						httpClient2.callHttpGet("/api/elevation/xml", params5),
						"ElevationResponse/result[3]/location/lat"));

	}

}
