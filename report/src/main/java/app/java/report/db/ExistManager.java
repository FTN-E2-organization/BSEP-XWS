package app.java.report.db;

import java.io.File;

import javax.xml.transform.OutputKeys;

import org.exist.xmldb.DatabaseImpl;
import org.exist.xmldb.EXistResource;
import org.exist.xupdate.XUpdateProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Database;
import org.xmldb.api.base.ResourceSet;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.CollectionManagementService;
import org.xmldb.api.modules.XMLResource;
import org.xmldb.api.modules.XPathQueryService;
import org.xmldb.api.modules.XUpdateQueryService;

@Service
public class ExistManager {

	@Autowired
	private AuthenticationManager authManager;
	
	public void createConnection() throws Exception {
		Class<?> cl = Class.forName(authManager.getDriver());
		// encapsulation of the database driver functionality
		Database database = (Database) cl.newInstance();
		database.setProperty("create-database", "true");

		// entry point for the API which enables you to get the Collection reference
		DatabaseManager.registerDatabase(database);
	}

	public void closeConnection(Collection col, XMLResource res) throws XMLDBException {
		if (col != null) {
			col.close();
		}
		if (res != null) {
			((EXistResource) res).freeResources();
		}
	}
	
	public Collection getOrCreateCollection(String collectionUri, int pathOffset) throws XMLDBException {
		Collection col = DatabaseManager.getCollection(authManager.getUri()+collectionUri, authManager.getUser(), authManager.getPassword());
		if (col == null) {
			if (collectionUri.startsWith("/")) {
				collectionUri = collectionUri.substring(1);
			}
			String pathSegments[] = collectionUri.split("/");
			if (pathSegments.length > 0) {
				StringBuilder path = new StringBuilder();
				for (int i = 0; i <= pathOffset; i++) {
					path.append("/"+pathSegments[i]);
				}
				Collection startCol = DatabaseManager.getCollection(authManager.getUri() + path, authManager.getUser(),
						authManager.getPassword());
				if (startCol == null) {
					String parentPath = path.substring(0, path.lastIndexOf("/"));
					Collection parentCol = DatabaseManager.getCollection(authManager.getUri() + parentPath,
							authManager.getUser(), authManager.getPassword());
					CollectionManagementService service = (CollectionManagementService) parentCol
							.getService("CollectionManagementService", "1.0");
					col = service.createCollection(pathSegments[pathOffset]);
					col.close();
					parentCol.close();
				} else {
					startCol.close();
				}
			}
			return getOrCreateCollection(collectionUri, ++pathOffset);
		} else {
			return col;
		}
	}

	public void store(String collectionId, String documentId, String filePath) throws Exception {
		createConnection();
		Collection col = null;
		XMLResource res = null;
		try {
			col = getOrCreateCollection(collectionId, 0);
			res = (XMLResource) col.createResource(documentId, XMLResource.RESOURCE_TYPE);
			File f = new File(filePath);

			if (!f.canRead()) {
				return;
			}
			res.setContent(f);
			col.storeResource(res);
		} finally {
			closeConnection(col, res);
		}
	}

	public void storeFromText(String collectionId, String documentId, String xmlString) throws Exception  {
		createConnection();
		Collection col = null;
		XMLResource res = null;
		try {
			col = getOrCreateCollection(collectionId, 0);
			res = (XMLResource) col.createResource(documentId, XMLResource.RESOURCE_TYPE);
			res.setContent(xmlString);
			col.storeResource(res);
		} finally {
			closeConnection(col, res);
		}
	}
	
	public XMLResource load(String collectionUri, String documentId) throws Exception  {
		createConnection();
		Collection col = null;
		XMLResource res = null;
		try {
			col = DatabaseManager.getCollection(authManager.getUri() + collectionUri, authManager.getUser(),
					authManager.getPassword());
			col.setProperty(OutputKeys.INDENT, "yes");
			res = (XMLResource) col.getResource(documentId);
			return res;
		} finally {
			if (col != null) {
				col.close();
			}
		}
	}

	
}