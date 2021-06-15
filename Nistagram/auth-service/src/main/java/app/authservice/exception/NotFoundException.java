package app.authservice.exception;

public class NotFoundException extends UserServiceException {

	private static final long serialVersionUID = 9113503975454739256L;
	
	public NotFoundException() {
        super();
    }
	
	public NotFoundException(String message) {
        super(message);
    }

}
