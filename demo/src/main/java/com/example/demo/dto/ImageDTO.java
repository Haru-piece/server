package com.example.demo.dto;

import com.example.demo.model.ImageEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ImageDTO {
	private String id;
	
	//�̹����� ���� �̸�
	private String filename;

	//�̹����� ���� ���
	private String filepath;
	
	public ImageDTO(final ImageEntity entity) {
		this.id = entity.getId();
		this.filename = entity.getFilename();
		this.filepath = entity.getFilepath();
	}

}
