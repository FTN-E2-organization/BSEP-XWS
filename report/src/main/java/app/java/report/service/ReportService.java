package app.java.report.service;

import org.springframework.stereotype.Service;

import app.java.report.dom.DOMParser;
import app.java.report.dto.MonitoringDTO;
import app.java.report.dto.NumberOfClicksDTO;
import app.java.report.dto.XmlDTO;
import app.java.report.jaxb.JaxB;
import app.java.report.repository.ReportRepository;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XMLResource;

@Service
public class ReportService {

	private DOMParser domParser;
	private JaxB jaxB;
	private ReportRepository reportRepository;
	
	public ReportService(DOMParser domParser, JaxB jaxB, ReportRepository reportRepository) {
		this.domParser = domParser;
		this.jaxB = jaxB;
		this.reportRepository = reportRepository;
	}
	
	public String playWithXML(XmlDTO dto) throws Exception {
		Document document = domParser.buildDocumentFromText(dto.getText());
		NodeList profesori = document.getElementsByTagName("profesor");

		for (int i = 0; i < profesori.getLength(); i++) {
			Element profesor = (Element) profesori.item(i);
			profesor.setAttribute("id", "prof" + i);

			Element titula = document.createElement("Titila");
			titula.appendChild(document.createTextNode("Profesor"));
			profesor.appendChild(titula);
		}

		return domParser.getDocumentAsString(document);

	}

	
	public void saveFileFromString(List<MonitoringDTO> monitoring) throws Exception {
		String text = "<monitoring>";
		for(MonitoringDTO m : monitoring) {
			text += "<campaing><content>" + m.contentType + "</content><type>" + m.campaignType +"</type><category>" + m.categoryName +"</category><name>"+ m.name +"</name><like>"+ String.valueOf(m.numberLikes) + "</like><dislike>"+ String.valueOf(m.numberDislikes) +"</dislike><comment>"+ String.valueOf(m.numberComments) +"</comment>";
			for(NumberOfClicksDTO n : m.numberOfClicksDTOs) {
				text += "<click><number>"+ n.numberOfClicks + "</number><ownertype>" + n.ownerType + "</ownertype><ownername>" + n.ownerUsername + "</ownername></click>";
			}
			text += "</campaing>";
		}
		text += "</monitoring>";
		
		reportRepository.saveReport(text);
	}
	
	public XMLResource getFromFile()throws Exception {
		XMLResource res =  reportRepository.getReport();
		if (res == null) { 
            System.out.println("document not found!"); 
		} else { 
            System.out.println(res.getContent()); 
    } 
		return reportRepository.getReport();
	}
	
}
