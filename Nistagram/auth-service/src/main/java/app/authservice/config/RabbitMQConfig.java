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
	
	@Value("${fanout.auth}")
	String fanout;
	
	@Value("${queue.auth-activity-profile}")
	String queueActivityProfile;
	
	@Value("${queue.auth-publishing-profile}")
	String queuePublishingProfile;
	
	@Value("${queue.auth-following-profile}")
	String queueFollowingProfile;
	
	@Value("${queue.auth-story-profile}")
	String queueStoryProfile;
	
	@Value("${queue.auth-notification-profile}")
	String queueNotificationProfile;
	
	@Value("${queue.profile-canceled}")
	String queueCanceled;
	
	@Value("${queue.profile-done}")
	String queueDone;

	
	@Bean
	FanoutExchange fanoutCreate() {
		return new FanoutExchange(fanout);
	}
	
	@Bean
    Queue queueActivityProfile() {
      return new Queue(queueActivityProfile, false);
    }
	
	@Bean
    Queue queuePublishingProfile() {
      return new Queue(queuePublishingProfile, false);
    }
	
	@Bean
    Queue queueFollowingProfile() {
      return new Queue(queueFollowingProfile, false);
    }
	
	@Bean
    Queue queueStoryProfile() {
      return new Queue(queueStoryProfile, false);
    }
	
	@Bean
    Queue queueNotificationProfile() {
      return new Queue(queueNotificationProfile, false);
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
    Binding bindingToActivityProfile(Queue queueActivityProfile, FanoutExchange fanout) {
        return BindingBuilder.bind(queueActivityProfile).to(fanout);
	}

	@Bean
    Binding bindingToublishingProfile(Queue queuePublishingProfile, FanoutExchange fanout) {
        return BindingBuilder.bind(queuePublishingProfile).to(fanout);
	}
	
	@Bean
    Binding bindingToStoryProfile(Queue queueStoryProfile, FanoutExchange fanout) {
        return BindingBuilder.bind(queueStoryProfile).to(fanout);
	}
	
	@Bean
    Binding bindingToFollowingProfile(Queue queueFollowingProfile, FanoutExchange fanout) {
        return BindingBuilder.bind(queueFollowingProfile).to(fanout);
	}
	
	@Bean
    Binding bindingToNotificationProfile(Queue queueNotificationProfile, FanoutExchange fanout) {
        return BindingBuilder.bind(queueNotificationProfile()).to(fanout);
	}
	
	@Bean
	public MessageConverter jsonMessageConverter() {
		return new Jackson2JsonMessageConverter();
	}

	
}
