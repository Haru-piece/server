package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.example.demo.model.UserEntity;
import com.example.demo.model.ChallengeEntity;

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
	

	public UserDTO(final UserEntity entity) {
		this.id = entity.getId();
		this.password = entity.getPassword();
		this.challengeEntityTitle = entity.getChallenge().getTitle();
	}
	
}