package app.java.zuulserver.dto;

import app.java.zuulserver.enums.ContentType;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class MediaDTO {
	public Long idContent;
	public ContentType contentType;
	public String path;

}
