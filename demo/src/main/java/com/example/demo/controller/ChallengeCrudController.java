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

//챌린지를 C R U D하고, 조회하는 컨트롤러
@Slf4j
@RestController
@RequestMapping("challenge")
public class ChallengeCrudController {

	@Autowired
	private ChallengeService service;

	// 챌린지를 만듬
	@PostMapping
	public ResponseEntity<?> createChallenge(@AuthenticationPrincipal String userId, @RequestBody ChallengeDTO dto) {
		try {
			// (1) ChallengeEntity로 변환한다.
			ChallengeEntity entity = ChallengeDTO.toEntity(dto);

			// (2) id를 null로 초기화 한다. 생성 당시에는 id가 없어야 하기 때문이다.
			entity.setId(null);

			// (3) 유저 아이디를 설정 해 준다.
			entity.setUserId(userId);

			// (4) 서비스를 이용해 Challenge엔티티를 생성한다.
			List<ChallengeEntity> entities = service.createWithRelation(entity);

			// (5) 자바 스트림을 이용해 리턴된 엔티티 리스트를 ChallengeDTO리스트로 변환한다.
			List<ChallengeDTO> dtos = entities.stream().map(ChallengeDTO::new).collect(Collectors.toList());

			// (6) 변환된 ChallengeDTO리스트를 이용해 ResponseDTO를 초기화한다.
			ResponseDTO<ChallengeDTO> response = ResponseDTO.<ChallengeDTO>builder().data(dtos).build();

			// (7) ResponseDTO를 리턴한다.
			return ResponseEntity.ok().body(response);
		} catch (Exception e) {
			// (8) 혹시 예외가 나는 경우 dto대신 error에 메시지를 넣어 리턴한다.
			String error = e.getMessage();
			ResponseDTO<ChallengeDTO> response = ResponseDTO.<ChallengeDTO>builder().error(error).build();
			return ResponseEntity.badRequest().body(response);
		}
	}

	// 사용자가 만든 챌린지를 리턴
	@GetMapping
	public ResponseEntity<?> retrieveChallengeList(@AuthenticationPrincipal String userId) {
		// (1) 서비스 메서드의 retrieve메서드를 사용해 Challenge리스트를 가져온다
		List<ChallengeEntity> entities = service.retrieve(userId);

		// (2) 자바 스트림을 이용해 리턴된 엔티티 리스트를 ChallengeDTO리스트로 변환한다.
		List<ChallengeDTO> dtos = entities.stream().map(ChallengeDTO::new).collect(Collectors.toList());

		// (3) 변환된 ChallengeDTO리스트를 이용해ResponseDTO를 초기화한다.
		ResponseDTO<ChallengeDTO> response = ResponseDTO.<ChallengeDTO>builder().data(dtos).build();

		// (4) ResponseDTO를 리턴한다.
		return ResponseEntity.ok().body(response);
	}

	////////////////////////
	// Update //
	////////////////////////

	////////////////////////
	// Delete //
	////////////////////////
}
