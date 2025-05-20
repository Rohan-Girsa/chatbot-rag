package com.atpl.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.atpl.entity.TblKnowledgeBase;

@Repository
public interface TblKnowledgeBaseRepository extends JpaRepository<TblKnowledgeBase, Long> {

	Optional<TblKnowledgeBase> findByClientAndPageNo(String client, Integer pageNo);

}
