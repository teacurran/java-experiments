package com.wirelust.jmscluster.mdb;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.MessageListener;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.hibernate.search.backend.impl.jms.AbstractJMSHibernateSearchController;

/**
 * Date: 05-Jul-2015
 *
 * @author T. Curran
 */
@MessageDriven(activationConfig = {
		@ActivationConfigProperty(propertyName="destinationType", propertyValue="javax.jms.Queue"),
		@ActivationConfigProperty(propertyName="destination", propertyValue="queue/HibernateSearch")
})
public class HibernateSearchIndexer extends AbstractJMSHibernateSearchController implements MessageListener {

	@PersistenceContext
	EntityManager em;

	@Override
	protected Session getSession() {
		return em.unwrap(Session.class);
	}

	protected void cleanSessionIfNeeded(Session session) {
	}
}

