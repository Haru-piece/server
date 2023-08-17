package com.example.demo.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.ChallengeDTO;
import com.example.demo.dto.ParticipatingChallengeDTO;
import com.example.demo.dto.ResponseDTO;
import com.example.demo.model.ChallengeEntity;
import com.example.demo.model.ParticipatingChallengeEntity;
import com.example.demo.service.ChallengeService;
import com.example.demo.service.ParticipatingChallengeService;

import lombok.extern.slf4j.Slf4j;

//������ Ư�� ç������ �����ϰ�, ������ ��Ʈ�ѷ�
//������ Ư�� ç������ Ÿ �������� ���� ���θ� ��ȸ�ϰ�, Ư�� ç������ ���������� �˸��� ��Ʈ�ѷ�
@Slf4j
@RestController
@RequestMapping("challenge")
public class UserActionController {

	@Autowired
	private ChallengeService service;

	@Autowired
	private ParticipatingChallengeService pCService;

	// Ư�� ç���� ����
	@GetMapping("/this")
	public ResponseEntity<?> retrieveChallenge(@AuthenticationPrincipal String userId, @RequestBody ChallengeDTO dto) {

		try {
			// (1) ChallengeEntity�� ��ȯ�Ѵ�.
			ChallengeEntity entity = ChallengeDTO.toEntity(dto);

			// (2) ���񽺸� �̿��� Ư�� Challenge��ƼƼ�� ��ȸ�Ѵ�.
			List<ChallengeEntity> entities = service.retrieveSpecificChallenge(entity, userId);

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

	// Ư�� ç������ ����
	@PostMapping("/participate/{challengeId}")
	public ResponseEntity<?> participateChallenge(@AuthenticationPrincipal String userId,
			@PathVariable String challengeId) {
		try {
			// (1) ���񽺸� �̿��� ���� ����
			List<ChallengeEntity> entities = service.participateChallenge(challengeId, userId);

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

	// Ư�� ç�����κ��� ������
	@PostMapping("/out/{participatingChallengeId}")
	public ResponseEntity<?> getOutFromChallenge(@AuthenticationPrincipal String userId,
			@PathVariable String participatingChallengeId) {
		try {
			// (1) ���񽺸� �̿��� ������ ç�������� ������
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

	// ç������ Ÿ ���� ���� ���� ��ȸ
	@GetMapping("/success/{challengeId}")
	public ResponseEntity<?> retrieveSuccessInfoFromChallenge(@AuthenticationPrincipal String userId,
			@PathVariable String challengeId) {
		try {
			// (1) ���񽺸� �̿��� ������ ç�������� ������
			List<ParticipatingChallengeEntity> entities = pCService.retrieveSuccessInfoFromChallenge(challengeId);

			// (2) �ڹ� ��Ʈ���� �̿��� ���ϵ� ��ƼƼ ����Ʈ�� ParticipatingChallengeDTO����Ʈ�� ��ȯ�Ѵ�.
			List<ParticipatingChallengeDTO> dtos = entities.stream().map(ParticipatingChallengeDTO::new)
					.collect(Collectors.toList());

			// (3) ��ȯ�� ChallengeDTO����Ʈ�� �̿��� ResponseDTO�� �ʱ�ȭ�Ѵ�.
			ResponseDTO<ParticipatingChallengeDTO> response = ResponseDTO.<ParticipatingChallengeDTO>builder()
					.data(dtos).build();

			// (4) ResponseDTO�� �����Ѵ�.
			return ResponseEntity.ok().body(response);
		} catch (Exception e) {
			// (6) Ȥ�� ���ܰ� ���� ��� dto��� error�� �޽����� �־� �����Ѵ�.
			String error = e.getMessage();
			ResponseDTO<ParticipatingChallengeDTO> response = ResponseDTO.<ParticipatingChallengeDTO>builder()
					.error(error).build();
			return ResponseEntity.badRequest().body(response);
		}
	}

	// ������ ç���� ���� ���� ������Ʈ
	@PostMapping("/success/{challengeId}")
	public ResponseEntity<?> updateSuccessInfoFromChallenge(@AuthenticationPrincipal String userId,
			@PathVariable String challengeId) {
		try {
			// (1) ���񽺸� �̿��� ������ ç�������� ������
			List<ParticipatingChallengeEntity> entities = pCService.updateSuccessInfoFromChallenge(challengeId, userId);

			// (2) �ڹ� ��Ʈ���� �̿��� ���ϵ� ��ƼƼ ����Ʈ�� ParticipatingChallengeDTO����Ʈ�� ��ȯ�Ѵ�.
			List<ParticipatingChallengeDTO> dtos = entities.stream().map(ParticipatingChallengeDTO::new)
					.collect(Collectors.toList());

			// (3) ��ȯ�� ChallengeDTO����Ʈ�� �̿��� ResponseDTO�� �ʱ�ȭ�Ѵ�.
			ResponseDTO<ParticipatingChallengeDTO> response = ResponseDTO.<ParticipatingChallengeDTO>builder()
					.data(dtos).build();

			// (4) ResponseDTO�� �����Ѵ�.
			return ResponseEntity.ok().body(response);
		} catch (Exception e) {
			// (6) Ȥ�� ���ܰ� ���� ��� dto��� error�� �޽����� �־� �����Ѵ�.
			String error = e.getMessage();
			ResponseDTO<ParticipatingChallengeDTO> response = ResponseDTO.<ParticipatingChallengeDTO>builder()
					.error(error).build();
			return ResponseEntity.badRequest().body(response);
		}
	}

}
