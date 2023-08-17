package com.example.demo.dto;

import com.example.demo.model.ParticipatingChallengeEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
//유저의 챌린지 참여 정보
public class ParticipatingChallengeDTO {
	private String id;
	private String challengeName;
	private String userName;
	
	//유저의 챌린지 성공 여부
	private boolean success;
	
	public ParticipatingChallengeDTO(final ParticipatingChallengeEntity entity) {
		this.id = entity.getId();
		this.challengeName = entity.getChallenge().getTitle();
		this.userName = entity.getUser().getEmail();
		
		this.success = entity.isSuccess();
	}
	
}