package com.example.demo.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.ParticipatingChallengeDTO;
import com.example.demo.dto.ResponseDTO;

import com.example.demo.model.ParticipatingChallengeEntity;
import com.example.demo.service.ParticipatingChallengeService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("relation")
public class ParticipatingChallengeController {
	
	@Autowired
	private ParticipatingChallengeService service;
	
	// Retrieve All Challenge
	@GetMapping("/all")
	public ResponseEntity<?> retrieveAllRelationList(
			@AuthenticationPrincipal String userId) {
		
		// (1) 엔티티를 리포지토리에서 가져온다.
		List<ParticipatingChallengeEntity> entities = service.retrieveAll();
		
		// (2) 자바 스트림을 이용해 리턴된 엔티티 리스트를 ChallengeDTO리스트로 변환한다.
		List<ParticipatingChallengeDTO> dtos = entities.stream().map(ParticipatingChallengeDTO::new).collect(Collectors.toList());

		// (3) 변환된 ChallengeDTO리스트를 이용해ResponseDTO를 초기화한다.
		ResponseDTO<ParticipatingChallengeDTO> response = ResponseDTO.<ParticipatingChallengeDTO>builder().data(dtos).build();

		// (4) ResponseDTO를 리턴한다.
		return ResponseEntity.ok().body(response);
	}
	

}
