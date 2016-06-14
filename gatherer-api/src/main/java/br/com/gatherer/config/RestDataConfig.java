package br.com.gatherer.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;

import br.com.gatherer.model.Activity;
import br.com.gatherer.model.Device;
import br.com.gatherer.model.Measure;

@Configuration
public class RestDataConfig extends RepositoryRestConfigurerAdapter {
	@Override
	public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
		config.exposeIdsFor(Device.class, Activity.class, Measure.class);
		super.configureRepositoryRestConfiguration(config);
	}
}
