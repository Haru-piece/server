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
//������ ç���� ���� ����
public class ParticipatingChallengeDTO {
	private String id;
	private String challengeName;
	private String userName;
	
	//������ ç���� ���� ����
	private boolean success;
	
	public ParticipatingChallengeDTO(final ParticipatingChallengeEntity entity) {
		this.id = entity.getId();
		this.challengeName = entity.getChallenge().getTitle();
		this.userName = entity.getUser().getEmail();
		
		this.success = entity.isSuccess();
	}
	
}