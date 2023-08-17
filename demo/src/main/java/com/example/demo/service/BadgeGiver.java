package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.BadgeEntity;
import com.example.demo.model.ParticipantWhoHasBadgeEntity;
import com.example.demo.model.UserEntity;
import com.example.demo.persistence.BadgeRepository;
import com.example.demo.persistence.ParticipantWhoHasBadgeRepository;
import com.example.demo.persistence.UserRepository;

import lombok.extern.slf4j.Slf4j;

//뱃지를 부여
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
	
	// 모든 뱃지의 정보 리턴
	public List<BadgeEntity> retrieveAll() {
		return badgeRepository.findAll();
	}
	
	// 누가 어떤 뱃지를 가지고 있는지의 정보 리턴
	public List<ParticipantWhoHasBadgeEntity> retrieveBadgeRelationAll(){
		return participantWhoHasBadgeRepository.findAll();
	}

}
