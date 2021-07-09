package app.activityservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.jaegertracing.Configuration.SenderConfiguration;
import io.jaegertracing.internal.samplers.ProbabilisticSampler;
import io.opentracing.Tracer;

@Configuration
public class JaegerConfig {

	
	@Bean
	public Tracer getTracer() {
		
		io.jaegertracing.Configuration.SamplerConfiguration samplerConfig = io.jaegertracing.Configuration.SamplerConfiguration
				.fromEnv().withType(ProbabilisticSampler.TYPE).withParam(1);

		/* Update default sender configuration with custom host and port */
		SenderConfiguration senderConfig = io.jaegertracing.Configuration.SenderConfiguration.fromEnv()
				.withAgentHost(System.getenv("JAEGER_HOST")).withAgentPort(6831);
		/* End */

		io.jaegertracing.Configuration.ReporterConfiguration reporterConfig = io.jaegertracing.Configuration.ReporterConfiguration
				.fromEnv().withLogSpans(true).withSender(senderConfig);

		io.jaegertracing.Configuration config = new io.jaegertracing.Configuration(System.getenv("JAEGER_SERVICE_NAME"))
				.withSampler(samplerConfig).withReporter(reporterConfig);

		return config.getTracer();
	}
}
