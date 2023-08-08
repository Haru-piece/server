package com.example.demo.service;

import java.util.List;

import com.example.demo.model.BadgeEntity;
import com.example.demo.persistence.BadgeRepository;

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

import com.example.demo.model.ParticipantWhoHasBadgeEntity;
import com.example.demo.persistence.ParticipantWhoHasBadgeRepository;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class BadgeGiver {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BadgeRepository badgeRepository;
	
	@Autowired
	private ParticipantWhoHasBadgeRepository participantWhoHasBadgeRepository;
	
	// createKing (챌린지를 5개 만들었을 때 부여받는 뱃지)
	public void becomeCreateKing(final UserEntity userEntity) {
		ParticipantWhoHasBadgeEntity pWHBadge = ParticipantWhoHasBadgeEntity.builder().build();
		
		pWHBadge.setUser(userEntity);
		userEntity.getParticipantWhoHasBadge().add(pWHBadge);
		
		BadgeEntity createKing = badgeRepository.findByName("CreateKing");
		
		pWHBadge.setBadge(createKing);
		createKing.getParticipantWhoHasBadge().add(pWHBadge);
		
		badgeRepository.save(createKing);
		userRepository.save(userEntity);
		participantWhoHasBadgeRepository.save(pWHBadge);
	}
	
	// participateKing (챌린지를 5개 참여했을 때 부여받는 뱃지)
	public void becomeParticipateKing(final UserEntity userEntity) {
		ParticipantWhoHasBadgeEntity pWHBadge = ParticipantWhoHasBadgeEntity.builder().build();
			
		pWHBadge.setUser(userEntity);
		userEntity.getParticipantWhoHasBadge().add(pWHBadge);
			
		BadgeEntity participateKing = badgeRepository.findByName("ParticipateKing");
			
		pWHBadge.setBadge(participateKing);
		participateKing.getParticipantWhoHasBadge().add(pWHBadge);
			
		badgeRepository.save(participateKing);
		userRepository.save(userEntity);
		participantWhoHasBadgeRepository.save(pWHBadge);
	}	
	
	// Retrieve All Badges
	public List<BadgeEntity> retrieveAll() {
		return badgeRepository.findAll();
	}
	
	// Retrieve All Relations of Badge
	public List<ParticipantWhoHasBadgeEntity> retrieveBadgeRelationAll(){
		return participantWhoHasBadgeRepository.findAll();
	}

}
