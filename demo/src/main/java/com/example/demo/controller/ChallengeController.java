package com.example.demo.controller;

import com.example.demo.dto.ResponseDTO;
import com.example.demo.dto.ChallengeDTO;
import com.example.demo.model.ChallengeEntity;
import com.example.demo.service.ChallengeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("challenge")
public class ChallengeController {
	
	@Autowired
	private ChallengeService service;
	
	//Create
	@PostMapping
	public ResponseEntity<?> createChallenge(@RequestBody ChallengeDTO dto) {
		try {
			String temporaryUserId = "temporary-user"; // temporary user id.

			// (1) ChallengeEntity�� ��ȯ�Ѵ�.
			ChallengeEntity entity = ChallengeDTO.toEntity(dto);

			// (2) id�� null�� �ʱ�ȭ �Ѵ�. ���� ��ÿ��� id�� ����� �ϱ� �����̴�.
			entity.setId(null);

			// (3) �ӽ� ���� ���̵� ���� �� �ش�.
			entity.setUserId(temporaryUserId);

			// (4) ���񽺸� �̿��� Challenge��ƼƼ�� �����Ѵ�.
			List<ChallengeEntity> entities = service.create(entity);

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
	
	// Retrieve
	@GetMapping
	public ResponseEntity<?> retrieveChallengeList() {
		String temporaryUserId = "temporary-user"; // temporary user id.

		// (1) ���� �޼����� retrieve�޼��带 ����� Todo����Ʈ�� �����´�
		List<ChallengeEntity> entities = service.retrieve(temporaryUserId);

		// (2) �ڹ� ��Ʈ���� �̿��� ���ϵ� ��ƼƼ ����Ʈ�� TodoDTO����Ʈ�� ��ȯ�Ѵ�.
		List<ChallengeDTO> dtos = entities.stream().map(ChallengeDTO::new).collect(Collectors.toList());

		// (6) ��ȯ�� TodoDTO����Ʈ�� �̿���ResponseDTO�� �ʱ�ȭ�Ѵ�.
		ResponseDTO<ChallengeDTO> response = ResponseDTO.<ChallengeDTO>builder().data(dtos).build();

		// (7) ResponseDTO�� �����Ѵ�.
		return ResponseEntity.ok().body(response);
	}
	
	// Update
	
	// Delete

	
}

