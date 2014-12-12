package framework.utils.parsers;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.thoughtworks.xstream.XStream;

import framework.utils.dto.IFrameworkDTO;

public class XMLFileReader extends FileReader {
	private Document document;
	private XStream xstream;
	private XPath xPath;

	public XMLFileReader(String fileLocation) {
		super(fileLocation);
		try {
			try {
				parsedInput = getStringFromInputStream(input);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			closeStream();
		} catch (IllegalStateException e) {
			// cannot create xstream
		}
	}

	public XMLFileReader() {

	}

	/**
	 * get java object from xml inputstream
	 * @param mainClazz	main class to parse
	 * @param clazzes	used classes in object to parse
	 * @return parsed object
	 */
	public <T extends IFrameworkDTO> T getObjectFromXmlDocumentFile(
			Class<T> mainClazz, Class<?>... clazzes) {
		buildDocumentFactoryForXML(parsedInput);
		return getObjectFromXmlDocument(parsedInput, mainClazz, clazzes);
	}

	/**
	 * get object from xml response
	 * @param response	response
	 * @param mainClazz	main class to parse
	 * @param clazzes	else class used in parsed object
	 * @return	parsed object
	 */
	public <T extends IFrameworkDTO> T getObjectFromXmlDocumentResponse(
			String response, Class<T> mainClazz, Class<?>... clazzes) {
		buildDocumentFactoryForXML(response);
		return getObjectFromXmlDocument(response, mainClazz, clazzes);
	}

	/**
	 * get java object object from xml
	 * @param inputDocument	xml document
	 * @param mainClazz	main class to parse
	 * @param clazzes	else classes used in parsed object
	 * @return	parsed object
	 */
	@SuppressWarnings("unchecked")
	private <T extends IFrameworkDTO> T getObjectFromXmlDocument(
			String inputDocument, Class<T> mainClazz, Class<?>... clazzes) {
		T data = null;
		for (Class<?> clazz : clazzes) {
			xstream.processAnnotations(clazz);
		}
		data = (T) xstream.fromXML(inputDocument);
		return data;
	}

	/**
	 * gets value of xml node by xpath from inputstream
	 * @param xpathExpression
	 * @return node value
	 */
	public String getXmlNodeValueByXpathFromFile(String xpathExpression) {
		buildDocumentFactoryForXML(parsedInput);
		return getXmlNodeValueByXpath(xpathExpression);
	}
	
	/**
	 * get value of xml node from response by xpath
	 * @param xpathExpression	xpath expression
	 * @param response	response
	 * @return	xml node value
	 */
	public String getXmlNodeValueByXpathFromResponse(String xpathExpression, String response) {
		buildDocumentFactoryForXML(response);
		return getXmlNodeValueByXpath(xpathExpression);
	}
	
	/**
	 * get node value from xml document by xpath
	 * @param xpathExpression	xpath expression
	 * @return	value of node
	 */
	private String getXmlNodeValueByXpath(String xpathExpression) {
		String xpathResult = null;
		try {
			xpathResult = xPath.compile(xpathExpression).evaluate(document);
		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return xpathResult;
	}

	/**
	 * build document factory for xml document
	 * @param inputDocument	input document
	 */
	private void buildDocumentFactoryForXML(String inputDocument) {
		DocumentBuilderFactory builderFactory = DocumentBuilderFactory
				.newInstance();
		DocumentBuilder builder = null;
		try {
			builder = builderFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		try {
			document = builder.parse(new InputSource(new StringReader(
					inputDocument)));
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		xstream = new XStream();
		xPath = XPathFactory.newInstance().newXPath();
	}
}
