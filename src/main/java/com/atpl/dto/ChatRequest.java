package com.atpl.dto;

import lombok.Data;

@Data
public class ChatRequest {
	private String question;
	private String client;
	private Integer pageno;
	private String user;
}