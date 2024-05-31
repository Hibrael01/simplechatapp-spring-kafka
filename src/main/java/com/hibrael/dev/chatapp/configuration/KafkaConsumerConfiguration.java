package com.hibrael.dev.chatapp.configuration;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import com.hibrael.dev.chatapp.model.ChatMessage;

@EnableKafka
@Configuration
public class KafkaConsumerConfiguration {

	@Value(value = "${spring.kafka.bootstrap-servers}")
	private String bootstrapAddress;
	
	@Bean
	public ConsumerFactory<String, String> consumerFactory(String groupId) {
		//Defines the configuration to create a Kafka Consumer instance
		
		Map<String, Object> props = new HashMap<>();
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
		props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
		props.put(JsonDeserializer.VALUE_DEFAULT_TYPE, ChatMessage.class);
		props.put(JsonDeserializer.TRUSTED_PACKAGES, "com.hibrael.dev.chatapp.model");
		return new DefaultKafkaConsumerFactory<>(props);
	}
	
	
	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory(String groupId) {
		ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(consumerFactory(groupId));
		return factory;
	}
	
	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, String> chatContainerFactory() {
		return kafkaListenerContainerFactory("chat");
	}
}
