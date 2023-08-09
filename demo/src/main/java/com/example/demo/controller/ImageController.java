package com.example.demo.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dto.ChallengeDTO;
import com.example.demo.dto.ImageDTO;
import com.example.demo.dto.ResponseDTO;
import com.example.demo.model.ChallengeEntity;
import com.example.demo.model.ImageEntity;
import com.example.demo.service.ChallengeService;
import com.example.demo.service.ImageService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("image")
public class ImageController {
	
	@Autowired
	private ImageService service;
	
	@PostMapping
	public String write(ImageEntity imageEntity, MultipartFile image) throws Exception{
		
		service.write(imageEntity, image);
		
		return "message";
	}
	
	@GetMapping("/all")
	public ResponseEntity<?> retrieveAllImageInfo(
			@AuthenticationPrincipal String userId) {
		// (1) 모든 이미지 리스트를 가져온다
		List<ImageEntity> entities = service.retrieveAllImageInfo();
		
		// (2) 자바 스트림을 이용해 리턴된 엔티티 리스트를 ImageDTO리스트로 변환한다.
		List<ImageDTO> dtos = entities.stream().map(ImageDTO::new).collect(Collectors.toList());
		
		// (3) 변환된 ImageDTO리스트를 이용해ResponseDTO를 초기화한다.
		ResponseDTO<ImageDTO> response = ResponseDTO.<ImageDTO>builder().data(dtos).build();
		
		// (4) ImageDTO를 리턴한다.
		return ResponseEntity.ok().body(response);
	}
	

}
