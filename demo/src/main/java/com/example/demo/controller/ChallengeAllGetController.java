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

//��� ç������ ���Ϲ޴� ��Ʈ�ѷ�
@Slf4j
@RestController
@RequestMapping("challenge")
public class ChallengeAllGetController {

	@Autowired
	private ChallengeService service;

	@Autowired
	private ParticipatingChallengeService pCService;

	// ��� ç������ �־��� ���ؿ� ���� �����Ͽ� ����
	@GetMapping("/all")
	public ResponseEntity<?> retrieveAllChallengeList(@AuthenticationPrincipal String userId,
			@RequestParam(required = false) String sort) {

		// (1) sort�� ���� �����̴�. ���� ���ؿ� ���� ��ƼƼ�� �������丮���� �����´�.
		List<ChallengeEntity> entities;
		if (sort.equals("date")) {
			entities = service.retrieveAllSortedByDate();
		} else if (sort.equals("count")) {
			entities = service.retrieveAllSortedByParticipantCount();
		} else {
			entities = service.retrieveAll();
		}

		// (2) �ڹ� ��Ʈ���� �̿��� ���ϵ� ��ƼƼ ����Ʈ�� ChallengeDTO����Ʈ�� ��ȯ�Ѵ�.
		List<ChallengeDTO> dtos = entities.stream().map(ChallengeDTO::new).collect(Collectors.toList());

		// (3) ��ȯ�� ChallengeDTO����Ʈ�� �̿���ResponseDTO�� �ʱ�ȭ�Ѵ�.
		ResponseDTO<ChallengeDTO> response = ResponseDTO.<ChallengeDTO>builder().data(dtos).build();

		// (4) ResponseDTO�� �����Ѵ�.
		return ResponseEntity.ok().body(response);
	}

	// ������ ������ ��� ç�������� ����
	@GetMapping("/all/participate")
	public ResponseEntity<?> retrieveAllParticipateChallenge(@AuthenticationPrincipal String userId) {
		// (1) ������ ������ ��� ç������ �����´�
		List<ChallengeEntity> entities = pCService.retrieveParticipateAll(userId);

		// (2) �ڹ� ��Ʈ���� �̿��� ���ϵ� ��ƼƼ ����Ʈ�� ChallengeDTO����Ʈ�� ��ȯ�Ѵ�.
		List<ChallengeDTO> dtos = entities.stream().map(ChallengeDTO::new).collect(Collectors.toList());

		// (3) ��ȯ�� ChallengeDTO����Ʈ�� �̿���ResponseDTO�� �ʱ�ȭ�Ѵ�.
		ResponseDTO<ChallengeDTO> response = ResponseDTO.<ChallengeDTO>builder().data(dtos).build();

		// (4) ResponseDTO�� �����Ѵ�.
		return ResponseEntity.ok().body(response);
	}

	// ī�װ��� ���� �ִ� ��� ç���� ����
	@GetMapping("/all/category")
	public ResponseEntity<?> retrieveAllChallengeListByCategory(@AuthenticationPrincipal String userId,
			@RequestBody ChallengeDTO dto) {
		// (1) �־��� ī�װ��� ���� ç���� ����Ʈ�� �����´�
		List<ChallengeEntity> entities = service.retrieveAllByCategory(dto.getCategory());

		// (2) �ڹ� ��Ʈ���� �̿��� ���ϵ� ��ƼƼ ����Ʈ�� ChallengeDTO����Ʈ�� ��ȯ�Ѵ�.
		List<ChallengeDTO> dtos = entities.stream().map(ChallengeDTO::new).collect(Collectors.toList());

		// (3) ��ȯ�� ChallengeDTO����Ʈ�� �̿���ResponseDTO�� �ʱ�ȭ�Ѵ�.
		ResponseDTO<ChallengeDTO> response = ResponseDTO.<ChallengeDTO>builder().data(dtos).build();

		// (4) ResponseDTO�� �����Ѵ�.
		return ResponseEntity.ok().body(response);
	}

	// Ư�� ī�װ��� ���� ç�������� ��ȸ ������ �����Ͽ� ����
	@GetMapping("/all/categoryAndCount")
	public ResponseEntity<?> retrieveAllChallengeLitByCategoryAndCount(@AuthenticationPrincipal String userId,
			@RequestBody ChallengeDTO dto) {
		// (1) ���� �޼���� Ư�� ī�װ��� ���� ç���� ����Ʈ�� �����´�
		List<ChallengeEntity> entities = service.retrieveAllByCategoryAndParticipantCount(dto.getCategory());

		// (2) �ڹ� ��Ʈ���� �̿��� ���ϵ� ��ƼƼ ����Ʈ�� ChallengeDTO����Ʈ�� ��ȯ�Ѵ�.
		List<ChallengeDTO> dtos = entities.stream().map(ChallengeDTO::new).collect(Collectors.toList());

		// (3) ��ȯ�� ChallengeDTO����Ʈ�� �̿���ResponseDTO�� �ʱ�ȭ�Ѵ�.
		ResponseDTO<ChallengeDTO> response = ResponseDTO.<ChallengeDTO>builder().data(dtos).build();

		// (4) ResponseDTO�� �����Ѵ�.
		return ResponseEntity.ok().body(response);
	}
}
