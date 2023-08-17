package com.example.demo.dto;

import com.example.demo.model.ParticipantWhoHasBadgeEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
//뱃지를 가진 유저의 정보
public class ParticipantWhoHasBadgeDTO {
	private String id;
	private String badgeName;
	private String userName;
	private String userId;
	
	public ParticipantWhoHasBadgeDTO(final ParticipantWhoHasBadgeEntity entity) {
		this.id = entity.getId();
		this.badgeName = entity.getBadge().getName();
		this.userName = entity.getUser().getEmail();
		this.userId = entity.getUser().getId();
	}
	
}
