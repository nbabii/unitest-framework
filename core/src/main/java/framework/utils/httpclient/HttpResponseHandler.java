package framework.utils.httpclient;

import java.util.HashMap;
import java.util.Map;

import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Headers;
import com.jayway.restassured.response.Response;

import framework.dto.IFrameworkDTO;
import framework.utils.parsers.JsonFileReader;
import framework.utils.parsers.XMLFileReader;

public class HttpResponseHandler {
	
	public static String getHttpResponseBody(Response httpCall) {
		return httpCall.getBody().asString();
	}

	public static int getHttpResponseStatusCode(Response httpCall) {
		return httpCall.getStatusCode();
	}

	public static <T extends IFrameworkDTO> T getJsonResponseAsObject(
			Response httpCall, Class<T> clazz)
			throws IncorrectContentTypeException {
		if (getContentType(httpCall).contains(ContentType.JSON.toString())) {
			JsonFileReader jsonFileReader = new JsonFileReader();
			return jsonFileReader.readJsonToObjectFromResponse(clazz,
					getHttpResponseBody(httpCall));
		} else
			throw new IncorrectContentTypeException(
					"content type is not json content type, it is: "
							+ getContentType(httpCall));
	}

	public static <T extends IFrameworkDTO> T getXmlResponseAsObject(
			Response httpCall, Class<T> mainClazz, Class<?>... clazzes)
			throws IncorrectContentTypeException {
		if (getContentType(httpCall).contains(ContentType.XML.toString())) {
			XMLFileReader xmlFileReader = new XMLFileReader();
			return xmlFileReader.getObjectFromXmlDocumentResponse(
					getHttpResponseBody(httpCall), mainClazz, clazzes);
		} else
			throw new IncorrectContentTypeException(
					"content type is not xml content type, it is: "
							+ getContentType(httpCall));
	}

	public static Object getJsonFieldSingleValue(Response httpCall,
			String singleProperty) throws Exception {
		if (getContentType(httpCall).contains(ContentType.JSON.toString())) {
			JsonFileReader jsonFileReader = new JsonFileReader();
			return jsonFileReader.readJsonResponseSingleProperty(
					singleProperty, getHttpResponseBody(httpCall));
		} else
			throw new IncorrectContentTypeException(
					"content type is not json content type, it is: "
							+ getContentType(httpCall));
	}

	public static String getXmlNodeSingleValue(Response httpCall,
			String xpathExpression) throws IncorrectContentTypeException {

		if (getContentType(httpCall).contains(ContentType.XML.toString())) {
			XMLFileReader xmlFileReader = new XMLFileReader();
			return xmlFileReader.getXmlNodeValueByXpathFromResponse(
					xpathExpression, getHttpResponseBody(httpCall));
		} else
			throw new IncorrectContentTypeException(
					"content type is not xml content type, it is: "
							+ getContentType(httpCall));
	}

	public static String getContentType(Response httpCall) {
		return httpCall.getContentType();
	}

	public static Map<String, String> getCookies(Response httpCall) {
		return httpCall.getCookies();
	}

	public static Map<String, String> getHeaders(Response httpCall) {
		Headers headers = httpCall.getHeaders();
		Map<String, String> headersMap = new HashMap<String, String>();
		while (headers.iterator().hasNext()) {
			headersMap.put(headers.iterator().next().getName(), headers
					.iterator().next().getValue());
		}
		return headersMap;
	}

}
