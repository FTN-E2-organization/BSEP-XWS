package app.publishingservice.event;

import java.io.File;
import java.util.List;

import app.publishingservice.model.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class PostCreatedEvent {

	private String transactionId;
	private Post post;
	private List<File> files;
}
