package app.java.agentapp.exception;

public class ValidationException extends AgentAppException{

	private static final long serialVersionUID = -7757907814535874105L;

	public ValidationException() {
        super();
    }
	
	public ValidationException(String message) {
        super(message);
    }
}
