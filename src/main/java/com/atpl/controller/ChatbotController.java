package com.atpl.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.atpl.dto.ChatRequest;
import com.atpl.dto.ChatResponse;
import com.atpl.service.ChatbotService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chatbot")
public class ChatbotController {

	private final ChatbotService chatbotService;

	@PostMapping("/ask")
	public ResponseEntity<ChatResponse> askQuestion(@RequestBody ChatRequest chatRequest) {
		return new ResponseEntity<ChatResponse>(chatbotService.getAnswer(chatRequest), HttpStatus.OK);
	}
}