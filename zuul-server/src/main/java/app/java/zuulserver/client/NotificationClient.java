package app.java.zuulserver.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import app.java.zuulserver.dto.NotificationDTO;

@FeignClient(name = "notification-service")
public interface NotificationClient {

	@PostMapping("api/notification")
	public void create(@RequestBody NotificationDTO notificationDTO);
	
	
	
	
}
