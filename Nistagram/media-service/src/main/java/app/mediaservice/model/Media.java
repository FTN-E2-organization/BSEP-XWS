package app.mediaservice.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import app.mediaservice.enums.ContentType;

@Document
public class Media {

	@Id
	private String idMongo;

	private Long idContent;

	private ContentType contentType;
	
	private String path;
	
	public Long getIdContent() {
		return idContent;
	}

	public void setIdContent(Long idContent) {
		this.idContent = idContent;
	}

	public ContentType getContentType() {
		return contentType;
	}

	public void setContentType(ContentType contentType) {
		this.contentType = contentType;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getIdMongo() {
		return idMongo;
	}

	public void setIdMongo(String idMongo) {
		this.idMongo = idMongo;
	}

}