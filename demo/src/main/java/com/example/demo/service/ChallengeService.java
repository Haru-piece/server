package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

import com.example.demo.model.ChallengeEntity;
import com.example.demo.persistence.ChallengeRepository;
import com.example.demo.model.UserEntity;
import com.example.demo.persistence.UserRepository;
import com.example.demo.model.ParticipatingChallengeEntity;
import com.example.demo.persistence.ParticipatingChallengeRepository;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ChallengeService {
	
	
	@Autowired
	private ChallengeRepository challengeRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ParticipatingChallengeRepository participatingChallengeRepository;
	
	@Autowired
	private BadgeGetConditionChecker badgeGetConditonChecker;
	
	// Create With Relation
	public List<ChallengeEntity> createWithRelation(final ChallengeEntity challengeEntity) {
		
		// Validations
		validate(challengeEntity);
			
		//1. ChallengerRepository에 새로운 Challenge를 저장합니다.
		//2. 이 때, 새로운 Challenge를 사용하려는 유저들의 모임에 Create한 유저의 정보를 저장합니다.
		//3. Create한 유저의 Challenge 내역에 해당 Challenge를 저장합니다.
		
		//4. CreateCount를 하나 올려줍니다.
		//5. Create King의 조건에 부합하면 Create King 뱃지를 수여합니다.
		
		String challengeUserId = challengeEntity.getUserId();
		
		log.info("Challenge User Id : {}", challengeEntity.getUserId());
		
		Optional<UserEntity> original = userRepository.findById(challengeUserId);
			
		if(original.isPresent()) {
			//challenge 사용자 수 추가
			challengeEntity.setParticipantCount(challengeEntity.getParticipantCount() + 1);
			
			//1.
			//challengeRepository.save(challengeEntity);
				
			//log.info("Entity Id : {} is saved.", challengeEntity.getId());
			
			
			
			final UserEntity challengeUserEntity = original.get();
			//4.
			challengeUserEntity.setCreateCount(challengeUserEntity.getCreateCount() + 1);
			
			//2. + 3.
			saveRelationBetweenChallengeAndUser(challengeEntity, challengeUserEntity);
			
			//5.
			badgeGetConditonChecker.createKing(challengeUserEntity);
		}
		
		return challengeRepository.findByUserId(challengeEntity.getUserId());
	}
	
	//Challenge와 User 간 관계 설정
	//1. 유저에 도전하는 Challenge의 정보 저장
	//2. Challenge에 해당 Challenge에 참여한 유저의 정보 저장
	public void saveRelationBetweenChallengeAndUser(ChallengeEntity challengeEntity, UserEntity userEntity) {
		ParticipatingChallengeEntity pCEntity = ParticipatingChallengeEntity.builder().build();
		
		pCEntity.setChallenge(challengeEntity);
		challengeEntity.getParticipatingChallengeEntities().add(pCEntity);
		
		pCEntity.setUser(userEntity);
		userEntity.getParticipatingChallengeEntities().add(pCEntity);
		
		challengeRepository.save(challengeEntity);
		userRepository.save(userEntity);
		participatingChallengeRepository.save(pCEntity);
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
	public List<ChallengeEntity> getOutFromChallenge(final String participatingChallengeId){
		Optional<ParticipatingChallengeEntity> original = participatingChallengeRepository.findById(participatingChallengeId);
		
		if(original.isPresent()) {
			ParticipatingChallengeEntity pCEntity = original.get();
			
			//challenge 사용자 수 감소
			pCEntity.getChallenge().setParticipantCount(pCEntity.getChallenge().getParticipantCount() - 1);
			challengeRepository.save(pCEntity.getChallenge());
			
			pCEntity.getChallenge().getParticipatingChallengeEntities().remove(pCEntity);
			pCEntity.getUser().getParticipatingChallengeEntities().remove(pCEntity);
			
			participatingChallengeRepository.delete(pCEntity);
		}
		
		return challengeRepository.findAll();
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
