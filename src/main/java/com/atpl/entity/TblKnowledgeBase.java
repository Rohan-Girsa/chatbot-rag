package com.atpl.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "ai_knowledge_base")
public class TblKnowledgeBase {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String client;
	@Column(name = "page_no")
	private Integer pageNo;
	@Column(name = "file_path")
	private String filepath;
	private String text;

}