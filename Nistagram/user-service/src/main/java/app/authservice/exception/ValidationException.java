package app.userservice.exception;

public class ValidationException extends UserServiceException {

	private static final long serialVersionUID = -2402829180545847130L;
	
	public ValidationException() {
        super();
    }
	
	public ValidationException(String message) {
        super(message);
    }

}
