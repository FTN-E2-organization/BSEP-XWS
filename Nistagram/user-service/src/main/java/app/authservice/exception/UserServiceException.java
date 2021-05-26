package app.authservice.exception;

public class UserServiceException extends Exception {

	private static final long serialVersionUID = 8892212404640952207L;
	
	public UserServiceException() {
        super();
    }
	
	public UserServiceException(String message) {
        super(message);
    }

}
