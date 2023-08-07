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
public class BadgeGetConditionChecker {
	
	@Autowired
	private BadgeGiver badgeGiver;
	
	public void createKing(final UserEntity userEntity) {
		Integer conditionForCreateKing = 5;
		
		if(userEntity.getCreateCount() == conditionForCreateKing) {
			badgeGiver.becomeCreateKing(userEntity);
		}
	}

}
