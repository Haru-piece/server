package com.example.demo.dto;

import com.example.demo.model.ChallengeEntity;
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
public class ChallengeDTO {
	private String id;
	private String title;
	private boolean done;
	//추가한 날짜
	private LocalDateTime addedDate;
	
	//챌린지에 참여하고 있는 사용자 수
	private Integer participantCount;
	
	//챌린지에 참여하고 있는 사용자의 id
	private List<String> participantIds;
	
	//챌린지의 카테고리
	private String category;
	
	public ChallengeDTO(final ChallengeEntity entity) {
		this.id = entity.getId();
		this.title = entity.getTitle();
		this.done = entity.isDone();
		this.addedDate = entity.getAddedDate();
		this.participantCount = entity.getParticipantCount();
		this.participantIds = entity.getParticipatingChallengeEntities().stream()
									.map(participatingChallenge -> participatingChallenge.getUser().getId())
									.collect(Collectors.toList());
		this.category = entity.getCategory();
	}
	
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

	
}


