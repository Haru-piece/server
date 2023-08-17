package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.UserEntity;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
//사용자가 뱃지를 받을 수 있는 조건인지 아닌지 체크
public class BadgeGetConditionChecker {
	
	@Autowired
	private BadgeGiver badgeGiver;
	
	public void createKing(final UserEntity userEntity) {
		Integer conditionForCreateKing = 5;
		
		if(userEntity.getCreateCount() == conditionForCreateKing) {
			badgeGiver.becomeCreateKing(userEntity);
		}
	}
	
	public void participateKing(final UserEntity userEntity) {
		Integer conditionForParticipateKing = 5;
		
		if(userEntity.getParticipateCount() == conditionForParticipateKing) {
			badgeGiver.becomeParticipateKing(userEntity);
		}
	}

	

}
