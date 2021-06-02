package app.mediaservice;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.Lifecycle;
import org.springframework.stereotype.Component;

import app.mediaservice.repository.MediaRepository;
import app.mediaservice.service.MediaService;

@SpringBootApplication
@EnableEurekaClient
public class MediaServiceApplication implements CommandLineRunner{
	
	@Resource
	private MediaService ms;
	
	@Component
	public static class ApplicationLifecycle implements Lifecycle {
		
		@Autowired
		private MediaRepository mediaRepository;
		
		@Value("${dropDatabase}")
		private boolean shouldDrop;

		@Override
		public void start() {
		}
		
		@Resource
		private MediaService ms;
		
		@Override
		public void stop() {
			if (shouldDrop) {
				mediaRepository.deleteAll();
				ms.deleteAll();
			}
		}

		@Override
		public boolean isRunning() {
			return true;
		}
	}
	
	
	public static void main(String[] args) {
		SpringApplication.run(MediaServiceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		ms.init();
		
	}
	
}
