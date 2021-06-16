package app.followingservice.exception;

public class BadRequest extends FollowingServiceException{

	private static final long serialVersionUID = 2358480594697234222L;

	public BadRequest() {
        super();
    }
	
	public BadRequest(String message) {
        super(message);
    }
	
}
