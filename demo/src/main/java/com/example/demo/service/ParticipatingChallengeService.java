package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.ChallengeEntity;
import com.example.demo.model.ParticipatingChallengeEntity;
import com.example.demo.model.UserEntity;
import com.example.demo.persistence.ChallengeRepository;
import com.example.demo.persistence.ParticipatingChallengeRepository;
import com.example.demo.persistence.UserRepository;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
public class ParticipatingChallengeService {
	@Autowired
	private ParticipatingChallengeRepository participatingChallengeRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ChallengeRepository challengeRepository;
	
	// ��� '���� � ç������ �����ϰ� �ִ���'�� ������ ����
	public List<ParticipatingChallengeEntity> retrieveAll() {
		return participatingChallengeRepository.findAll();
	}
	
	// ������ �����ϰ� �ִ� ��� ç�������� ����Ʈ�� ���� 
	public List<ChallengeEntity> retrieveParticipateAll(String userId){
		Optional<UserEntity> original = userRepository.findById(userId);
		
		if(original.isPresent()) {
			UserEntity userEntity = original.get();
			
			//Fetch Join�� Ȱ���Ͽ� JPA N + 1 ���� �ذ�
			
			//���� ����
			/*
			return userEntity.getParticipatingChallengeEntities().stream()
							 .map(participatingChallenge -> participatingChallenge.getChallenge())
							 .collect(Collectors.toList());
			*/
			
			//����ȭ�� ���� ����
			return challengeRepository.findByParticipants(userEntity);
		
		}
		else throw new RuntimeException("userId�� �߸� �Է��߾��!");
	}
	
	// ç�������� ���� �����ߴ��� ���θ� ����
	public List<ParticipatingChallengeEntity> retrieveSuccessInfoFromChallenge(String challengeId){
		//Fetch Join�� Ȱ���Ͽ� JPA N + 1 ���� �ذ�
		
		//���� ����
		//Optional<ChallengeEntity> original = challengeRepository.findById(challengeId);
		
		//����ȭ�� ���� ����
		Optional<ChallengeEntity> original = challengeRepository.findByIdWithParticipants(challengeId);
		
		if(original.isPresent()) {
			ChallengeEntity challengeEntity = original.get();
			return challengeEntity.getParticipatingChallengeEntities();
		}
		
		else throw new RuntimeException("challengeId�� �߸� �Է��߾��!");
		
	}
	
	// ç������ ���� ���� ������Ʈ
	public List<ParticipatingChallengeEntity> updateSuccessInfoFromChallenge(String challengeId, 
																			 String userId){
		Optional<ParticipatingChallengeEntity> original = participatingChallengeRepository.findByChallengeIdAndUserId(challengeId, userId);
			
		if(original.isPresent()) {
			ParticipatingChallengeEntity pCEntity = original.get();
			
			//�������� �ٲ��ش�.
			pCEntity.setSuccess(true);
			//�ٲ��� �κ� ����
			participatingChallengeRepository.save(pCEntity);
			
			return retrieveSuccessInfoFromChallenge(challengeId);
		}
			
		else throw new RuntimeException("challengeId�� userId�� �߸� �Է��߾��!");
			
	}


}
