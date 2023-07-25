package com.example.demo.dto;

import com.example.demo.model.ChallengeEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;  

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ChallengeDTO {
	private String id;
	private String title;
	private boolean done;
	private LocalDateTime addedDate;
	private Integer participantCount;
	
	public ChallengeDTO(final ChallengeEntity entity) {
		this.id = entity.getId();
		this.title = entity.getTitle();
		this.done = entity.isDone();
		this.addedDate = entity.getAddedDate();
		this.participantCount = entity.getParticipantCount();
	}
	
	public static ChallengeEntity toEntity(final ChallengeDTO dto) {
		return ChallengeEntity.builder()
						.id(dto.getId())
						.title(dto.getTitle())
						.done(dto.isDone())
						//���� Date �߰�
						.addedDate(LocalDateTime.now())
						
						//������ 0���� �����ؾ� �ϴµ�, �׽�Ʈ�ϱ� ���ؼ� dto���� �޾ƿ´�.
						.participantCount(dto.getParticipantCount())
						.build();
	}

	
}


