package com.wirelust.experiment.hibernatesearch.producers;

import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Date: 09-Jul-2015
 *
 * @author T. Curran
 */
public class EntityManagerProducer {

	@PersistenceContext
	private EntityManager entityManager;


	@Produces
	protected EntityManager createEntityManager() {
		return this.entityManager;
	}

}
