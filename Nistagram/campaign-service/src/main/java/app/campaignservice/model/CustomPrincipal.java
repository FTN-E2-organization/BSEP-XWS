package app.campaignservice.model;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CustomPrincipal {

    private String username;
    private String roles;
    private String permissions;
    private String token;
    
}
