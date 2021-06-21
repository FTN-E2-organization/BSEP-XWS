package app.authservice.dto;

import java.util.Collection;
import java.util.Set;

import app.authservice.model.ProfileType;

public class VerificationRequestDTO {

	public Long id;
	public String name;
	public String surname;
	public String category;
	public String username;
	public Boolean isApproved;
	public String type;
}
