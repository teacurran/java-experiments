package com.wirelust.experiment.hibernatesearch.api;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import javax.annotation.Resource;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.Queue;
import javax.management.MBeanServerConnection;
import javax.management.MBeanServerInvocationHandler;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.wirelust.experiment.hibernatesearch.model.City;
import com.wirelust.experiment.hibernatesearch.repositories.CityRepository;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.lucene.search.Query;
import org.hibernate.search.MassIndexer;
import org.hibernate.search.SearchFactory;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.hibernate.search.stat.Statistics;
import org.hornetq.api.core.management.MessageCounterInfo;
import org.hornetq.api.core.management.ObjectNameBuilder;
import org.hornetq.api.jms.management.JMSQueueControl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Date: 09-Jul-2015
 *
 * @author T. Curran
 */
@Path("/data")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class DataResource {

	private static final Logger LOGGER = LoggerFactory.getLogger(DataResource.class);

	private static final String JMX_HOST = "localhost";
	private static final int JMX_PORT = 9990;  // management-web port
	String JMX_URL = System.getProperty("jmx.service.url", "service:jmx:http-remoting-jmx://" + JMX_HOST + ":" +
		JMX_PORT);

	@Inject
	EntityManager em;

	@Resource(lookup = "java:/queue/HibernateSearch")
	Queue hibernateSearchQueue;

	// Injecting an instance because of:
	// https://issues.jboss.org/browse/WFLY-3338
	@Inject
	Instance<JMSContext> jmsContextInstance;

	@Inject
	CityRepository cityRepository;

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
				cityRepository.save(city);
			}

		} catch (Exception e) {
			LOGGER.error("Fatal error", e);
		}

		return Response.status(Response.Status.NO_CONTENT).build();
	}

	@GET
	@Path("/reindex")
	public Response reindex() throws InterruptedException {

		LOGGER.info("Reindexing application");

		FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(em);
		fullTextEntityManager.flushToIndexes();

		MassIndexer indexer = fullTextEntityManager.createIndexer(City.class);
		indexer.startAndWait();

		return Response.status(Response.Status.NO_CONTENT).build();
	}

	@GET
	@Path("/search")
	@Transactional
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
	@Transactional
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


		long queueDepth = -1;
		long queueCount = -1;
		try {
			ObjectName on = ObjectNameBuilder.DEFAULT.getJMSQueueObjectName(hibernateSearchQueue.getQueueName());
			JMXConnector connector = JMXConnectorFactory.connect(new JMXServiceURL(JMX_URL), new HashMap<String,
				Object>());
			MBeanServerConnection mbsc = connector.getMBeanServerConnection();
			JMSQueueControl queueControl = MBeanServerInvocationHandler.newProxyInstance(mbsc, on,
				JMSQueueControl.class, false);

			// Step 8. List the message counters and convert them to MessageCounterInfo data structure.
			String counters = queueControl.listMessageCounter();
			MessageCounterInfo messageCounter = MessageCounterInfo.fromJSON(counters);

			queueDepth = messageCounter.getDepth();
			queueCount = messageCounter.getCount();
		} catch (Exception e) {
			LOGGER.error("error reading queue length", e);
		}
		results.put("queue-count", queueCount);
		results.put("queue-depth", queueDepth);


		// count how many items are in the full text index
		FullTextEntityManager fullTextEntityManager =
			org.hibernate.search.jpa.Search.getFullTextEntityManager(em);
		SearchFactory searchFactory = fullTextEntityManager.getSearchFactory();
		Statistics statistics = searchFactory.getStatistics();
		results.put("full-text-count", (long)statistics.getNumberOfIndexedEntities(City.class.getName()));

		return Response.status(Response.Status.OK).entity(results).build();

	}

}
