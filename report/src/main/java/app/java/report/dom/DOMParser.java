package app.java.report.dom;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import static org.apache.xerces.jaxp.JAXPConstants.JAXP_SCHEMA_LANGUAGE;
import static org.apache.xerces.jaxp.JAXPConstants.W3C_XML_SCHEMA;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;;

@Component
public class DOMParser {
	
	private DocumentBuilderFactory factory;
	private Document document;
	
	public DOMParser() {
		factory = DocumentBuilderFactory.newInstance();
		
		factory.setNamespaceAware(true);
		factory.setIgnoringComments(true);
		factory.setIgnoringElementContentWhitespace(true);
		
		factory.setAttribute(JAXP_SCHEMA_LANGUAGE, W3C_XML_SCHEMA);
		
	}
	
	public Document buildDocumentFromText(String text) throws SAXException, IOException, ParserConfigurationException {
		DocumentBuilder builder = factory.newDocumentBuilder();
		return builder.parse(new InputSource(new ByteArrayInputStream(text.getBytes("UTF-8"))));
	}
	
	public Document buildDocumentFromFile(String filePath) throws SAXException, IOException, ParserConfigurationException {
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc =  builder.parse(new File(filePath));
		return doc;
	}
	
	public String getDocumentAsString(Document document) throws TransformerException {
		StringWriter sw = new StringWriter();
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer transformer = tf.newTransformer();
		
		transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
		transformer.setOutputProperty(OutputKeys.METHOD,"xml");
		transformer.setOutputProperty(OutputKeys.INDENT,"yes");
		transformer.setOutputProperty(OutputKeys.ENCODING,"UTF-8");

		transformer.transform(new DOMSource(document), new StreamResult(sw));
		
		return sw.toString();
		
	}

}
