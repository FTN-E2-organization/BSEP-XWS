package app.followingservice.validator;

import app.followingservice.dto.ProfileCategoryDTO;
import app.followingservice.exception.ValidationException;

public class ProfileCategoryValidator {

	private static void checkNullOrEmpty(String field, String message) throws Exception {
		if(field == null || field.isEmpty()) {
			throw new ValidationException(message);
		}
	}
	
	public static void validate(ProfileCategoryDTO profileCategoryDTO) throws Exception {
		checkNullOrEmpty(profileCategoryDTO.name, "Category name is required field.");
	}
	
}
