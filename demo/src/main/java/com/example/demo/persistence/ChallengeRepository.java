package com.example.demo.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.model.ChallengeEntity;

import java.util.List;

@Repository
public interface ChallengeRepository extends JpaRepository<ChallengeEntity, String>{
	List<ChallengeEntity> findByUserId(String userId);
	List<ChallengeEntity> findByCategory(String category);
	List<ChallengeEntity> findByCategoryOrderByParticipantCountDesc(String category);
}


