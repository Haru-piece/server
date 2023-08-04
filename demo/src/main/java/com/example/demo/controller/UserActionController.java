package com.example.demo.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
public class UserActionController {
	
	@Autowired
	private ChallengeService service;
	
	//Participate
	@PostMapping("/participate")
	public ResponseEntity<?> participateChallenge(
			@AuthenticationPrincipal String userId,
			@RequestBody ChallengeDTO dto) {
		try {
			// (1) ChallengeEntity�� ��ȯ�Ѵ�.
			ChallengeEntity entity = ChallengeDTO.toEntity(dto);

			// (2) ���񽺸� �̿��� ���� ����
			List<ChallengeEntity> entities = service.participateChallenge(entity, userId);

			// (3) �ڹ� ��Ʈ���� �̿��� ���ϵ� ��ƼƼ ����Ʈ�� ChallengeDTO����Ʈ�� ��ȯ�Ѵ�.
			List<ChallengeDTO> dtos = entities.stream().map(ChallengeDTO::new).collect(Collectors.toList());

			// (4) ��ȯ�� ChallengeDTO����Ʈ�� �̿��� ResponseDTO�� �ʱ�ȭ�Ѵ�.
			ResponseDTO<ChallengeDTO> response = ResponseDTO.<ChallengeDTO>builder().data(dtos).build();

			// (5) ResponseDTO�� �����Ѵ�.
			return ResponseEntity.ok().body(response);
		} catch (Exception e) {
			// (6) Ȥ�� ���ܰ� ���� ��� dto��� error�� �޽����� �־� �����Ѵ�.
			String error = e.getMessage();
			ResponseDTO<ChallengeDTO> response = ResponseDTO.<ChallengeDTO>builder().error(error).build();
			return ResponseEntity.badRequest().body(response);
		}
	}

	
	//Get out from participate
	@PostMapping("/out/{participatingChallengeId}")
	public ResponseEntity<?> getOutFromChallenge(
			@AuthenticationPrincipal String userId,
			@PathVariable String participatingChallengeId) {
		try {
			// (1) ���񽺸� �̿��� ���� ����
			List<ChallengeEntity> entities = service.getOutFromChallenge(participatingChallengeId);

			// (2) �ڹ� ��Ʈ���� �̿��� ���ϵ� ��ƼƼ ����Ʈ�� ChallengeDTO����Ʈ�� ��ȯ�Ѵ�.
			List<ChallengeDTO> dtos = entities.stream().map(ChallengeDTO::new).collect(Collectors.toList());

			// (3) ��ȯ�� ChallengeDTO����Ʈ�� �̿��� ResponseDTO�� �ʱ�ȭ�Ѵ�.
			ResponseDTO<ChallengeDTO> response = ResponseDTO.<ChallengeDTO>builder().data(dtos).build();

			// (4) ResponseDTO�� �����Ѵ�.
			return ResponseEntity.ok().body(response);
		} catch (Exception e) {
			// (6) Ȥ�� ���ܰ� ���� ��� dto��� error�� �޽����� �־� �����Ѵ�.
			String error = e.getMessage();
			ResponseDTO<ChallengeDTO> response = ResponseDTO.<ChallengeDTO>builder().error(error).build();
			return ResponseEntity.badRequest().body(response);
		}
	}
	
	
	

}
