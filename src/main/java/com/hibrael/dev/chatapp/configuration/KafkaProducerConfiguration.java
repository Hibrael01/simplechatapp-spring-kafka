package com.hibrael.dev.chatapp.configuration;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import com.hibrael.dev.chatapp.model.ChatMessage;

@Configuration
public class KafkaProducerConfiguration {

	@Value(value = "${spring.kafka.bootstrap-servers}")
	private String bootstrapAddress;
	
	@Bean
	public ProducerFactory<String, ChatMessage> producerFactory() {
		//Defines the configuration to create a Kafka Producer instance
		
		Map<String, Object> configurationProps = new HashMap<>();
		configurationProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
		configurationProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		configurationProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
		
		return new DefaultKafkaProducerFactory<>(configurationProps);
	}
	
	@Bean
	public KafkaTemplate<String, ChatMessage> kafkaTemplate() {
		return new KafkaTemplate<>(producerFactory());
	}
	
}
