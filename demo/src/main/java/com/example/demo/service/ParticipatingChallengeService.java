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
	
	// 모든 '누가 어떤 챌린지에 참여하고 있는지'의 정보를 리턴
	public List<ParticipatingChallengeEntity> retrieveAll() {
		return participatingChallengeRepository.findAll();
	}
	
	// 유저가 참여하고 있는 모든 챌린지들의 리스트를 리턴 
	public List<ChallengeEntity> retrieveParticipateAll(String userId){
		Optional<UserEntity> original = userRepository.findById(userId);
		
		if(original.isPresent()) {
			UserEntity userEntity = original.get();
			
			//Fetch Join을 활용하여 JPA N + 1 문제 해결
			
			//이전 리턴
			/*
			return userEntity.getParticipatingChallengeEntities().stream()
							 .map(participatingChallenge -> participatingChallenge.getChallenge())
							 .collect(Collectors.toList());
			*/
			
			//최적화한 이후 리턴
			return challengeRepository.findByParticipants(userEntity);
		
		}
		else throw new RuntimeException("userId를 잘못 입력했어요!");
	}
	
	// 챌린지에서 누가 성공했는지 여부를 리턴
	public List<ParticipatingChallengeEntity> retrieveSuccessInfoFromChallenge(String challengeId){
		//Fetch Join을 활용하여 JPA N + 1 문제 해결
		
		//이전 리턴
		//Optional<ChallengeEntity> original = challengeRepository.findById(challengeId);
		
		//최적화한 이후 리턴
		Optional<ChallengeEntity> original = challengeRepository.findByIdWithParticipants(challengeId);
		
		if(original.isPresent()) {
			ChallengeEntity challengeEntity = original.get();
			return challengeEntity.getParticipatingChallengeEntities();
		}
		
		else throw new RuntimeException("challengeId를 잘못 입력했어요!");
		
	}
	
	// 챌린지에 성공 여부 업데이트
	public List<ParticipatingChallengeEntity> updateSuccessInfoFromChallenge(String challengeId, 
																			 String userId){
		Optional<ParticipatingChallengeEntity> original = participatingChallengeRepository.findByChallengeIdAndUserId(challengeId, userId);
			
		if(original.isPresent()) {
			ParticipatingChallengeEntity pCEntity = original.get();
			
			//성공으로 바꿔준다.
			pCEntity.setSuccess(true);
			//바꿔준 부분 저장
			participatingChallengeRepository.save(pCEntity);
			
			return retrieveSuccessInfoFromChallenge(challengeId);
		}
			
		else throw new RuntimeException("challengeId나 userId를 잘못 입력했어요!");
			
	}


}
