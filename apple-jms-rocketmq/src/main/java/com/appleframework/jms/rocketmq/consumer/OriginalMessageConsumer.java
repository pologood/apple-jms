package com.appleframework.jms.rocketmq.consumer;

import java.util.List;
import java.util.Map;

import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;

import com.appleframework.jms.core.consumer.IMessageConusmer;
import com.appleframework.jms.rocketmq.RocketMQPushConsumer;

/**
 * @author Cruise.Xu
 * 
 */
public abstract class OriginalMessageConsumer implements IMessageConusmer<Message> {

	private RocketMQPushConsumer consumer;

	private String topic;

	private String tags;

	private Map<String, String> topicTagMap;

	public void setConsumer(RocketMQPushConsumer consumer) {
		this.consumer = consumer;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public void setTopicTagMap(Map<String, String> topicTagMap) {
		this.topicTagMap = topicTagMap;
	}

	protected void init() throws MQClientException {
		if (null != topic && null != tags) {
			consumer.subscribe(topic, tags);
		}
		if (null != topicTagMap && topicTagMap.size() > 0) {
			for (String topicc : topicTagMap.keySet()) {
				String tagss = topicTagMap.get(topicc);
				if (null != topicc && null != tagss) {
					consumer.subscribe(topicc, tagss);
				}
			}
		}

		consumer.registerMessageListener(new MessageListenerConcurrently() {
			public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext context) {
				Message msg = list.get(0);
				onMessage(msg);
				return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
			}
		});
		consumer.start();
	}
}