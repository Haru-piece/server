package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.example.demo.model.ParticipantWhoHasBadgeEntity;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParticipantWhoHasBadgeDTO {
	private String id;
	private String badgeName;
	private String userName;
	
	public ParticipantWhoHasBadgeDTO(final ParticipantWhoHasBadgeEntity entity) {
		this.id = entity.getId();
		this.badgeName = entity.getBadge().getName();
		this.userName = entity.getUser().getEmail();
	}
	
}
