package com.example.demo.persistence;

import com.example.demo.model.ParticipatingChallengeEntity;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ParticipatingChallengeRepository extends JpaRepository<ParticipatingChallengeEntity, String>{
	Optional<ParticipatingChallengeEntity> findByChallengeIdAndUserId(String ChallengeId, String userId);
}
