package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

import com.example.demo.model.ParticipatingChallengeEntity;
import com.example.demo.persistence.ParticipatingChallengeRepository;

import com.example.demo.model.UserEntity;
import com.example.demo.persistence.UserRepository;

import com.example.demo.model.ChallengeEntity;
import com.example.demo.persistence.ChallengeRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Slf4j
@Service
public class ParticipatingChallengeService {
	@Autowired
	private ParticipatingChallengeRepository participatingChallengeRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ChallengeRepository challengeRepository;
	
	// Retrieve All participating Challenge Entity
	public List<ParticipatingChallengeEntity> retrieveAll() {
		return participatingChallengeRepository.findAll();
	}
	
	// Retrieve user's All participating Challenge Entity 
	public List<ChallengeEntity> retrieveParticipateAll(String userId){
		Optional<UserEntity> original = userRepository.findById(userId);
		
		if(original.isPresent()) {
			UserEntity userEntity = original.get();
			return userEntity.getParticipatingChallengeEntities().stream()
							 .map(participatingChallenge -> participatingChallenge.getChallenge())
							 .collect(Collectors.toList());
			
		}
		else throw new RuntimeException("userId를 잘못 입력했어요!");
	}
	
	// Retrieve Success Info From Challenge
	public List<ParticipatingChallengeEntity> retrieveSuccessInfoFromChallenge(String challengeId){
		Optional<ChallengeEntity> original = challengeRepository.findById(challengeId);
		
		if(original.isPresent()) {
			ChallengeEntity challengeEntity = original.get();
			return challengeEntity.getParticipatingChallengeEntities();
		}
		
		else throw new RuntimeException("challengeId를 잘못 입력했어요! ㅡㅡ");
		
	}

}
