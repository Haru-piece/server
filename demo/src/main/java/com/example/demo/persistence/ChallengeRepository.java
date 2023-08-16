package com.example.demo.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.example.demo.model.ChallengeEntity;
import com.example.demo.model.UserEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChallengeRepository extends JpaRepository<ChallengeEntity, String>{
	List<ChallengeEntity> findByUserId(String userId);
	List<ChallengeEntity> findByCategory(String category);
	List<ChallengeEntity> findByCategoryOrderByParticipantCountDesc(String category);
	
	
	//JPA N + 1문제를 해결하기 위해, FETCH JOIN을 사용하였다.
	@Query("SELECT c FROM ChallengeEntity c JOIN FETCH c.participatingChallengeEntities WHERE c.id = :challengeId")
	Optional<ChallengeEntity> findByIdWithParticipants(@Param("challengeId") String challengeId);
	
	@Query("SELECT DISTINCT c FROM ChallengeEntity c " +
	           "JOIN FETCH c.participatingChallengeEntities pc " +
	           "WHERE pc.user = :user")
	List<ChallengeEntity> findByParticipants(@Param("user") UserEntity userEntity);
	
	
}


