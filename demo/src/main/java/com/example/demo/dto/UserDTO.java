package com.example.demo.dto;

import com.example.demo.model.UserEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

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
	
	private List<String> recentViewChallengeId;
	
	private List<String> participatingChallengeIds;

	public UserDTO(final UserEntity entity) {
		this.id = entity.getId();
		this.password = entity.getPassword();
		this.recentViewChallengeId = entity.getRecentViewChallengeId();
		this.participatingChallengeIds = entity.getParticipatingChallengeEntities().stream()
												.map(participatingChallenge -> participatingChallenge.getChallenge().getId())
												.collect(Collectors.toList());
	}
	
}