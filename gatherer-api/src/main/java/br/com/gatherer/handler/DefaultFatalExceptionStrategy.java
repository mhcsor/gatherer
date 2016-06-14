package br.com.gatherer.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.listener.FatalExceptionStrategy;
import org.springframework.amqp.rabbit.listener.exception.ListenerExecutionFailedException;
import org.springframework.amqp.support.converter.MessageConversionException;
import org.springframework.messaging.handler.annotation.support.MethodArgumentNotValidException;
import org.springframework.web.client.HttpClientErrorException;

public class DefaultFatalExceptionStrategy implements FatalExceptionStrategy {

	private static final Logger LOGGER = LoggerFactory.getLogger(DefaultFatalExceptionStrategy.class);

	public boolean isFatal(Throwable t) {
		if (t instanceof ListenerExecutionFailedException && (t.getCause() instanceof MessageConversionException
				|| t.getCause() instanceof MethodArgumentNotValidException
				|| t.getCause() instanceof HttpClientErrorException)) {
			LOGGER.warn("Fatal message loading error; message rejected; it will be dropped: {}",
					((ListenerExecutionFailedException) t).getFailedMessage());
			return true;
		}

		return false;
	}

}
