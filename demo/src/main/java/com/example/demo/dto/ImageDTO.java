package com.example.demo.dto;

import com.example.demo.model.BadgeEntity;
import com.example.demo.model.ImageEntity;

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
public class ImageDTO {
	private String id;
	
	//private String title;
	
	//private String content;
	
	private String filename;

	private String filepath;
	
	public ImageDTO(final ImageEntity entity) {
		this.id = entity.getId();
		//this.title = entity.getTitle();
		//this.content = entity.getContent();
		this.filename = entity.getFilename();
		this.filepath = entity.getFilepath();
	}

}
