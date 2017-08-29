package com.aa.flighthub.bluemix.producer;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KafkaProducerApplication implements CommandLineRunner {
	
	@Autowired
	private FlightHubKafkaProducer producer;
	
	@Value("${timer.period}")
	private long period;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(FlightHubKafkaProducer.class);

	public static void main(String[] args) {
		SpringApplication.run(KafkaProducerApplication.class, args);
	}

	@Override
	public void run(String... arg0) throws Exception {
		LOGGER.info("Ready");
		TimerTask repeatedTask = new TimerTask() {
			public void run() {
				producer.send("Test Message " + new Date().toInstant().toEpochMilli() );
			}
		};
		
		Timer timer = new Timer("Timer");
		long delay = 0L;
		timer.scheduleAtFixedRate(repeatedTask, delay, period);
	}
	
	
}
