package com.wirelust.experiment.hibernatesearch.api;

import com.wirelust.experiment.hibernatesearch.model.City;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.io.input.BOMInputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Date: 09-Jul-2015
 *
 * @author T. Curran
 */
@Path("/data")
@Produces(MediaType.APPLICATION_JSON)
@Transactional
public class DataResource {

	private static final Logger LOGGER = LoggerFactory.getLogger(DataResource.class);

	@Inject
	EntityManager em;

	@GET
	@Path("/bootstrap")
	public Response bootstrap() {

		LOGGER.info("BootStrapping application");


		try (
			BufferedReader reader = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("/cities.csv")));
			CSVParser parser = new CSVParser(reader, CSVFormat.EXCEL.withHeader());
			) {

			for (final CSVRecord record : parser) {

				City city = new City();
				city.setLocode(record.get("LOCODE"));
				city.setName(record.get("NAME"));
				city.setState(record.get("STATE"));
				em.persist(city);
			}

		} catch (Exception e) {
			LOGGER.error("Fatal error", e);
		}

		return Response.status(Response.Status.NO_CONTENT).build();
	}
}
