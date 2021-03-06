package com.appleframework.jms.eventbus.producer;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appleframework.jms.core.exception.JmsException;
import com.appleframework.jms.core.producer.MessageProducer;
import com.appleframework.jms.core.utils.ByteUtils;
import com.google.common.eventbus.EventBus;


/**
 * @author Cruise.Xu
 * 
 */
public class ByteMessageProducer implements MessageProducer {
	
	private static Logger logger = LoggerFactory.getLogger(ByteMessageProducer.class);

	private EventBus eventBus;

	public void setEventBus(EventBus eventBus) {
		this.eventBus = eventBus;
	}

	@Override
	public void sendByte(byte[] message) throws JmsException {
		try {
			eventBus.post(message);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

	@Override
	public void sendObject(Serializable message) throws JmsException {
		try {
			eventBus.post(ByteUtils.toBytes(message));
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

	@Override
	public void sendText(String message) throws JmsException {
		try {
			eventBus.post(message.getBytes());
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

}