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
import java.util.Iterator;

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
	
	//특정 Challenge에 가입할 때
	public List<ChallengeEntity> participateChallenge(final ChallengeEntity challengeEntity,
													  final String userId){
		Optional<ChallengeEntity> original = challengeRepository.findById(challengeEntity.getId());
		
		if(original.isPresent()) {
			ChallengeEntity editedChallengeEntity = original.get();
			
			//challenge 사용자 수 증가
			editedChallengeEntity.setParticipantCount(editedChallengeEntity.getParticipantCount() + 1);
			
			//challenge에 가입할 때 관계 설정
			final UserEntity challengeUserEntity = userRepository.findById(userId).get();
			saveRelationBetweenChallengeAndUser(editedChallengeEntity, challengeUserEntity);
		}
		
		return challengeRepository.findAll();
	}
	
	//가입한 특정 Challenge에서 나올 때
	public List<ChallengeEntity> getOutFromChallenge(final ChallengeEntity challengeEntity,
			  final String userId){
		
		final UserEntity challengeUserEntity = userRepository.findById(userId).get();
		
		//유저 정보에서 참여 중인 챌린지를 null로 설정한다.
		challengeUserEntity.setChallenge(null);
		
		userRepository.save(challengeUserEntity);
		
		//챌린지에서 참여 중인 유저의 id를 삭제한다.
		Optional<ChallengeEntity> original = challengeRepository.findById(challengeEntity.getId());
		final ChallengeEntity editedChallengeEntity = original.get();
		
		List<UserEntity> challengers = editedChallengeEntity.getChallengers();
		Iterator<UserEntity> iterator = challengers.iterator();
		
		while (iterator.hasNext()) {
            UserEntity userEntity = iterator.next();
            
            if (userEntity.getId().equals(userId)) {
                iterator.remove();
            }
        }
		
		//challenge 사용자 수 감소
		editedChallengeEntity.setParticipantCount(editedChallengeEntity.getParticipantCount() - 1);
				
		challengeRepository.save(editedChallengeEntity);
		
		return challengeRepository.findAll();
	}
	

	//Challenge와 User 간 관계 설정
	//1. 유저에 도전하는 Challenge의 정보 저장
	//2. Challenge에 해당 Challenge에 참여한 유저의 정보 저장
	public void saveRelationBetweenChallengeAndUser(ChallengeEntity challengeEntity, UserEntity challengeUserEntity) {
		//1.
		challengeUserEntity.setChallenge(challengeEntity);
		userRepository.save(challengeUserEntity);
		
		//2.
		challengeEntity.getChallengers().add(challengeUserEntity);
		
		challengeRepository.save(challengeEntity);
	}
	
	// Create
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
	
	// 최근 조회한 챌린지의 Id를 유저의 RecentViewChallengeId에 저장한다.
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
		return challengeRepository.findAll(Sort.by(Sort.Direction.DESC, "addedDate"));
	}
	
	// Retrieve All Challenges Sorted By Participants' Count
	public List<ChallengeEntity> retrieveAllSortedByParticipantCount() {
		return challengeRepository.findAll(Sort.by(Sort.Direction.DESC, "participantCount"));
	}
	
	public List<ChallengeEntity> retrieveAllByCategory(String category){
		return challengeRepository.findByCategory(category);
	}
	
	public List<ChallengeEntity> retrieveAllByCategoryAndParticipantCount(String category){
		return challengeRepository.findByCategoryOrderByParticipantCountDesc(category);
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
