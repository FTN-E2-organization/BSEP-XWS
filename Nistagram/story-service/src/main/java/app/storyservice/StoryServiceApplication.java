package app.storyservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.Lifecycle;
import org.springframework.stereotype.Component;

import app.storyservice.repository.ProfileRepository;
import app.storyservice.repository.StoryRepository;

@SpringBootApplication
public class StoryServiceApplication {

	@Component
	public static class ApplicationLifecycle implements Lifecycle {

		@Autowired
		private StoryRepository storyRepository;
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
				storyRepository.deleteAll();
				profileRepository.deleteAll();
			}
		}

		@Override
		public boolean isRunning() {
			return true;
		}
	}

	public static void main(String[] args) {
		SpringApplication.run(StoryServiceApplication.class, args);
	}

}
