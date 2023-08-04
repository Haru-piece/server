package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.example.demo.model.ParticipatingChallengeEntity;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParticipatingChallengeDTO {
	private String id;
	private String challengeName;
	private String userName;
	
	public ParticipatingChallengeDTO(final ParticipatingChallengeEntity entity) {
		this.id = entity.getId();
		this.challengeName = entity.getChallenge().getTitle();
		this.userName = entity.getUser().getEmail();
	}
	
}