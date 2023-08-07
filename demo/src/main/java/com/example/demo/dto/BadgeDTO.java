package com.example.demo.dto;

import com.example.demo.model.BadgeEntity;
import com.example.demo.model.ParticipatingChallengeEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;  

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BadgeDTO {
	private String id;
	private String name;
	
	
	public BadgeDTO(final BadgeEntity entity) {
		this.id = entity.getId();
		this.name = entity.getName();
	}

	/*
	public static ChallengeEntity toEntity(final ChallengeDTO dto) {
		return ChallengeEntity.builder()
						.id(dto.getId())
						.title(dto.getTitle())
						.done(dto.isDone())
						//현재 Date 추가
						.addedDate(LocalDateTime.now())
						.participantCount(0)
						.category(dto.getCategory())
						.participatingChallengeEntities(new ArrayList<ParticipatingChallengeEntity>())
						
						.build();
	}
	*/

	
}


