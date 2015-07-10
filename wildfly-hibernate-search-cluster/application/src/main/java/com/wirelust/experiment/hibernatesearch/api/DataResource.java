package com.wirelust.experiment.hibernatesearch.api;

import com.wirelust.experiment.hibernatesearch.model.City;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.io.input.BOMInputStream;
import org.apache.lucene.search.Query;
import org.hibernate.search.SearchFactory;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.hibernate.search.stat.Statistics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;

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

	@GET
	@Path("/search")
	public Response search(
			@QueryParam("q")
			final String queryString
		) {

		FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(em);

		QueryBuilder qb = fullTextEntityManager.getSearchFactory()
			.buildQueryBuilder()
			.forEntity(City.class)
			.get();

		Query luceneQuery = qb.keyword()
			.onFields("locode", "name", "state")
			.matching(queryString).createQuery();

		// wrap Lucene query in a javax.persistence.Query
		FullTextQuery jpaQuery = fullTextEntityManager.createFullTextQuery(luceneQuery, City.class);

		// execute search
		List<City> result = (List<City>)jpaQuery.getResultList();

		return Response.status(Response.Status.OK).entity(result).build();
	}

	@GET
	@Path("/stats")
	public Response stats() {

		HashMap<String, Long> results = new HashMap<>();

		// count how many items are in the database
		javax.persistence.Query countQuery = em.createQuery("SELECT count(c) FROM City c");
		Object result = countQuery.getSingleResult();
		long count = 0;
		if (result instanceof Integer) {
			count = (Integer)result;
		} else if (result instanceof Long) {
			count = (Long)result;
		}
		results.put("db-count", count);


		// count how many items are in the full text index
		FullTextEntityManager fullTextEntityManager =
			org.hibernate.search.jpa.Search.getFullTextEntityManager(em);
		SearchFactory searchFactory = fullTextEntityManager.getSearchFactory();
		Statistics statistics = searchFactory.getStatistics();

		results.put("full-text-count", (long)statistics.getNumberOfIndexedEntities(City.class.getName()));

		return Response.status(Response.Status.OK).entity(results).build();

	}

}
