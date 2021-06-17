package app.java.agentapp.exception;

public class BadRequest extends AgentAppException{

	private static final long serialVersionUID = 8252649832535064473L;

	public BadRequest() {
        super();
    }
	
	public BadRequest(String message) {
        super(message);
    }
}
