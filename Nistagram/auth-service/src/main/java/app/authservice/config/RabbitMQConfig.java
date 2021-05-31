package app.authservice.config;

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
	
	@Value("${fanout.profile-created}")
	String fanoutCreated;
	
	@Value("${queue.publishing-profile-created}")
	String queuePublishingCreated;
	
	@Value("${queue.following-profile-created}")
	String queueFollowingCreated;
	
	@Value("${queue.profile-canceled}")
	String queueCanceled;
	
	@Value("${queue.profile-done}")
	String queueDone;
	
	@Bean
	FanoutExchange fanoutCreate() {
		return new FanoutExchange(fanoutCreated);
	}
	
	@Bean
    Queue queuePublishingCreated() {
      return new Queue(queuePublishingCreated, false);
    }
	
	@Bean
    Queue queueFollowingCreated() {
      return new Queue(queueFollowingCreated, false);
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
    Binding bindingToPublishing(Queue queuePublishingCreated, FanoutExchange fanoutCreated) {
        return BindingBuilder.bind(queuePublishingCreated).to(fanoutCreated);
	}
	
	@Bean
    Binding bindingToFollowing(Queue queueFollowingCreated, FanoutExchange fanoutCreated) {
        return BindingBuilder.bind(queueFollowingCreated).to(fanoutCreated);
	}
	
	@Bean
	public MessageConverter jsonMessageConverter() {
		return new Jackson2JsonMessageConverter();
	}

	
}
