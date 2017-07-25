package com.aa.flighthub.bluemix.producer;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.config.SaslConfigs;
import org.apache.kafka.common.config.SslConfigs;

import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

@Configuration
public class KafkaProducerConfig {
	  @Value("${bootstrap.servers}")
	  private String bootstrapServers;
	  
	  @Value("${sasl.jaas.config}")
	  private String saslConfig;
	  
	  @Value("${user}")
	  private String user;
	  
	  @Value("${password}")
	  private String password;
	  
	  @Value("${topic}")
	  private String topic;
	  

	  @Bean
	  public Map<String, Object> producerConfigs() {
	    Map<String, Object> props = new HashMap<>();
	    props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
	    props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
	    props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
	    // The security.protocol constant is defined in org.apache.kafka.streams.StreamsConfig and I don't want to import a whole package just for that
	    props.put("security.protocol", "SASL_SSL");
	    props.put(SaslConfigs.SASL_JAAS_CONFIG, saslConfig.replace("USERNAME", user).replace("PASSWORD", password));
	    props.put(SaslConfigs.SASL_MECHANISM, "PLAIN");
	    props.put(SslConfigs.SSL_PROTOCOL_CONFIG, "TLSv1.2");
	    props.put(SslConfigs.SSL_ENABLED_PROTOCOLS_CONFIG, "TLSv1.2");
	    props.put(SslConfigs.SSL_ENDPOINT_IDENTIFICATION_ALGORITHM_CONFIG, "HTTPS");
	    
	    return props;
	  }

	  @Bean
	  public ProducerFactory<String, String> producerFactory() {
	    return new DefaultKafkaProducerFactory<>(producerConfigs());
	  }

	  @Bean
	  public KafkaTemplate<String, String> kafkaTemplate() {
	    return new KafkaTemplate<>(producerFactory());
	  }

	  @Bean
	  public FlightHubKafkaProducer producer() {
	    return new FlightHubKafkaProducer(kafkaTemplate(), topic);
	  }
	
}
