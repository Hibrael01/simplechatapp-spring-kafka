package com.hibrael.dev.chatapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hibrael.dev.chatapp.service.KafkaService;

@RestController
@RequestMapping("/chat")
public class ChatController {

	@Autowired
	private KafkaService kafkaService;
	
	@PostMapping("/sendMessage")
	public void sendMessage(@RequestParam String key, @RequestParam String senderId, @RequestParam String recipientId, 
			@RequestParam String bodyMessage) {
		kafkaService.sendMessage(key, senderId, recipientId, bodyMessage);
	}
	
}
