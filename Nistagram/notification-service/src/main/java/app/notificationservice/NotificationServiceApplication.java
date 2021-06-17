package app.notificationservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.Lifecycle;
import org.springframework.stereotype.Component;

import app.notificationservice.repository.NotificationRepository;
import app.notificationservice.repository.ProfileRepository;

@SpringBootApplication
public class NotificationServiceApplication {

	@Component
	public static class ApplicationLifecycle implements Lifecycle {

		@Autowired
		private NotificationRepository notificationRepository;
		@Autowired
		private ProfileRepository profileRepository;

		@Value("${dropDatabase}")
		private boolean shouldDrop;

		@Override
		public void start() {
		}

		@Override
		public void stop() {
			if (shouldDrop) {
				notificationRepository.deleteAll();
				profileRepository.deleteAll();
			}
		}

		@Override
		public boolean isRunning() {
			return true;
		}
	}	
	public static void main(String[] args) {
		SpringApplication.run(NotificationServiceApplication.class, args);
	}

}
