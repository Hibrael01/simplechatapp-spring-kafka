package com.hibrael.dev.chatapp.configuration;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfiguration {

	@Value(value = "${message.topic.private.name}")
	private String topicName;
	
	@Bean
	public NewTopic createNewTopic() {
		return TopicBuilder.name(topicName)
				.partitions(10)
				.replicas(1)
				.compact()
				.build();
	}
	
}
