package app.java.agentapp.validator;

import app.java.agentapp.dto.AgentDTO;
import app.java.agentapp.exception.ValidationException;
import java.util.regex.*;

public class AgentValidator {
	
	public static void createAgentValidation(AgentDTO agentDTO) throws Exception {
		checkNullOrEmpty(agentDTO.username, "Username is required field.");
		checkNullOrEmpty(agentDTO.email, "Email is required field.");
		checkNullOrEmpty(agentDTO.password, "Password is required field.");
		
		checkEmailFormat(agentDTO.email);
		checkPasswordFormat(agentDTO.password);
	}
	
	public static void checkNullOrEmpty(String field, String message) throws Exception {
		if(field == null || field.isEmpty()) {
			throw new ValidationException(message);
		}
	}
	
	public static void checkPasswordFormat(String password) throws Exception{
		/*Must have at least one numeric character.
		Must have at least one lowercase character.
		Must have at least one uppercase character.
		Must have at least one special symbol.
		Password must contain a length of at least 10 characters and a maximum of 32 characters.*/
		Pattern pattern = Pattern.compile("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[?|!@#.$%/]).{10,32}$");
        Matcher matcher = pattern.matcher(password);
        if(!matcher.matches()) {
        	throw new ValidationException("Wrong format of password.");
        }
	}
	
	public static void checkEmailFormat(String username) throws Exception{
		Pattern usernameRegex = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = usernameRegex.matcher(username);
        if(!matcher.find())
        	throw new ValidationException("Wrong format of email.");
	}

}
