package app.java.agentapp.report;

import java.awt.Color;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import app.java.agentapp.dto.MonitoringDTO;
import app.java.agentapp.dto.NumberOfClicksDTO;

public class ReportPDFExporter {

	Collection<MonitoringDTO> monitoring;
	
	public ReportPDFExporter(Collection<MonitoringDTO> monitoring) {
		this.monitoring = monitoring;
	}
	
	private void writeTableHeader(PdfPTable table) {
		PdfPCell cell = new PdfPCell();
		cell.setBackgroundColor(Color.LIGHT_GRAY);
		cell.setPadding(5);
		
		cell.setPhrase(new Phrase("Content type"));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("Campaign type"));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("Category name"));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("Name"));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("Number likes"));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("Number dislikes"));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("Number comments"));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("Number clicks"));
		table.addCell(cell);
	}
	
	private void writeTableData(PdfPTable table) {
		for(MonitoringDTO m : monitoring) {
			table.addCell(m.contentType);
			table.addCell(m.campaignType);
			table.addCell(m.categoryName);
			table.addCell(m.name);
			table.addCell(String.valueOf(m.numberLikes));
			table.addCell(String.valueOf(m.numberDislikes));
			table.addCell(String.valueOf(m.numberComments));
			table.addCell(String.valueOf(m.numberClick));
		}
	}
	
	private void writeTableHeader2(PdfPTable table) {
		PdfPCell cell = new PdfPCell();
		cell.setBackgroundColor(Color.LIGHT_GRAY);
		cell.setPadding(5);
		
		cell.setPhrase(new Phrase("Campaign name"));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("Owner type"));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("Owner username"));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("Number clicks"));
		table.addCell(cell);
	}
	
	private void writeTableData2(PdfPTable table) {
		for(MonitoringDTO m : monitoring) {
			for(NumberOfClicksDTO n : m.numberOfClicksDTOs) {
			table.addCell(m.name);
			table.addCell(n.ownerType);
			table.addCell(n.ownerUsername);
			table.addCell(String.valueOf(n.numberOfClicks));
			}
		}
	}
	
	public void export(HttpServletResponse response) throws DocumentException, IOException {
		Document document = new Document(PageSize.A4);
		
		PdfWriter.getInstance(document, response.getOutputStream());
		 
		document.open();
		
		Paragraph title = new Paragraph("Monitoring of agent campaigns"); 
		title.setAlignment(Paragraph.ALIGN_CENTER);
		document.add(title);
		
		PdfPTable table = new PdfPTable(8);
		table.setWidthPercentage(100);
		table.setSpacingBefore(15);
		
		writeTableHeader(table);
		writeTableData(table);
		
		document.add(table);
		
		PdfPTable table2 = new PdfPTable(4);
		table2.setWidthPercentage(100);
		table2.setSpacingBefore(15);
		
		writeTableHeader2(table2);
		writeTableData2(table2);
		
		document.add(table2);
		
		document.close();
	}
}
