package app.java.agentapp.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import app.java.agentapp.dto.CustomerDTO;
import app.java.agentapp.exception.ValidationException;

public class CustomerValidator {

	public static void createCustomerValidation(CustomerDTO customerDTO) throws Exception {
		checkNullOrEmpty(customerDTO.username, "Username is required field.");
		checkNullOrEmpty(customerDTO.email, "Email is required field.");
		checkNullOrEmpty(customerDTO.password, "Password is required field.");
		
		checkEmailFormat(customerDTO.email);
		checkPasswordFormat(customerDTO.password);
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
