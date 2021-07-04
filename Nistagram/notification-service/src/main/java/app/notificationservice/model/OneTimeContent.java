package app.notificationservice.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Document
public class OneTimeContent {

	@Id
	private String idMongo;
	
	private String contentPath;
	
	@JsonProperty
	private boolean isSeen;
}
