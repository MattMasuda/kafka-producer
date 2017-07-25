package com.aa.flighthub.bluemix.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

public class FlightHubKafkaProducer {

  private static final Logger LOGGER = LoggerFactory.getLogger(FlightHubKafkaProducer.class);

  private KafkaTemplate<String, String> kafkaTemplate;
  private String topic;
  
  public FlightHubKafkaProducer(KafkaTemplate<String,String> kafkaTemplate, String topic) {
	  this.kafkaTemplate = kafkaTemplate;
	  this.topic = topic;
  }

  public void send(String message) {
    // the KafkaTemplate provides asynchronous send methods returning a Future
    ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(topic, message);

    // register a callback with the listener to receive the result of the send asynchronously
    future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {

      @Override
      public void onSuccess(SendResult<String, String> result) {
        LOGGER.info("sent message='{}' with offset={}", message,
            result.getRecordMetadata().offset());
      }

      @Override
      public void onFailure(Throwable ex) {
        LOGGER.error("unable to send message='{}'", message, ex);
      }
    });

    // or, to block the sending thread to await the result, invoke the future's get() method
  }
}