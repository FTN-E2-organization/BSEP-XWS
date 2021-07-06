package app.java.report.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.w3c.dom.Document;
import org.xmldb.api.modules.XMLResource;

import app.java.report.db.ExistManager;

@Repository
public class ReportRepository {

	private String collectionId = "/db/report";
	@Autowired
	private ExistManager existManager;
	
	public void saveReport(String text) throws Exception {
		existManager.storeFromText(collectionId, "Izvjestaj", text);
	}
	
	public XMLResource getReport() throws Exception{
		return existManager.load(collectionId, "Izvjestaj");
	}
}
