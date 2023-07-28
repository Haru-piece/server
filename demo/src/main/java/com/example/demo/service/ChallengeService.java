package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

import com.example.demo.model.ChallengeEntity;
import com.example.demo.persistence.ChallengeRepository;
import com.example.demo.model.UserEntity;
import com.example.demo.persistence.UserRepository;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ChallengeService {
	
	
	@Autowired
	private ChallengeRepository challengeRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	// Create With Relation
	public List<ChallengeEntity> createWithRelation(final ChallengeEntity challengeEntity) {
		
		// Validations
		validate(challengeEntity);
			
		//1. ChallengerRepository에 새로운 Challenge를 저장합니다.
		//2. 이 때, 새로운 Challenge를 사용하려는 유저들의 모임에 Create한 유저의 정보를 저장합니다.
		//3. Create한 유저의 Challenge 내역에 해당 Challenge를 저장합니다.
		
		String challengeUserId = challengeEntity.getUserId();
		
		log.info("Challenge User Id : {}", challengeEntity.getUserId());
		
		Optional<UserEntity> original = userRepository.findById(challengeUserId);
			
		if(original.isPresent()) {
			//challenge 사용자 수 추가
			challengeEntity.setParticipantCount(challengeEntity.getParticipantCount() + 1);
			
			//1.
			challengeRepository.save(challengeEntity);
				
			log.info("Entity Id : {} is saved.", challengeEntity.getId());
			
			
			//2. + 3.
			final UserEntity challengeUserEntity = original.get();
			saveRelationBetweenChallengeAndUser(challengeEntity, challengeUserEntity);
		}
		
		return challengeRepository.findByUserId(challengeEntity.getUserId());
	}
	
	public List<ChallengeEntity> participateChallenge(final ChallengeEntity challengeEntity,
													  final String userId){
		log.info("What we want to find : {}", challengeEntity.getId());
		
		Optional<ChallengeEntity> original = challengeRepository.findById(challengeEntity.getId());
		
		if(original.isPresent()) {
			ChallengeEntity editedChallengeEntity = original.get();
			//return createWithRelation(editedChallengeEntity);
			
			editedChallengeEntity.setParticipantCount(editedChallengeEntity.getParticipantCount() + 1);
			challengeRepository.save(editedChallengeEntity);
			
			final UserEntity challengeUserEntity = userRepository.findById(userId).get();
			saveRelationBetweenChallengeAndUser(editedChallengeEntity, challengeUserEntity);
			
			return challengeRepository.findAll();
		}
		
		else return null;
	}

	public void saveRelationBetweenChallengeAndUser(ChallengeEntity challengeEntity, UserEntity challengeUserEntity) {
		//3.
		challengeUserEntity.setChallenge(challengeEntity);
		userRepository.save(challengeUserEntity);
		
		//2.
		challengeEntity.getChallengers().add(challengeUserEntity);
	}
	
	// Create
	// 원본입니다. CreateWithRelation이 망가졌을 때를 대비한 백업입니다.
	public List<ChallengeEntity> create(final ChallengeEntity entity) {
		// Validations
		validate(entity);

		challengeRepository.save(entity);
		
		log.info("Entity Id : {} is saved.", entity.getId());
		
		return challengeRepository.findByUserId(entity.getUserId());
	}
	
	// Retrieve
	public List<ChallengeEntity> retrieve(final String userId) {
		return challengeRepository.findByUserId(userId);
	}
	
	// Retrieve 특정 entity
	public List<ChallengeEntity> retrieveSpecificChallenge(final ChallengeEntity entity, final String userId){
		String challengeId = entity.getId();
		List<ChallengeEntity> list = List.of(challengeRepository.findById(challengeId).get());
		
		addRecentViewChallengeId(userId, challengeId);
		return list;
	}
	
	public void addRecentViewChallengeId(String userId, String viewedChallengeId) {
		UserEntity userEntity = userRepository.findById(userId).get();
		
		List<String> recentViewChallengeId = userEntity.getRecentViewChallengeId();
		
		if(recentViewChallengeId.size() >= 5) recentViewChallengeId.remove(0);
		userEntity.getRecentViewChallengeId().add(viewedChallengeId);
		
		userRepository.save(userEntity);
	}
	
	// Update
	 
	// Delete
	
	// Retrieve All Challenges
	public List<ChallengeEntity> retrieveAll() {
		return challengeRepository.findAll();
	}
	
	// Retrieve All Challenges Sorted By Date
	public List<ChallengeEntity> retrieveAllSortedByDate() {
		return challengeRepository.findAll(Sort.by(Sort.Direction.ASC, "addedDate"));
	}
	
	// Retrieve All Challenges Sorted By Participants' Count
	public List<ChallengeEntity> retrieveAllSortedByParticipantCount() {
		return challengeRepository.findAll(Sort.by(Sort.Direction.DESC, "participantCount"));
	}
	

	// Validate
	public void validate(final ChallengeEntity entity) {
		if(entity == null) {
			log.warn("Entity cannot be null.");
			throw new RuntimeException("Entity cannot be null.");
		}

		if(entity.getUserId() == null) {
			log.warn("Unknown user.");
			throw new RuntimeException("Unknown user.");
		}
	}


}
