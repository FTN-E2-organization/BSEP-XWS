package app.followingservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class FollowingServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(FollowingServiceApplication.class, args);
	}

}
