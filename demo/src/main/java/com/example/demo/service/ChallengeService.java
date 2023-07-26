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
	public List<ChallengeEntity> createWithRelation(ChallengeEntity entity) {
		// Validations
		validate(entity);
			
		//1. ChallengerRepository�� ���ο� Challenge�� �����մϴ�.
		//2. �� ��, ���ο� Challenge�� ����Ϸ��� �������� ���ӿ� Create�� ������ ������ �����մϴ�.
		//3. Create�� ������ Challenge ������ �ش� Challenge�� �����մϴ�.
		
		String challengeUserId = entity.getUserId();
		Optional<UserEntity> original = userRepository.findById(challengeUserId);
			
		if(original.isPresent()) {
			final UserEntity challengeUserEntity = original.get();
					
			//1.
			challengeRepository.save(entity);
				
			log.info("Entity Id : {} is saved.", entity.getId());
			
			//3.
			challengeUserEntity.setChallenge(entity);
			userRepository.save(challengeUserEntity);
			
			//2.
			entity.getChallengers().add(challengeUserEntity);
		}
		
		return challengeRepository.findByUserId(entity.getUserId());
	}

	// Create
	// �����Դϴ�. CreateWithRelation�� �������� ���� ����� ����Դϴ�.
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
