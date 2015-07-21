package com.wirelust.jmscluster.mdb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * Date: 20-Jul-2015
 *
 * @author T. Curran
 */
public class JMSListener implements MessageListener {

	private static final Logger LOGGER = LoggerFactory.getLogger(JMSListenerAnnotated.class);

    @Override
	public void onMessage(final Message argMessage) {

		if (argMessage instanceof TextMessage) {
			try {
				TextMessage textMessage = (TextMessage) argMessage;
				LOGGER.info("received:{} on queue/test2", textMessage.getText());
			} catch (JMSException ex) {
				throw new RuntimeException(ex);
			}
		} else {
			throw new IllegalArgumentException("Message must be of type TextMessage");
		}
	}

}

