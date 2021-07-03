package app.campaignservice.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

	
	@Value("${queue.auth-campaign-profile}")
	String queueCampaignProfile;
	
	@Value("${queue.profile-canceled}")
	String queueCanceled;

	@Bean
	Queue queueCampaignProfile() {
		return new Queue(queueCampaignProfile, false);
	}
	
	@Bean
	Queue queueCanceled() {
		return new Queue(queueCanceled, false);
	}


	@Bean
	public MessageConverter jsonMessageConverter() {
		return new Jackson2JsonMessageConverter();
	}

	
}
