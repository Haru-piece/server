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
	
	//이미지의 파일 이름
	private String filename;

	//이미지의 파일 경로
	private String filepath;
	
	public ImageDTO(final ImageEntity entity) {
		this.id = entity.getId();
		this.filename = entity.getFilename();
		this.filepath = entity.getFilepath();
	}

}
