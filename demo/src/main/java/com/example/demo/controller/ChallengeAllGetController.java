package com.example.demo.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.ChallengeDTO;
import com.example.demo.dto.ResponseDTO;
import com.example.demo.model.ChallengeEntity;
import com.example.demo.service.ChallengeService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("challenge")
public class ChallengeAllGetController {
	
	@Autowired
	private ChallengeService service;
	
	// Retrieve All Challenge
	@GetMapping("/all")
	public ResponseEntity<?> retrieveAllChallengeList(
			@AuthenticationPrincipal String userId) {
		// (1) 서비스 메서드의 retrieveAll메서드를 사용해 모든 Challenge 리스트를 가져온다
		List<ChallengeEntity> entities = service.retrieveAll();

		// (2) 자바 스트림을 이용해 리턴된 엔티티 리스트를 ChallengeDTO리스트로 변환한다.
		List<ChallengeDTO> dtos = entities.stream().map(ChallengeDTO::new).collect(Collectors.toList());

		// (3) 변환된 ChallengeDTO리스트를 이용해ResponseDTO를 초기화한다.
		ResponseDTO<ChallengeDTO> response = ResponseDTO.<ChallengeDTO>builder().data(dtos).build();

		// (4) ResponseDTO를 리턴한다.
		return ResponseEntity.ok().body(response);
	}
	
	// Retrieve All Challenge Sorted By Added Date
	@GetMapping("/all/date")
	public ResponseEntity<?> retrieveAllChallengeListSortedByDate(
				@AuthenticationPrincipal String userId) {
		// (1) 서비스 메서드의 retrieveAllSortedByDate메서드를 사용해 모든 Challenge 리스트를 날짜 순으로 가져온다
		List<ChallengeEntity> entities = service.retrieveAllSortedByDate();

		// (2) 자바 스트림을 이용해 리턴된 엔티티 리스트를 ChallengeDTO리스트로 변환한다.
		List<ChallengeDTO> dtos = entities.stream().map(ChallengeDTO::new).collect(Collectors.toList());

		// (3) 변환된 ChallengeDTO리스트를 이용해ResponseDTO를 초기화한다.
		ResponseDTO<ChallengeDTO> response = ResponseDTO.<ChallengeDTO>builder().data(dtos).build();

		// (4) ResponseDTO를 리턴한다.
		return ResponseEntity.ok().body(response);
	}
	
	// Retrieve All Challenge Sorted By Participant Count
	@GetMapping("/all/count")
	public ResponseEntity<?> retrieveAllChallengeListSortedByParticipantCount(
					@AuthenticationPrincipal String userId) {
		// (1) 서비스 메서드의 retrieveAllSortedByParticipantCount메서드를 사용해 참여자 수 순으로 모든 Challenge 리스트를 가져온다
		List<ChallengeEntity> entities = service.retrieveAllSortedByParticipantCount();

		// (2) 자바 스트림을 이용해 리턴된 엔티티 리스트를 ChallengeDTO리스트로 변환한다.
		List<ChallengeDTO> dtos = entities.stream().map(ChallengeDTO::new).collect(Collectors.toList());

		// (3) 변환된 ChallengeDTO리스트를 이용해ResponseDTO를 초기화한다.
		ResponseDTO<ChallengeDTO> response = ResponseDTO.<ChallengeDTO>builder().data(dtos).build();

		// (4) ResponseDTO를 리턴한다.
		return ResponseEntity.ok().body(response);
	}

}
