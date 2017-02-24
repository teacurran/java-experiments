package com.wirelust.sosandbox.providers;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.Provider;

import org.jboss.resteasy.plugins.providers.jackson.ResteasyJacksonProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Date: 23-Feb-2017
 *
 * @author T. Curran
 */
@Provider
@Consumes({MediaType.APPLICATION_JSON, "text/json"})
@Produces({MediaType.APPLICATION_JSON, "text/json"})
public class JacksonConfigurationProvider extends ResteasyJacksonProvider {

	private static final Logger LOGGER = LoggerFactory.getLogger(JacksonConfigurationProvider.class);

	public JacksonConfigurationProvider() {
		super();

		LOGGER.info("loading jackson configurator");

		JacksonObjectMapper mapper = JacksonObjectMapper.get();
		setMapper(mapper);

	}
}