package app.java.report.jaxb;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.springframework.stereotype.Component;

@Component
public class JaxB {
	
	public <T> T unmarshall(Class genericClass, String text) throws JAXBException {
		JAXBContext context = JAXBContext.newInstance(genericClass);
		Unmarshaller unmarshaller = context.createUnmarshaller();
		T createdObject = (T) unmarshaller.unmarshal(new StringReader(text));
		return createdObject;
	}
	
	public <T> String marshall(Class genericClass,T objecToMarshall) throws JAXBException {
		JAXBContext context = JAXBContext.newInstance(genericClass);
		Marshaller marshaller = context.createMarshaller();
		StringWriter sw = new StringWriter();
		marshaller.marshal(objecToMarshall, sw);
		return sw.toString();
	}
	

}