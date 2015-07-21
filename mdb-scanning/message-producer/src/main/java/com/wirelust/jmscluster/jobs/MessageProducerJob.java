package com.wirelust.jmscluster.jobs;

import java.net.InetAddress;
import java.net.UnknownHostException;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.jms.*;

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

	@Resource(lookup = "java:/queue/test2")
	Queue testQueue2;

	@Resource(lookup = "java:/JmsXA")
	private ConnectionFactory connectionFactory;

	Connection connection = null;

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

		Session session = null;
		MessageProducer testQueuePublisher = null;
		MessageProducer testQueue2Publisher = null;
		TextMessage message1 = null;
		TextMessage message2 = null;

		try {
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

			testQueuePublisher = session.createProducer(testQueue);
			message1 = session.createTextMessage(messageString);
			testQueuePublisher.send(message1);
			LOGGER.info("sent message:{} to queue:{}", messageString, testQueue.getQueueName());

			testQueue2Publisher = session.createProducer(testQueue2);
			message2 = session.createTextMessage(messageString);
			testQueue2Publisher.send(message2);
			LOGGER.info("sent message:{} to queue:{}", messageString, testQueue2.getQueueName());

		} catch (Exception e) {
			LOGGER.error("unable to send message", e);
		} finally {
			if (session != null) {
				try {
					session.close();
				} catch (Exception e) {
					LOGGER.error("unable to close JMS session", e);
				}
			}
		}
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

	@PostConstruct
	public void makeConnection() {
		try {
			connection = connectionFactory.createConnection();
		} catch (Exception t) {
			String reason = "Failed to create JMS connection";
			LOGGER.error(reason, t);
		}
	}

	@PreDestroy
	public void endConnection() throws RuntimeException {
		if (connection != null) {
			try {
				connection.close();
			} catch (Exception e) {
				String reason = "Failed to close JMS connection";
				LOGGER.error(reason, e);
			}
		}
	}

}
