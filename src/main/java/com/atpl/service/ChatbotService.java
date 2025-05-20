package com.atpl.service;

import com.atpl.dto.ChatRequest;
import com.atpl.dto.ChatResponse;

public interface ChatbotService {
	public ChatResponse getAnswer(ChatRequest req);
}
