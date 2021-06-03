package app.storyservice.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

	
	@Value("${queue.auth-story-profile}")
	String queueStoryProfile;
	
	@Value("${queue.profile-canceled}")
	String queueCanceled;
	
	@Value("${queue.story-created}")
	String queueStoryCreated;

	@Bean
	Queue queueStoryProfile() {
		return new Queue(queueStoryProfile, false);
	}
	
	@Bean
	Queue queueCanceled() {
		return new Queue(queueCanceled, false);
	}
	
	@Bean
	Queue queueStoryCreated() {
		return new Queue(queueStoryCreated, false);
	}

	@Bean
	public MessageConverter jsonMessageConverter() {
		return new Jackson2JsonMessageConverter();
	}

	
}
