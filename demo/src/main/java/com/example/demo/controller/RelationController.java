package com.example.demo.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.ParticipantWhoHasBadgeDTO;
import com.example.demo.dto.ParticipatingChallengeDTO;
import com.example.demo.dto.ResponseDTO;
import com.example.demo.model.ParticipantWhoHasBadgeEntity;
import com.example.demo.model.ParticipatingChallengeEntity;
import com.example.demo.service.BadgeGiver;
import com.example.demo.service.ParticipatingChallengeService;

import lombok.extern.slf4j.Slf4j;

//챌린지 - 유저 간 관계(relation)에 대한 정보, 뱃지 - 유저 간 관계에 대한 정보를 리턴하는 컨트롤러  
@Slf4j
@RestController
@RequestMapping("relation")
public class RelationController {

	@Autowired
	private ParticipatingChallengeService service;

	@Autowired
	private BadgeGiver badgeGiver;

	// 챌린지 - 유저 간 관계에 대한 정보 리턴
	@GetMapping("/challenge")
	public ResponseEntity<?> retrieveAllChallengeRelationList(@AuthenticationPrincipal String userId) {

		// (1) 엔티티를 리포지토리에서 가져온다.
		List<ParticipatingChallengeEntity> entities = service.retrieveAll();

		// (2) 자바 스트림을 이용해 리턴된 엔티티 리스트를 ParticipatingChallengeDTO리스트로 변환한다.
		List<ParticipatingChallengeDTO> dtos = entities.stream().map(ParticipatingChallengeDTO::new)
				.collect(Collectors.toList());

		// (3) 변환된 DTO리스트를 이용해ResponseDTO를 초기화한다.
		ResponseDTO<ParticipatingChallengeDTO> response = ResponseDTO.<ParticipatingChallengeDTO>builder().data(dtos)
				.build();

		// (4) ResponseDTO를 리턴한다.
		return ResponseEntity.ok().body(response);
	}

	// 뱃지 - 유저 간 관계에 대한 정보 리턴
	@GetMapping("/badge")
	public ResponseEntity<?> retrieveAllBadgeRelationList(@AuthenticationPrincipal String userId) {

		// (1) 엔티티를 리포지토리에서 가져온다.
		List<ParticipantWhoHasBadgeEntity> entities = badgeGiver.retrieveBadgeRelationAll();

		// (2) 자바 스트림을 이용해 리턴된 엔티티 리스트를 ParticipantWhoHasBadgeDTO리스트로 변환한다.
		List<ParticipantWhoHasBadgeDTO> dtos = entities.stream().map(ParticipantWhoHasBadgeDTO::new)
				.collect(Collectors.toList());

		// (3) 변환된 DTO리스트를 이용해ResponseDTO를 초기화한다.
		ResponseDTO<ParticipantWhoHasBadgeDTO> response = ResponseDTO.<ParticipantWhoHasBadgeDTO>builder().data(dtos)
				.build();

		// (4) ResponseDTO를 리턴한다.
		return ResponseEntity.ok().body(response);
	}

}
