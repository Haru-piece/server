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

//������ ���� ������ ���Ϲ޴� ��Ʈ�ѷ�
@Slf4j
@RestController
@RequestMapping("badge")
public class BadgeController {

	@Autowired
	private BadgeGiver badgeGiver;

	// ��� ������ ������ ����
	@GetMapping("/all")
	public ResponseEntity<?> retrieveAllChallengeList(@AuthenticationPrincipal String userId) {

		// (1) ��� ���� ��ƼƼ���� �������丮���� �����´�.
		List<BadgeEntity> entities = badgeGiver.retrieveAll();

		// (2) �ڹ� ��Ʈ���� �̿��� ���ϵ� ��ƼƼ ����Ʈ�� BadgeDTO����Ʈ�� ��ȯ�Ѵ�.
		List<BadgeDTO> dtos = entities.stream().map(BadgeDTO::new).collect(Collectors.toList());

		// (3) ��ȯ�� BadgeDTO����Ʈ�� �̿��� ResponseDTO�� �ʱ�ȭ�Ѵ�.
		ResponseDTO<BadgeDTO> response = ResponseDTO.<BadgeDTO>builder().data(dtos).build();

		// (4) ResponseDTO�� �����Ѵ�.
		return ResponseEntity.ok().body(response);
	}

}
