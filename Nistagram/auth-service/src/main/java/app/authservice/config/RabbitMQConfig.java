package app.authservice.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

	
	@Value("${queue.profile-created}")
	String queueCreated;

	@Value("${queue.profile-canceled}")
	String queueCanceled;
	
	
	@Value("${queue.profile-done}")
	String queueDone;


	@Bean
	Queue queueCreate() {
		return new Queue(queueCreated, false);
	}
	
	@Bean
	Queue queueCanceled() {
		return new Queue(queueCanceled, false);
	}
	
	@Bean
	Queue queueDone() {
		return new Queue(queueDone, false);
	}
	
	@Bean
	public MessageConverter jsonMessageConverter() {
		return new Jackson2JsonMessageConverter();
	}

	
}
