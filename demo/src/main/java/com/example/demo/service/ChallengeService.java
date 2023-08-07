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
			
		//1. ChallengerRepository�� ���ο� Challenge�� �����մϴ�.
		//2. �� ��, ���ο� Challenge�� ����Ϸ��� �������� ���ӿ� Create�� ������ ������ �����մϴ�.
		//3. Create�� ������ Challenge ������ �ش� Challenge�� �����մϴ�.
		
		//4. CreateCount�� �ϳ� �÷��ݴϴ�.
		//5. Create King�� ���ǿ� �����ϸ� Create King ������ �����մϴ�.
		
		String challengeUserId = challengeEntity.getUserId();
		
		log.info("Challenge User Id : {}", challengeEntity.getUserId());
		
		Optional<UserEntity> original = userRepository.findById(challengeUserId);
			
		if(original.isPresent()) {
			//challenge ����� �� �߰�
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
	
	//Challenge�� User �� ���� ����
	//1. ������ �����ϴ� Challenge�� ���� ����
	//2. Challenge�� �ش� Challenge�� ������ ������ ���� ����
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
	
	
	//Ư�� Challenge�� ������ ��
	public List<ChallengeEntity> participateChallenge(final ChallengeEntity challengeEntity,
													  final String userId){
		Optional<ChallengeEntity> original = challengeRepository.findById(challengeEntity.getId());
		
		if(original.isPresent()) {
			ChallengeEntity editedChallengeEntity = original.get();
			
			//challenge ����� �� ����
			editedChallengeEntity.setParticipantCount(editedChallengeEntity.getParticipantCount() + 1);
			
			//challenge�� ������ �� ���� ����
			final UserEntity challengeUserEntity = userRepository.findById(userId).get();
			saveRelationBetweenChallengeAndUser(editedChallengeEntity, challengeUserEntity);
		}
		
		return challengeRepository.findAll();
	}
	
	//������ Ư�� Challenge���� ���� ��
	public List<ChallengeEntity> getOutFromChallenge(final String participatingChallengeId){
		Optional<ParticipatingChallengeEntity> original = participatingChallengeRepository.findById(participatingChallengeId);
		
		if(original.isPresent()) {
			ParticipatingChallengeEntity pCEntity = original.get();
			
			//challenge ����� �� ����
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
	
	// Retrieve Ư�� entity
	public List<ChallengeEntity> retrieveSpecificChallenge(final ChallengeEntity entity, final String userId){
		String challengeId = entity.getId();
		List<ChallengeEntity> list = List.of(challengeRepository.findById(challengeId).get());
		
		addRecentViewChallengeId(userId, challengeId);
		return list;
	}
	
	// �ֱ� ��ȸ�� ç������ Id�� ������ RecentViewChallengeId�� �����Ѵ�.
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
