package com.example.demo.dto;

import com.example.demo.model.ChallengeEntity;
import com.example.demo.model.UserEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.format.DateTimeFormatter;
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
	//�߰��� ��¥
	private LocalDateTime addedDate;
	
	//ç������ �����ϰ� �ִ� ����� ��
	private Integer participantCount;
	
	//ç������ ī�װ�
	private String category;
	
	//ç������ �����ϰ� �ִ� �������� Id
	private List<String> challengerIds = new ArrayList<String>();
	
	public ChallengeDTO(final ChallengeEntity entity) {
		this.id = entity.getId();
		this.title = entity.getTitle();
		this.done = entity.isDone();
		this.addedDate = entity.getAddedDate();
		this.participantCount = entity.getParticipantCount();
		this.category = entity.getCategory();
		
		
		this.challengerIds = (entity.getChallengers()).stream()
													 .map(UserEntity::getId)
													 .collect(Collectors.toList());
	}
	
	public static ChallengeEntity toEntity(final ChallengeDTO dto) {
		return ChallengeEntity.builder()
						.id(dto.getId())
						.title(dto.getTitle())
						.done(dto.isDone())
						//���� Date �߰�
						.addedDate(LocalDateTime.now())
						.participantCount(0)
						.category(dto.getCategory())
						.challengers(new ArrayList<UserEntity>())
						.build();
	}

	
}


