package com.example.demo.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.ChallengeDTO;
import com.example.demo.dto.ResponseDTO;
import com.example.demo.model.ChallengeEntity;
import com.example.demo.service.ChallengeService;
import com.example.demo.service.ParticipatingChallengeService;

import lombok.extern.slf4j.Slf4j;

//모든 챌린지를 리턴받는 컨트롤러
@Slf4j
@RestController
@RequestMapping("challenge")
public class ChallengeAllGetController {

	@Autowired
	private ChallengeService service;

	@Autowired
	private ParticipatingChallengeService pCService;

	// 모든 챌린지를 주어진 기준에 따라 정렬하여 리턴
	@GetMapping("/all")
	public ResponseEntity<?> retrieveAllChallengeList(@AuthenticationPrincipal String userId,
			@RequestParam(required = false) String sort) {

		// (1) sort는 정렬 기준이다. 정렬 기준에 맞춰 엔티티를 리포지토리에서 가져온다.
		List<ChallengeEntity> entities;
		if (sort.equals("date")) {
			entities = service.retrieveAllSortedByDate();
		} else if (sort.equals("count")) {
			entities = service.retrieveAllSortedByParticipantCount();
		} else {
			entities = service.retrieveAll();
		}

		// (2) 자바 스트림을 이용해 리턴된 엔티티 리스트를 ChallengeDTO리스트로 변환한다.
		List<ChallengeDTO> dtos = entities.stream().map(ChallengeDTO::new).collect(Collectors.toList());

		// (3) 변환된 ChallengeDTO리스트를 이용해ResponseDTO를 초기화한다.
		ResponseDTO<ChallengeDTO> response = ResponseDTO.<ChallengeDTO>builder().data(dtos).build();

		// (4) ResponseDTO를 리턴한다.
		return ResponseEntity.ok().body(response);
	}

	// 유저가 참여한 모든 챌린지들을 리턴
	@GetMapping("/all/participate")
	public ResponseEntity<?> retrieveAllParticipateChallenge(@AuthenticationPrincipal String userId) {
		// (1) 유저가 참여한 모든 챌린지를 가져온다
		List<ChallengeEntity> entities = pCService.retrieveParticipateAll(userId);

		// (2) 자바 스트림을 이용해 리턴된 엔티티 리스트를 ChallengeDTO리스트로 변환한다.
		List<ChallengeDTO> dtos = entities.stream().map(ChallengeDTO::new).collect(Collectors.toList());

		// (3) 변환된 ChallengeDTO리스트를 이용해ResponseDTO를 초기화한다.
		ResponseDTO<ChallengeDTO> response = ResponseDTO.<ChallengeDTO>builder().data(dtos).build();

		// (4) ResponseDTO를 리턴한다.
		return ResponseEntity.ok().body(response);
	}

	// 카테고리에 속해 있는 모든 챌린지 리턴
	@GetMapping("/all/category")
	public ResponseEntity<?> retrieveAllChallengeListByCategory(@AuthenticationPrincipal String userId,
			@RequestBody ChallengeDTO dto) {
		// (1) 주어진 카테고리에 속한 챌린지 리스트를 가져온다
		List<ChallengeEntity> entities = service.retrieveAllByCategory(dto.getCategory());

		// (2) 자바 스트림을 이용해 리턴된 엔티티 리스트를 ChallengeDTO리스트로 변환한다.
		List<ChallengeDTO> dtos = entities.stream().map(ChallengeDTO::new).collect(Collectors.toList());

		// (3) 변환된 ChallengeDTO리스트를 이용해ResponseDTO를 초기화한다.
		ResponseDTO<ChallengeDTO> response = ResponseDTO.<ChallengeDTO>builder().data(dtos).build();

		// (4) ResponseDTO를 리턴한다.
		return ResponseEntity.ok().body(response);
	}

	// 특정 카테고리에 속한 챌린지들을 조회 순으로 정렬하여 리턴
	@GetMapping("/all/categoryAndCount")
	public ResponseEntity<?> retrieveAllChallengeLitByCategoryAndCount(@AuthenticationPrincipal String userId,
			@RequestBody ChallengeDTO dto) {
		// (1) 서비스 메서드로 특정 카테고리에 속한 챌린지 리스트를 가져온다
		List<ChallengeEntity> entities = service.retrieveAllByCategoryAndParticipantCount(dto.getCategory());

		// (2) 자바 스트림을 이용해 리턴된 엔티티 리스트를 ChallengeDTO리스트로 변환한다.
		List<ChallengeDTO> dtos = entities.stream().map(ChallengeDTO::new).collect(Collectors.toList());

		// (3) 변환된 ChallengeDTO리스트를 이용해ResponseDTO를 초기화한다.
		ResponseDTO<ChallengeDTO> response = ResponseDTO.<ChallengeDTO>builder().data(dtos).build();

		// (4) ResponseDTO를 리턴한다.
		return ResponseEntity.ok().body(response);
	}
}
