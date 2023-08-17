package com.example.demo.persistence;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.ParticipatingChallengeEntity;

public interface ParticipatingChallengeRepository extends JpaRepository<ParticipatingChallengeEntity, String>{
	Optional<ParticipatingChallengeEntity> findByChallengeIdAndUserId(String ChallengeId, String userId);
}
