package com.example.demo.dto;

import com.example.demo.model.BadgeEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BadgeDTO {
	private String id;
	
	//뱃지의 이름
	private String name;
	//뱃지의 이미지 경로
	private String imagePath;

	public BadgeDTO(final BadgeEntity entity) {
		this.id = entity.getId();
		this.name = entity.getName();
		this.imagePath = entity.getImagePath();
	}
}
