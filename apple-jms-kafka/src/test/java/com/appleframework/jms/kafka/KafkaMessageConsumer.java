package com.appleframework.jms.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appleframework.jms.kafka.consumer.multithread.thread.BytesMessageConsumer;


public class KafkaMessageConsumer extends BytesMessageConsumer {

	private static Logger logger = LoggerFactory.getLogger(KafkaMessageConsumer.class.getName());
	
	@Override
	public void onMessage(byte[] message) {
		String object = new String(message);
		logger.error(object.toString());
		commitSync();
	}

}
