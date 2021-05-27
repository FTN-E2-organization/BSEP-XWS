package app.followingservice.validator;

import app.followingservice.dto.ProfileDTO;
import app.followingservice.exception.ValidationException;

public class UserValidator {
	
	private static void checkNullOrEmpty(String field, String message) throws Exception {
		if(field == null || field.isEmpty()) {
			throw new ValidationException(message);
		}
	}
	
	public static void validate(ProfileDTO userDTO) throws Exception {
		checkNullOrEmpty(userDTO.username, "Username is required field.");
	}
}
