package framework.utils.httpclient;

import java.util.HashMap;
import java.util.Map;

import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Headers;
import com.jayway.restassured.response.Response;

import framework.utils.dto.IFrameworkDTO;
import framework.utils.parsers.JsonFileReader;
import framework.utils.parsers.XMLFileReader;

/**
 * Class for handling Http Responses
 * @author Taras.Lytvyn
 *
 */
public class HttpResponseHandler {
	
	/**
	 * get body of http response
	 * @param httpCall	response
	 * @return	response body
	 */
	public static String getHttpResponseBody(Response httpCall) {
		return httpCall.getBody().asString();
	}

	/**
	 * get http response status code
	 * @param httpCall	response
	 * @return	http status code
	 */
	public static int getHttpResponseStatusCode(Response httpCall) {
		return httpCall.getStatusCode();
	}

	/**
	 * get json response as object
	 * @param httpCall	response
	 * @param clazz	class to map
	 * @return	mapped object from response body
	 * @throws IncorrectContentTypeException
	 */
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

	/**
	 * get xml response as object
	 * @param httpCall	response
	 * @param mainClazz	xml mapping object parent class	
	 * @param clazzes	other xml mapping classes
	 * @return	mapped object from response body
	 * @throws IncorrectContentTypeException
	 */
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

	/**
	 * get single value of json response (if there several values with same key we can cast to array)
	 * @param httpCall	response
	 * @param singleProperty	property
	 * @return	property value
	 * @throws Exception
	 */
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

	/**
	 * get single value of xml node by xpath
	 * @param httpCall	response
	 * @param xpathExpression	xpath
	 * @return	property value for element from xpath
	 * @throws IncorrectContentTypeException
	 */
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

	/**
	 * get response content type
	 * @param httpCall
	 * @return content type text
	 */
	public static String getContentType(Response httpCall) {
		return httpCall.getContentType();
	}

	/**
	 * get cookies
	 * @param httpCall	response
	 * @return	cookies map
	 */
	public static Map<String, String> getCookies(Response httpCall) {
		return httpCall.getCookies();
	}

	/**
	 * get headers
	 * @param httpCall	response 
	 * @return	headers map
	 */
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
