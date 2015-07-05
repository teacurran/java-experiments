package com.wirelust.jmscluster.mdb;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;
import javax.xml.soap.Text;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Date: 05-Jul-2015
 *
 * @author T. Curran
 */
@MessageDriven(mappedName = "queue/test", activationConfig = {
		@ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge"),
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
		@ActivationConfigProperty(propertyName = "destination", propertyValue = "queue/test"),
		@ActivationConfigProperty(propertyName = "maxSession", propertyValue = "1") })
public class MessageListener implements javax.jms.MessageListener {

	private static final Logger LOGGER = LoggerFactory.getLogger(MessageListener.class);

	public void onMessage(final Message argMessage) {

		if (argMessage instanceof TextMessage) {
			try {
				TextMessage textMessage = (TextMessage) argMessage;
				LOGGER.info("received {}", textMessage.getText());
			} catch (JMSException ex) {
				throw new RuntimeException(ex);
			}
		} else {
			throw new IllegalArgumentException("Message must be of type TextMessage");
		}
	}

}
