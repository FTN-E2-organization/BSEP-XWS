package app.mediaservice.dto;

import app.mediaservice.enums.ContentType;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class UploadInfoDTO {

	public Long contentId;
	public ContentType type;
}
