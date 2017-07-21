package com.aa.flighthub.bluemix.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KafkaProducerApplication implements CommandLineRunner {
	
	@Autowired
	private FlightHubKafkaProducer producer;
	
	String topic = "Test";
	
	private static final Logger LOGGER = LoggerFactory.getLogger(FlightHubKafkaProducer.class);

	public static void main(String[] args) {
		SpringApplication.run(KafkaProducerApplication.class, args);
	}

	@Override
	public void run(String... arg0) throws Exception {
		LOGGER.info("Ready");
		producer.send(topic, "Test Message");
		
	}
	
	
}
