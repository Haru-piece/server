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

import com.example.demo.model.ParticipatingChallengeEntity;
import com.example.demo.service.ParticipatingChallengeService;

import com.example.demo.model.ParticipantWhoHasBadgeEntity;
import com.example.demo.service.BadgeGiver;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("relation")
public class RelationController {
	
	@Autowired
	private ParticipatingChallengeService service;
	
	@Autowired
	private BadgeGiver badgeGiver;
	
	// Retrieve All Challenge
	@GetMapping("/challenge")
	public ResponseEntity<?> retrieveAllChallengeRelationList(
			@AuthenticationPrincipal String userId) {
		
		// (1) ��ƼƼ�� �������丮���� �����´�.
		List<ParticipatingChallengeEntity> entities = service.retrieveAll();
		
		// (2) �ڹ� ��Ʈ���� �̿��� ���ϵ� ��ƼƼ ����Ʈ�� ChallengeDTO����Ʈ�� ��ȯ�Ѵ�.
		List<ParticipatingChallengeDTO> dtos = entities.stream().map(ParticipatingChallengeDTO::new).collect(Collectors.toList());

		// (3) ��ȯ�� ChallengeDTO����Ʈ�� �̿���ResponseDTO�� �ʱ�ȭ�Ѵ�.
		ResponseDTO<ParticipatingChallengeDTO> response = ResponseDTO.<ParticipatingChallengeDTO>builder().data(dtos).build();

		// (4) ResponseDTO�� �����Ѵ�.
		return ResponseEntity.ok().body(response);
	}
	
	@GetMapping("/badge")
	public ResponseEntity<?> retrieveAllBadgeRelationList(
			@AuthenticationPrincipal String userId) {
		
		// (1) ��ƼƼ�� �������丮���� �����´�.
		List<ParticipantWhoHasBadgeEntity> entities = badgeGiver.retrieveBadgeRelationAll();
		
		// (2) �ڹ� ��Ʈ���� �̿��� ���ϵ� ��ƼƼ ����Ʈ�� ChallengeDTO����Ʈ�� ��ȯ�Ѵ�.
		List<ParticipantWhoHasBadgeDTO> dtos = entities.stream().map(ParticipantWhoHasBadgeDTO::new).collect(Collectors.toList());

		// (3) ��ȯ�� ChallengeDTO����Ʈ�� �̿���ResponseDTO�� �ʱ�ȭ�Ѵ�.
		ResponseDTO<ParticipantWhoHasBadgeDTO> response = ResponseDTO.<ParticipantWhoHasBadgeDTO>builder().data(dtos).build();

		// (4) ResponseDTO�� �����Ѵ�.
		return ResponseEntity.ok().body(response);
	}

}
