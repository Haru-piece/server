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

//유저가 특정 챌린지를 참여하고, 나가는 컨트롤러
//유저가 특정 챌린지의 타 유저들의 성공 여부를 조회하고, 특정 챌린지에 성공했음을 알리는 컨트롤러
@Slf4j
@RestController
@RequestMapping("challenge")
public class UserActionController {

	@Autowired
	private ChallengeService service;

	@Autowired
	private ParticipatingChallengeService pCService;

	// 특정 챌린지 조희
	@GetMapping("/this")
	public ResponseEntity<?> retrieveChallenge(@AuthenticationPrincipal String userId, @RequestBody ChallengeDTO dto) {

		try {
			// (1) ChallengeEntity로 변환한다.
			ChallengeEntity entity = ChallengeDTO.toEntity(dto);

			// (2) 서비스를 이용해 특정 Challenge엔티티를 조회한다.
			List<ChallengeEntity> entities = service.retrieveSpecificChallenge(entity, userId);

			// (3) 자바 스트림을 이용해 리턴된 엔티티 리스트를 ChallengeDTO리스트로 변환한다.
			List<ChallengeDTO> dtos = entities.stream().map(ChallengeDTO::new).collect(Collectors.toList());

			// (4) 변환된 ChallengeDTO리스트를 이용해 ResponseDTO를 초기화한다.
			ResponseDTO<ChallengeDTO> response = ResponseDTO.<ChallengeDTO>builder().data(dtos).build();

			// (5) ResponseDTO를 리턴한다.
			return ResponseEntity.ok().body(response);
		} catch (Exception e) {
			// (6) 혹시 예외가 나는 경우 dto대신 error에 메시지를 넣어 리턴한다.
			String error = e.getMessage();
			ResponseDTO<ChallengeDTO> response = ResponseDTO.<ChallengeDTO>builder().error(error).build();
			return ResponseEntity.badRequest().body(response);
		}

	}

	// 특정 챌린지에 참여
	@PostMapping("/participate/{challengeId}")
	public ResponseEntity<?> participateChallenge(@AuthenticationPrincipal String userId,
			@PathVariable String challengeId) {
		try {
			// (1) 서비스를 이용해 참여 수행
			List<ChallengeEntity> entities = service.participateChallenge(challengeId, userId);

			// (2) 자바 스트림을 이용해 리턴된 엔티티 리스트를 ChallengeDTO리스트로 변환한다.
			List<ChallengeDTO> dtos = entities.stream().map(ChallengeDTO::new).collect(Collectors.toList());

			// (3) 변환된 ChallengeDTO리스트를 이용해 ResponseDTO를 초기화한다.
			ResponseDTO<ChallengeDTO> response = ResponseDTO.<ChallengeDTO>builder().data(dtos).build();

			// (4) ResponseDTO를 리턴한다.
			return ResponseEntity.ok().body(response);
		} catch (Exception e) {
			// (6) 혹시 예외가 나는 경우 dto대신 error에 메시지를 넣어 리턴한다.
			String error = e.getMessage();
			ResponseDTO<ChallengeDTO> response = ResponseDTO.<ChallengeDTO>builder().error(error).build();
			return ResponseEntity.badRequest().body(response);
		}
	}

	// 특정 챌린지로부터 나가기
	@PostMapping("/out/{participatingChallengeId}")
	public ResponseEntity<?> getOutFromChallenge(@AuthenticationPrincipal String userId,
			@PathVariable String participatingChallengeId) {
		try {
			// (1) 서비스를 이용해 참여한 챌린지에서 나가기
			List<ChallengeEntity> entities = service.getOutFromChallenge(participatingChallengeId);

			// (2) 자바 스트림을 이용해 리턴된 엔티티 리스트를 ChallengeDTO리스트로 변환한다.
			List<ChallengeDTO> dtos = entities.stream().map(ChallengeDTO::new).collect(Collectors.toList());

			// (3) 변환된 ChallengeDTO리스트를 이용해 ResponseDTO를 초기화한다.
			ResponseDTO<ChallengeDTO> response = ResponseDTO.<ChallengeDTO>builder().data(dtos).build();

			// (4) ResponseDTO를 리턴한다.
			return ResponseEntity.ok().body(response);
		} catch (Exception e) {
			// (6) 혹시 예외가 나는 경우 dto대신 error에 메시지를 넣어 리턴한다.
			String error = e.getMessage();
			ResponseDTO<ChallengeDTO> response = ResponseDTO.<ChallengeDTO>builder().error(error).build();
			return ResponseEntity.badRequest().body(response);
		}
	}

	// 챌린지의 타 유저 성공 여부 조회
	@GetMapping("/success/{challengeId}")
	public ResponseEntity<?> retrieveSuccessInfoFromChallenge(@AuthenticationPrincipal String userId,
			@PathVariable String challengeId) {
		try {
			// (1) 서비스를 이용해 참여한 챌린지에서 나가기
			List<ParticipatingChallengeEntity> entities = pCService.retrieveSuccessInfoFromChallenge(challengeId);

			// (2) 자바 스트림을 이용해 리턴된 엔티티 리스트를 ParticipatingChallengeDTO리스트로 변환한다.
			List<ParticipatingChallengeDTO> dtos = entities.stream().map(ParticipatingChallengeDTO::new)
					.collect(Collectors.toList());

			// (3) 변환된 ChallengeDTO리스트를 이용해 ResponseDTO를 초기화한다.
			ResponseDTO<ParticipatingChallengeDTO> response = ResponseDTO.<ParticipatingChallengeDTO>builder()
					.data(dtos).build();

			// (4) ResponseDTO를 리턴한다.
			return ResponseEntity.ok().body(response);
		} catch (Exception e) {
			// (6) 혹시 예외가 나는 경우 dto대신 error에 메시지를 넣어 리턴한다.
			String error = e.getMessage();
			ResponseDTO<ParticipatingChallengeDTO> response = ResponseDTO.<ParticipatingChallengeDTO>builder()
					.error(error).build();
			return ResponseEntity.badRequest().body(response);
		}
	}

	// 유저의 챌린지 성공 여부 업데이트
	@PostMapping("/success/{challengeId}")
	public ResponseEntity<?> updateSuccessInfoFromChallenge(@AuthenticationPrincipal String userId,
			@PathVariable String challengeId) {
		try {
			// (1) 서비스를 이용해 참여한 챌린지에서 나가기
			List<ParticipatingChallengeEntity> entities = pCService.updateSuccessInfoFromChallenge(challengeId, userId);

			// (2) 자바 스트림을 이용해 리턴된 엔티티 리스트를 ParticipatingChallengeDTO리스트로 변환한다.
			List<ParticipatingChallengeDTO> dtos = entities.stream().map(ParticipatingChallengeDTO::new)
					.collect(Collectors.toList());

			// (3) 변환된 ChallengeDTO리스트를 이용해 ResponseDTO를 초기화한다.
			ResponseDTO<ParticipatingChallengeDTO> response = ResponseDTO.<ParticipatingChallengeDTO>builder()
					.data(dtos).build();

			// (4) ResponseDTO를 리턴한다.
			return ResponseEntity.ok().body(response);
		} catch (Exception e) {
			// (6) 혹시 예외가 나는 경우 dto대신 error에 메시지를 넣어 리턴한다.
			String error = e.getMessage();
			ResponseDTO<ParticipatingChallengeDTO> response = ResponseDTO.<ParticipatingChallengeDTO>builder()
					.error(error).build();
			return ResponseEntity.badRequest().body(response);
		}
	}

}
