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

	public <T extends IFrameworkDTO> T getObjectFromXmlDocumentFile(
			Class<T> mainClazz, Class<?>... clazzes) {
		buildDocumentFactoryForXML(parsedInput);
		return getObjectFromXmlDocument(parsedInput, mainClazz, clazzes);
	}

	public <T extends IFrameworkDTO> T getObjectFromXmlDocumentResponse(
			String response, Class<T> mainClazz, Class<?>... clazzes) {
		buildDocumentFactoryForXML(response);
		return getObjectFromXmlDocument(response, mainClazz, clazzes);
	}

	@SuppressWarnings("unchecked")
	private <T extends IFrameworkDTO> T getObjectFromXmlDocument(
			String inputDocuemnt, Class<T> mainClazz, Class<?>... clazzes) {
		T data = null;
		for (Class<?> clazz : clazzes) {
			xstream.processAnnotations(clazz);
		}
		data = (T) xstream.fromXML(inputDocuemnt);
		return data;
	}

	public String getXmlNodeValueByXpathFromFile(String xpathExpression) {
		buildDocumentFactoryForXML(parsedInput);
		return getXmlNodeValueByXpath(xpathExpression);
	}
	
	public String getXmlNodeValueByXpathFromResponse(String xpathExpression, String response) {
		buildDocumentFactoryForXML(response);
		return getXmlNodeValueByXpath(xpathExpression);
	}
	
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
