package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

import com.example.demo.model.ImageEntity;
import com.example.demo.persistence.ImageRepository;

import java.io.File;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class ImageService {
	
	@Autowired
	private ImageRepository imageRepository;

	public void write(ImageEntity imageEntity, MultipartFile image) throws Exception{
		String projectPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\images";
		
		UUID uuid = UUID.randomUUID();
		
		String fileName = uuid + "_" + image.getOriginalFilename();
		
		File saveFile = new File(projectPath, fileName);
		
		image.transferTo(saveFile);
		
		imageEntity.setFilename(fileName);
		imageEntity.setFilepath("/images/" + fileName);
		
		imageRepository.save(imageEntity);
	}
	
	public List<ImageEntity> retrieveAllImageInfo(){
		return imageRepository.findAll();
	}


}
