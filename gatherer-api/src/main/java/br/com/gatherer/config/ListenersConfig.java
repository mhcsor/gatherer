package br.com.gatherer.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.ConditionalRejectingErrorHandler;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import br.com.gatherer.handler.DefaultFatalExceptionStrategy;
import br.com.gatherer.listener.DefaultMessageListener;

@Configuration
@PropertySource("file:${app.home}/conf/loader.properties")
public class ListenersConfig {

	@Autowired
	Environment env;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ListenersConfig.class);
	
	@Bean
	public SimpleMessageListenerContainer measuresListenerContainer() {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setConnectionFactory(connectionFactory());
		container.setQueueNames(env.getProperty("amqp.queue.measures"));
		container.setMessageListener(measuresListener());
		container.setAcknowledgeMode(AcknowledgeMode.AUTO);
		container.setErrorHandler(new ConditionalRejectingErrorHandler(new DefaultFatalExceptionStrategy()));
		return container;
	}

	@Bean
	public SimpleMessageListenerContainer activitiesListenerContainer() {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setConnectionFactory(connectionFactory());
		container.setQueueNames(env.getProperty("amqp.queue.activities"));
		container.setMessageListener(activitiesListener());
		container.setAcknowledgeMode(AcknowledgeMode.AUTO);
		container.setErrorHandler(new ConditionalRejectingErrorHandler(new DefaultFatalExceptionStrategy()));
		return container;
	}

	@Bean
	public ConnectionFactory connectionFactory() {
		CachingConnectionFactory connectionFactory = new CachingConnectionFactory(env.getProperty("amqp.hostname"));
		connectionFactory.setUsername(env.getProperty("amqp.username"));
		connectionFactory.setPassword(env.getProperty("amqp.password"));
		connectionFactory.setVirtualHost(env.getProperty("amqp.vhost"));
		connectionFactory.setHost(env.getProperty("amqp.hostname"));
		return connectionFactory;
	}

	@Bean
	public MessageListener measuresListener() {
		return new DefaultMessageListener(env.getProperty("api.uri"), env.getProperty("api.endpoint.measures"));
	}

	@Bean
	public MessageListener activitiesListener() {
		return new DefaultMessageListener(env.getProperty("api.uri"), env.getProperty("api.endpoint.activities"));
	}
}
