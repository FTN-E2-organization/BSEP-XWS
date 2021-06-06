package app.java.zuulserver.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
public class VerificationResponseDTO {

	public String username;
    public String roles;
    public String permissions;
    
}
