package app.publishingservice.model;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CustomPrincipal {

    private String username;
    private String roles;
    private String token;
    
}
