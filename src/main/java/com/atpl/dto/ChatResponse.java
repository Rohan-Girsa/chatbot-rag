package com.atpl.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatResponse {
	private String answer;
	private String sessionId;
	private String source;
	private boolean forwardedToAgent;
}