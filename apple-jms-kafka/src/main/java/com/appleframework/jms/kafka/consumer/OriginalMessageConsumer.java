package com.appleframework.jms.kafka.consumer;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appleframework.jms.core.consumer.AbstractMessageConusmer;

/**
 * @author Cruise.Xu
 * 
 */
@Deprecated
public abstract class OriginalMessageConsumer extends AbstractMessageConusmer<ConsumerRecord<String, byte[]>> implements Runnable {

	private static Logger logger = LoggerFactory.getLogger(OriginalMessageConsumer.class);

	protected String topic;
	
	protected String prefix = "";

	protected KafkaConsumer<String, byte[]> consumer;

	private AtomicBoolean closed = new AtomicBoolean(false);

	private long timeout = 100;

	protected void init() {
		new Thread(this).start();
	}

	@Override
	public void run() {
		try {
			String[] topics = topic.split(",");
			Set<String> topicSet = new HashSet<String>();
        	for (String tp : topics) {
        		String topicc = prefix + tp;
        		topicSet.add(topicc);
        		logger.warn("subscribe the topic -> " + topicc);
			}
			consumer.subscribe(topicSet);
			while (!closed.get()) {
				ConsumerRecords<String, byte[]> records = consumer.poll(timeout);
				for (ConsumerRecord<String, byte[]> record : records) {
					logger.debug("offset = %d, key = %s, value = %s%n", record.offset(), record.key(), record.value());
					processMessage(record);
				}
			}
		} catch (WakeupException e) {
			if (!closed.get())
				throw e;
		}
	}


	public void setTopic(String topic) {
		this.topic = topic.trim().replaceAll(" ", "");
	}

	public void setConsumer(KafkaConsumer<String, byte[]> consumer) {
		this.consumer = consumer;
	}

	public void setTimeout(long timeout) {
		this.timeout = timeout;
	}

	public void destroy() {
		closed.set(true);
		consumer.wakeup();
	}

	public void commitSync() {
		consumer.commitSync();
	}
	
	public void commitAsync() {
		consumer.commitAsync();
	}
	
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
}
