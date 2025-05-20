package com.atpl.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.atpl.dto.ChatRequest;
import com.atpl.dto.ChatResponse;
import com.atpl.entity.TblKnowledgeBase;
import com.atpl.repository.TblKnowledgeBaseRepository;
import com.atpl.service.ChatbotService;
import com.atpl.service.DetailsFetcherService;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.DocumentSplitter;
import dev.langchain4j.data.document.splitter.DocumentSplitters;
import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.embedding.AllMiniLmL6V2EmbeddingModel;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChatbotServicelmpl implements ChatbotService {

	private final TblKnowledgeBaseRepository tblKnowledgeBaseRepository;
	private final ChatLanguageModel chatLanguageModel;

	private ContentRetriever contentRetriever;

	@Override
	public ChatResponse getAnswer(ChatRequest req) {
		TblKnowledgeBase knowledgeBase = null;
		try {
			Optional<TblKnowledgeBase> data = tblKnowledgeBaseRepository.findByClientAndPageNo(req.getClient(),
					req.getPageno());

			if (!data.isPresent()) {
				return null;
			}
			knowledgeBase = data.get();
			Document textDoc = new Document(knowledgeBase.getText());
			log.info("DETAILS DOCUMENT" + textDoc);
			DetailsFetcherService assistant = createAssistant(textDoc);
			ChatResponse chatResponse = assistant.matchDetails(req.getQuestion());
			return chatResponse;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private DetailsFetcherService createAssistant(Document jobDocument) {
		DocumentSplitter splitter = DocumentSplitters.recursive(300, 0);
		List<TextSegment> segments = splitter.split(jobDocument);
		AllMiniLmL6V2EmbeddingModel embeddingModel = new AllMiniLmL6V2EmbeddingModel();
		List<Embedding> embeddings = embeddingModel.embedAll(segments).content();
		EmbeddingStore<TextSegment> embeddingStore = new InMemoryEmbeddingStore<TextSegment>();
		embeddingStore.addAll(embeddings, segments);
		contentRetriever = EmbeddingStoreContentRetriever.builder().embeddingStore(embeddingStore)
				.embeddingModel(embeddingModel).build();
		return AiServices.builder(DetailsFetcherService.class).chatLanguageModel(chatLanguageModel)
				.contentRetriever(contentRetriever).build();
	}

}