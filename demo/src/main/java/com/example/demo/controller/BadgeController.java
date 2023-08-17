package com.example.demo.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.BadgeDTO;
import com.example.demo.dto.ResponseDTO;
import com.example.demo.model.BadgeEntity;
import com.example.demo.service.BadgeGiver;

import lombok.extern.slf4j.Slf4j;

//뱃지에 대한 정보를 리턴받는 컨트롤러
@Slf4j
@RestController
@RequestMapping("badge")
public class BadgeController {

	@Autowired
	private BadgeGiver badgeGiver;

	// 모든 뱃지의 정보를 리턴
	@GetMapping("/all")
	public ResponseEntity<?> retrieveAllChallengeList(@AuthenticationPrincipal String userId) {

		// (1) 모든 뱃지 엔티티들을 리포지토리에서 가져온다.
		List<BadgeEntity> entities = badgeGiver.retrieveAll();

		// (2) 자바 스트림을 이용해 리턴된 엔티티 리스트를 BadgeDTO리스트로 변환한다.
		List<BadgeDTO> dtos = entities.stream().map(BadgeDTO::new).collect(Collectors.toList());

		// (3) 변환된 BadgeDTO리스트를 이용해 ResponseDTO를 초기화한다.
		ResponseDTO<BadgeDTO> response = ResponseDTO.<BadgeDTO>builder().data(dtos).build();

		// (4) ResponseDTO를 리턴한다.
		return ResponseEntity.ok().body(response);
	}

}
