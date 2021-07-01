package app.publishingservice.exception;

public class PublishingServiceException extends Exception {

	private static final long serialVersionUID = 8892212404640952207L;
	
	public PublishingServiceException() {
        super();
    }
	
	public PublishingServiceException(String message) {
        super(message);
    }

}
