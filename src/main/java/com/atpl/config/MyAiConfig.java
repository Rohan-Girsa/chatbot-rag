package com.atpl.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;

@Configuration
public class MyAiConfig {

	private final Environment environment;

	public MyAiConfig(Environment environment) {
		this.environment = environment;
	}

	@Bean(name = "chatLanguageModel")
	public ChatLanguageModel chatLanguageModel() {
		String type = environment.getProperty("ai.model.type");
		String apiKey = environment.getProperty(type + ".api.key");
		String modelName = environment.getProperty(type + ".model.name");
		String baseUrl = environment.getProperty(type + ".base.url");
		if (apiKey == null || modelName == null || baseUrl == null) {
			throw new IllegalStateException(String.format(
					"Missing config: type=%s, apiKey=%s, modelName=%s, baseUrl=%s", type, apiKey, modelName, baseUrl));
		}
		return OpenAiChatModel.builder().apiKey(apiKey).modelName(modelName).baseUrl(baseUrl).logRequests(true)
				.logResponses(true).responseFormat("json_object").build();
	}
}