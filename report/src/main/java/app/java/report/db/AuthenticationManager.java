package app.java.report.db;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Component
@Getter
@Setter
@NoArgsConstructor
public class AuthenticationManager {

	@Value("${conn.user}")
	private String user;
	@Value("${conn.password}")
	private String password;
	@Value("${conn.host}")
	private String host;
	@Value("${conn.port}")
	private String port;
	@Value("${conn.driver}")
	private String driver;
	@Value("${conn.uri}")
	private String uri;

}
