package com.aa.flighthub.bluemix.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaProducerGo implements  ApplicationListener<ApplicationReadyEvent	> {

	@Autowired
	KafkaProducer producer;
	
	String topic = "Test";
	
	private static final Logger LOGGER = LoggerFactory.getLogger(KafkaProducer.class);

	@Override
	public void onApplicationEvent(final ApplicationReadyEvent event) {
	
		LOGGER.info("Ready");
		producer.send(topic, "Test Message");
		
	}
	
	
}
