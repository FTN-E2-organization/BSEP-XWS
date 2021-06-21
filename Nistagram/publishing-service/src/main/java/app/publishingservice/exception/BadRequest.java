package app.publishingservice.exception;

public class BadRequest extends PublishingServiceException {

	private static final long serialVersionUID = -6974120806210175911L;

	public BadRequest() {
        super();
    }
	
	public BadRequest(String message) {
        super(message);
    }

}

