package br.com.gatherer.listener;

import org.apache.commons.lang3.time.DurationFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

public class DefaultMessageListener implements MessageListener {

	private static final Logger LOGGER = LoggerFactory.getLogger(DefaultMessageListener.class);

	private RestTemplate restTemplate;

	private HttpHeaders headers;

	private String endPoint;

	private String apiUri;

	public DefaultMessageListener(String apiUri, String endPoint) {
		this.restTemplate = new RestTemplate();
		this.headers = new HttpHeaders();
		this.headers.setContentType(MediaType.APPLICATION_JSON);
		this.endPoint = endPoint;
		this.apiUri = apiUri;
	}

	public void onMessage(Message message) {
		LOGGER.info("Starting message consuming...");
		long start = System.currentTimeMillis();

		HttpEntity<String> request = new HttpEntity<>(new String(message.getBody()), headers);

		LOGGER.info("Calling the REST API...");

		restTemplate.exchange(apiUri.concat(endPoint), HttpMethod.POST, request, String.class);

		LOGGER.info("Successfully called the REST API...");
		LOGGER.info("Message successfully consumed. Took {}", DurationFormatUtils
				.formatDuration(System.currentTimeMillis() - start, "ss'(seconds)' SSS'(milliseconds)'", true));
	}
}
