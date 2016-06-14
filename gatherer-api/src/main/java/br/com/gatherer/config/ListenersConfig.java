package br.com.gatherer.config;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.ConditionalRejectingErrorHandler;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.gatherer.handler.DefaultFatalExceptionStrategy;
import br.com.gatherer.listener.DefaultMessageListener;

@Configuration
public class ListenersConfig {

	// AMQP properties
	private @Value("${amqp.hostname}") String hostname;
	private @Value("${amqp.vhost}") String vhost;
	private @Value("${amqp.username}") String username;
	private @Value("${amqp.password}") String password;
	private @Value("${amqp.queue.measures}") String measures;
	private @Value("${amqp.queue.activities}") String activities;

	// REST API properties
	private @Value("${api.uri}") String apiUri;
	private @Value("${api.endpoint.activities}") String activitiesEndpoint;
	private @Value("${api.endpoint.measures}") String measuresEndpoint;

	@Bean
	public SimpleMessageListenerContainer measuresListenerContainer() {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setConnectionFactory(connectionFactory());
		container.setQueueNames(measures);
		container.setMessageListener(measuresListener());
		container.setAcknowledgeMode(AcknowledgeMode.AUTO);
		container.setErrorHandler(new ConditionalRejectingErrorHandler(new DefaultFatalExceptionStrategy()));
		return container;
	}

	@Bean
	public SimpleMessageListenerContainer activitiesListenerContainer() {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setConnectionFactory(connectionFactory());
		container.setQueueNames(activities);
		container.setMessageListener(activitiesListener());
		container.setAcknowledgeMode(AcknowledgeMode.AUTO);
		container.setErrorHandler(new ConditionalRejectingErrorHandler(new DefaultFatalExceptionStrategy()));
		return container;
	}

	@Bean
	public ConnectionFactory connectionFactory() {
		CachingConnectionFactory connectionFactory = new CachingConnectionFactory(hostname);
		connectionFactory.setUsername(username);
		connectionFactory.setPassword(password);
		connectionFactory.setVirtualHost(vhost);
		return connectionFactory;
	}

	public MessageListener measuresListener() {
		return new DefaultMessageListener(apiUri, measuresEndpoint);
	}

	@Bean
	public MessageListener activitiesListener() {
		return new DefaultMessageListener(apiUri, activitiesEndpoint);
	}
}
