package app.java.zuulserver.dto;

import app.java.zuulserver.enums.ContentType;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class MediaContentDTO {

	public Long idContent;
	public ContentType contentType;
	public String path;
	public String ownerUsername;
	
}
