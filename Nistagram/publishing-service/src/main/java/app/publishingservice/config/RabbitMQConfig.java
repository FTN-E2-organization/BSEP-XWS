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
	
	@Value("${fanout.post-created}")
	String fanoutPostCreated;
	
	@Value("${queue.media-post-created}")
	String queueMediaPostCreated;
	
	@Value("${fanout.story-created}")
	String fanoutStoryCreated;
	
	@Value("${queue.story-created}")
	String queueStoryCreated;
	
	@Value("${queue.media-story-created}")
	String queueMediaStoryCreated;

	@Bean
	Queue queueCreated() {
		return new Queue(queueCreated, false);
	}
	
	@Bean
	Queue queueCanceled() {
		return new Queue(queueCanceled, false);
	}
	
	@Bean
	FanoutExchange fanoutPostCreated() {
		return new FanoutExchange(fanoutPostCreated);
	}
	
	@Bean
	Queue queueMediaPostCreated() {
		return new Queue(queueMediaPostCreated, false);
	}
	
	@Bean
	FanoutExchange fanoutStoryCreated() {
		return new FanoutExchange(fanoutStoryCreated);
	}
	
	@Bean
	Queue queueStoryCreated() {
		return new Queue(queueStoryCreated, false);
	}
	
	@Bean
	Queue queueMediaStoryCreated() {
		return new Queue(queueMediaStoryCreated, false);
	}
	
	@Bean
    Binding bindingPostToMedia(Queue queueMediaPostCreated, FanoutExchange fanoutPostCreated) {
        return BindingBuilder.bind(queueMediaPostCreated).to(fanoutPostCreated);
	}
	
	@Bean
    Binding bindingStory(Queue queueStoryCreated, FanoutExchange fanoutStoryCreated) {
        return BindingBuilder.bind(queueStoryCreated).to(fanoutStoryCreated);
	}
	
	@Bean
    Binding bindingStoryToMedia(Queue queueMediaStoryCreated, FanoutExchange fanoutStoryCreated) {
        return BindingBuilder.bind(queueMediaStoryCreated).to(fanoutStoryCreated);
	}

	@Bean
	public MessageConverter jsonMessageConverter() {
		return new Jackson2JsonMessageConverter();
	}

	
}
