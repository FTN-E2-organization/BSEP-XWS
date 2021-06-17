package app.java.agentapp;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.Lifecycle;
import org.springframework.stereotype.Component;

import app.java.agentapp.repository.ProductRepository;
import app.java.agentapp.service.ProductService;

@SpringBootApplication
public class AgentAppApplication implements CommandLineRunner{

	@Resource
	private ProductService ps;
	
	@Component
	public static class ApplicationLifecycle implements Lifecycle {
		
		@Autowired
		private ProductRepository productRepository;
		
		@Override
		public void start() {
		}
		
		@Resource
		private ProductService ps;
		
		@Override
		public void stop() {
			productRepository.deleteAll();
			ps.deleteAll();
			
		}

		@Override
		public boolean isRunning() {
			return true;
		}
	}
	
	public static void main(String[] args) {
		SpringApplication.run(AgentAppApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		ps.init();
	}

}
