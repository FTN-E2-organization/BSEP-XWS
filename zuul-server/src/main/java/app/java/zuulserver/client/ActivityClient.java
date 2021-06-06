package app.java.zuulserver.client;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "activity-service")
public interface ActivityClient {	
	
}
