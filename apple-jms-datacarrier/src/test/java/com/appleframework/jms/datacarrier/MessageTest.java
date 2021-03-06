package com.appleframework.jms.datacarrier;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.appleframework.jms.core.producer.MessageProducer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:config/*.xml" })
public class MessageTest {

	@Resource
	private MessageProducer messageProducer;
	
	@Test
	public void testAddOpinion1() {
		try {
			long t = System.currentTimeMillis();
			for (int i = 1; i <= 10000; i++) {
				messageProducer.sendText("xxxxxxxxxxxxxxxxxxxxx" + i);
			}
			System.out.println(System.currentTimeMillis() - t);
			System.in.read();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
