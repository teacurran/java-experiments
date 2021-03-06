package com.wirelust.jmscluster.jobs;

import java.net.InetAddress;
import java.net.UnknownHostException;
import javax.annotation.Resource;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.Queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Date: 05-Jul-2015
 *
 * @author T. Curran
 */
@Singleton
public class MessageProducerJob {

	private static final Logger LOGGER = LoggerFactory.getLogger(MessageProducerJob.class);

	@Resource(lookup = "java:/queue/test")
	Queue testQueue;

	// Injecting an instance because of:
	// https://issues.jboss.org/browse/WFLY-3338
	@Inject
	Instance<JMSContext> jmsContextInstance;

	String hostName;
	int messageCount = 0;

	@Schedule(hour = "*", minute = "*", second = "*/5", persistent = false)
	public void createMessages() {
		messageCount++;

		String messageString = String.format("message number:%d from host:%s node:%s",
				messageCount,
				getHostName(),
				getNodeName());

		LOGGER.info("sending {}", messageString);

		JMSContext context = jmsContextInstance.get();
		context.createProducer().send(testQueue, messageString);
	}

	public String getHostName() {
		if (hostName != null) {
			return hostName;
		}

		InetAddress inetAddress = null;
		try {
			inetAddress = InetAddress.getLocalHost();
			hostName = inetAddress.getHostName();
		} catch (UnknownHostException une) {
			LOGGER.error("unable to get host name", une);
			hostName = "unknown";
		}

		return hostName;
	}

	public String getNodeName() {

		return System.getProperty("nodename");
	}

}
