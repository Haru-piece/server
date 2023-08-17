package com.example.demo.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.example.demo.model.UserEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
	
	private Integer createCount;
	private Integer participateCount;
	
	private List<String> recentViewChallengeId;
	
	private List<String> participatingChallengeIds;
	
	private List<String> badgeNames;

	public UserDTO(final UserEntity entity) {
		this.id = entity.getId();
		this.username = entity.getUsername();
		this.password = entity.getPassword();
		this.recentViewChallengeId = entity.getRecentViewChallengeId();
		this.participatingChallengeIds = entity.getParticipatingChallengeEntities().stream()
												.map(participatingChallenge -> participatingChallenge.getChallenge().getId())
												.collect(Collectors.toList());
		this.badgeNames = entity.getParticipantWhoHasBadge().stream()
							.map(participantWhoHasBadge -> participantWhoHasBadge.getBadge().getName())
							.collect(Collectors.toList());
		this.createCount = entity.getCreateCount();
		this.participateCount = entity.getParticipateCount();
	}
	
}