package app.followingservice.exception;

public class ValidationException extends FollowingServiceException{

	private static final long serialVersionUID = 1780380959150814297L;
	
	public ValidationException() {
        super();
    }
	
	public ValidationException(String message) {
        super(message);
    }
}
