package app.mediaservice.dto;

import app.mediaservice.enums.ContentType;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class MediaDTO {
	public Long idContent;
	public ContentType contentType;
	public String path;

}
