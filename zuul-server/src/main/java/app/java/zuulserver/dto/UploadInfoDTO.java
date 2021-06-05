package app.java.zuulserver.dto;

import app.java.zuulserver.enums.ContentType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UploadInfoDTO {

	Long contentId;
	ContentType type;
}
