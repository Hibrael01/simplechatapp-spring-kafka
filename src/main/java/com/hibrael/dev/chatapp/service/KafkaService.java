package com.hibrael.dev.chatapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.hibrael.dev.chatapp.model.ChatMessage;

@Service
public class KafkaService {

	@Autowired
	private KafkaTemplate<String, ChatMessage> kafkaTemplate;
	
	@Value(value = "${message.topic.private.name}")
	private String topicName;
	
	public void sendMessage(String key, String senderId, String recipientId, String bodyMessage) {
		kafkaTemplate.send(topicName, key, ChatMessage.builder()
				.senderId(senderId)
				.recipientId(recipientId)
				.body(bodyMessage)
				.build());
	}
	
	@KafkaListener(topics = "${message.topic.private.name}", containerFactory = "chatContainerFactory")
	public void listenerChat(ChatMessage chatMessage) {
		System.out.println(chatMessage.getSenderId() + " : " + chatMessage.getBody());
	}
	
}
