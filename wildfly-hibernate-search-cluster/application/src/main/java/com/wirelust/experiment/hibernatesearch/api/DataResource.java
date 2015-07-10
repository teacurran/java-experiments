package com.wirelust.experiment.hibernatesearch.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Date: 09-Jul-2015
 *
 * @author T. Curran
 */
@Path("/data")
@Produces(MediaType.APPLICATION_JSON)
public class DataResource {

	private static final Logger LOGGER = LoggerFactory.getLogger(DataResource.class);

	@GET
	@Path("/bootstrap")
	public void bootstrap() {

		LOGGER.info("BootStrapping application");

	}
}
