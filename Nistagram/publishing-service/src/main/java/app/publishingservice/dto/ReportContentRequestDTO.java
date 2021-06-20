package app.publishingservice.dto;

import app.publishingservice.enums.ContentType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ReportContentRequestDTO {

	public String reason;
	public int contentId;
	public ContentType type;
	public String initiatorUsername;
	public String ownerUsername;
}
