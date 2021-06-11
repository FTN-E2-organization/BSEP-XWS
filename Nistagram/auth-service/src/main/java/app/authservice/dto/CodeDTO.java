package app.authservice.dto;

public class CodeDTO {
	public String username;
	public String enteredCode;
	
	public CodeDTO() {}

	public CodeDTO(String username, String enteredCode) {
		super();
		this.username = username;
		this.enteredCode = enteredCode;
	}
}
