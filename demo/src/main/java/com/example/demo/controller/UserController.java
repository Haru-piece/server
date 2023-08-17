package com.example.demo.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.ResponseDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.model.UserEntity;
import com.example.demo.security.TokenProvider;
import com.example.demo.service.UserService;

import lombok.extern.slf4j.Slf4j;

//ȸ������, �α����ϴ� ��Ʈ�ѷ�
//��� �������� ������, ������ ������ ���Ϲ޴� ��Ʈ�ѷ�
@Slf4j
@RestController
@RequestMapping("/auth")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private TokenProvider tokenProvider;

	private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	// ȸ������
	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@RequestBody UserDTO userDTO) {
		try {
			// ������ ���� �����
			UserEntity user = UserEntity.builder().email(userDTO.getEmail()).username(userDTO.getUsername())
					.password(passwordEncoder.encode(userDTO.getPassword())).createCount(0).participateCount(0).build();

			// service�� �̿��� repository�� ���� ����
			UserEntity registeredUser = userService.create(user);
			UserDTO responseUserDTO = UserDTO.builder().email(registeredUser.getEmail()).id(registeredUser.getId())
					.username(registeredUser.getUsername()).createCount(registeredUser.getCreateCount()).build();

			return ResponseEntity.ok(responseUserDTO);
		} catch (Exception e) {
			// ���ܰ� ���� ��� bad response ����.
			ResponseDTO responseDTO = ResponseDTO.builder().error(e.getMessage()).build();
			return ResponseEntity.badRequest().body(responseDTO);
		}
	}

	// �α���
	@PostMapping("/signin")
	public ResponseEntity<?> authenticate(@RequestBody UserDTO userDTO) {
		UserEntity user = userService.getByCredentials(userDTO.getEmail(), userDTO.getPassword(), passwordEncoder);

		if (user != null) {
			// ��ū ����
			final String token = tokenProvider.create(user);

			final UserDTO responseUserDTO = UserDTO.builder().email(user.getEmail()).id(user.getId()).token(token)
					.build();
			return ResponseEntity.ok().body(responseUserDTO);
		} else {
			ResponseDTO responseDTO = ResponseDTO.builder().error("Login failed.").build();
			return ResponseEntity.badRequest().body(responseDTO);
		}
	}

	/**********************/
	/* ȸ�� Ż�� */
	/**********************/

	// Retrieve All User
	@GetMapping("/all")
	public ResponseEntity<?> retrieveAllUserList(@AuthenticationPrincipal String userId) {
		// (1) ���� �޼����� retrieveAll�޼��带 ����� ��� User ����Ʈ�� �����´�
		List<UserEntity> entities = userService.retrieveAll();

		// (2) �ڹ� ��Ʈ���� �̿��� ���ϵ� ��ƼƼ ����Ʈ�� userDTO����Ʈ�� ��ȯ�Ѵ�.
		List<UserDTO> dtos = entities.stream().map(UserDTO::new).collect(Collectors.toList());

		// (3) ��ȯ�� UserDTO����Ʈ�� �̿���ResponseDTO�� �ʱ�ȭ�Ѵ�.
		ResponseDTO<UserDTO> response = ResponseDTO.<UserDTO>builder().data(dtos).build();

		// (4) ResponseDTO�� �����Ѵ�.
		return ResponseEntity.ok().body(response);
	}

	// Retrieve My User Info
	@GetMapping("/mine")
	public ResponseEntity<?> retrieveMine(@AuthenticationPrincipal String userId) {
		// (1) ���� �޼��带 �̿��Ͽ� ���� User ������ �����´�
		List<UserEntity> entities = userService.retrieveMyEntity(userId);

		// (2) �ڹ� ��Ʈ���� �̿��� ���ϵ� ��ƼƼ ����Ʈ�� userDTO����Ʈ�� ��ȯ�Ѵ�.
		List<UserDTO> dtos = entities.stream().map(UserDTO::new).collect(Collectors.toList());

		// (3) ��ȯ�� UserDTO����Ʈ�� �̿���ResponseDTO�� �ʱ�ȭ�Ѵ�.
		ResponseDTO<UserDTO> response = ResponseDTO.<UserDTO>builder().data(dtos).build();

		// (4) ResponseDTO�� �����Ѵ�.
		return ResponseEntity.ok().body(response);
	}
}