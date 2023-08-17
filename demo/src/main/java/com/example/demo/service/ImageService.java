package com.example.demo.service;

import java.io.File;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.ImageEntity;
import com.example.demo.persistence.ImageRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ImageService {
	
	@Autowired
	private ImageRepository imageRepository;

	//이미지 추가
	public ImageEntity write(ImageEntity imageEntity, MultipartFile image) throws Exception{
		//for 로컬 서버
		//String projectPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\image";
		
		//for ec2 서버
		String projectPath = System.getProperty("user.dir") + "/image/";
		
		log.info(projectPath);
		
		UUID uuid = UUID.randomUUID();
		
		String fileName = uuid + "_" + image.getOriginalFilename();
		
		File saveFile = new File(projectPath, fileName);
		
		image.transferTo(saveFile);
		
		imageEntity.setFilename(fileName);
		imageEntity.setFilepath("/image/" + fileName);
		
		imageRepository.save(imageEntity);
	
		return imageEntity;
	}
	
	//모든 이미지 정보 리턴
	public List<ImageEntity> retrieveAllImageInfo(){
		return imageRepository.findAll();
	}


}
