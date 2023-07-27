package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.example.demo.model.UserEntity;
import com.example.demo.model.ChallengeEntity;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
	private String token;
	private String email;
	private String username;
	private String password;
	private String id;
	
	private String challengeEntityTitle;
	private List<String> recentViewChallengeId;
	

	public UserDTO(final UserEntity entity) {
		this.id = entity.getId();
		this.password = entity.getPassword();
		this.challengeEntityTitle = entity.getChallenge().getTitle();
		this.recentViewChallengeId = entity.getRecentViewChallengeId();
	}
	
}