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

import com.example.demo.dto.ChallengeDTO;
import com.example.demo.dto.ResponseDTO;
import com.example.demo.model.ChallengeEntity;
import com.example.demo.service.ChallengeService;

import lombok.extern.slf4j.Slf4j;

//ç������ C R U D�ϰ�, ��ȸ�ϴ� ��Ʈ�ѷ�
@Slf4j
@RestController
@RequestMapping("challenge")
public class ChallengeCrudController {

	@Autowired
	private ChallengeService service;

	// ç������ ����
	@PostMapping
	public ResponseEntity<?> createChallenge(@AuthenticationPrincipal String userId, @RequestBody ChallengeDTO dto) {
		try {
			// (1) ChallengeEntity�� ��ȯ�Ѵ�.
			ChallengeEntity entity = ChallengeDTO.toEntity(dto);

			// (2) id�� null�� �ʱ�ȭ �Ѵ�. ���� ��ÿ��� id�� ����� �ϱ� �����̴�.
			entity.setId(null);

			// (3) ���� ���̵� ���� �� �ش�.
			entity.setUserId(userId);

			// (4) ���񽺸� �̿��� Challenge��ƼƼ�� �����Ѵ�.
			List<ChallengeEntity> entities = service.createWithRelation(entity);

			// (5) �ڹ� ��Ʈ���� �̿��� ���ϵ� ��ƼƼ ����Ʈ�� ChallengeDTO����Ʈ�� ��ȯ�Ѵ�.
			List<ChallengeDTO> dtos = entities.stream().map(ChallengeDTO::new).collect(Collectors.toList());

			// (6) ��ȯ�� ChallengeDTO����Ʈ�� �̿��� ResponseDTO�� �ʱ�ȭ�Ѵ�.
			ResponseDTO<ChallengeDTO> response = ResponseDTO.<ChallengeDTO>builder().data(dtos).build();

			// (7) ResponseDTO�� �����Ѵ�.
			return ResponseEntity.ok().body(response);
		} catch (Exception e) {
			// (8) Ȥ�� ���ܰ� ���� ��� dto��� error�� �޽����� �־� �����Ѵ�.
			String error = e.getMessage();
			ResponseDTO<ChallengeDTO> response = ResponseDTO.<ChallengeDTO>builder().error(error).build();
			return ResponseEntity.badRequest().body(response);
		}
	}

	// ����ڰ� ���� ç������ ����
	@GetMapping
	public ResponseEntity<?> retrieveChallengeList(@AuthenticationPrincipal String userId) {
		// (1) ���� �޼����� retrieve�޼��带 ����� Challenge����Ʈ�� �����´�
		List<ChallengeEntity> entities = service.retrieve(userId);

		// (2) �ڹ� ��Ʈ���� �̿��� ���ϵ� ��ƼƼ ����Ʈ�� ChallengeDTO����Ʈ�� ��ȯ�Ѵ�.
		List<ChallengeDTO> dtos = entities.stream().map(ChallengeDTO::new).collect(Collectors.toList());

		// (3) ��ȯ�� ChallengeDTO����Ʈ�� �̿���ResponseDTO�� �ʱ�ȭ�Ѵ�.
		ResponseDTO<ChallengeDTO> response = ResponseDTO.<ChallengeDTO>builder().data(dtos).build();

		// (4) ResponseDTO�� �����Ѵ�.
		return ResponseEntity.ok().body(response);
	}

	////////////////////////
	// Update //
	////////////////////////

	////////////////////////
	// Delete //
	////////////////////////
}
