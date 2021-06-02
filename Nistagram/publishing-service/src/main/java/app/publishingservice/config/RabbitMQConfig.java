package app.publishingservice.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

	
	@Value("${queue.publishing-profile-created}")
	String queueCreated;
	
	@Value("${queue.profile-canceled}")
	String queueCanceled;
	
	@Value("${queue.story-created}")
	String queueStoryCreated;
	

	@Bean
	Queue queueCreated() {
		return new Queue(queueCreated, false);
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
