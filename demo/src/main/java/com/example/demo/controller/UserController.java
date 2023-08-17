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

//회원가입, 로그인하는 컨트롤러
//모든 유저들의 정보와, 본인의 정보를 리턴받는 컨트롤러
@Slf4j
@RestController
@RequestMapping("/auth")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private TokenProvider tokenProvider;

	private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	// 회원가입
	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@RequestBody UserDTO userDTO) {
		try {
			// 저장할 유저 만들기
			UserEntity user = UserEntity.builder().email(userDTO.getEmail()).username(userDTO.getUsername())
					.password(passwordEncoder.encode(userDTO.getPassword())).createCount(0).participateCount(0).build();

			// service를 이용해 repository에 유저 저장
			UserEntity registeredUser = userService.create(user);
			UserDTO responseUserDTO = UserDTO.builder().email(registeredUser.getEmail()).id(registeredUser.getId())
					.username(registeredUser.getUsername()).createCount(registeredUser.getCreateCount()).build();

			return ResponseEntity.ok(responseUserDTO);
		} catch (Exception e) {
			// 예외가 나는 경우 bad response 리턴.
			ResponseDTO responseDTO = ResponseDTO.builder().error(e.getMessage()).build();
			return ResponseEntity.badRequest().body(responseDTO);
		}
	}

	// 로그인
	@PostMapping("/signin")
	public ResponseEntity<?> authenticate(@RequestBody UserDTO userDTO) {
		UserEntity user = userService.getByCredentials(userDTO.getEmail(), userDTO.getPassword(), passwordEncoder);

		if (user != null) {
			// 토큰 생성
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
	/* 회원 탈퇴 */
	/**********************/

	// Retrieve All User
	@GetMapping("/all")
	public ResponseEntity<?> retrieveAllUserList(@AuthenticationPrincipal String userId) {
		// (1) 서비스 메서드의 retrieveAll메서드를 사용해 모든 User 리스트를 가져온다
		List<UserEntity> entities = userService.retrieveAll();

		// (2) 자바 스트림을 이용해 리턴된 엔티티 리스트를 userDTO리스트로 변환한다.
		List<UserDTO> dtos = entities.stream().map(UserDTO::new).collect(Collectors.toList());

		// (3) 변환된 UserDTO리스트를 이용해ResponseDTO를 초기화한다.
		ResponseDTO<UserDTO> response = ResponseDTO.<UserDTO>builder().data(dtos).build();

		// (4) ResponseDTO를 리턴한다.
		return ResponseEntity.ok().body(response);
	}

	// Retrieve My User Info
	@GetMapping("/mine")
	public ResponseEntity<?> retrieveMine(@AuthenticationPrincipal String userId) {
		// (1) 서비스 메서드를 이용하여 나의 User 정보를 가져온다
		List<UserEntity> entities = userService.retrieveMyEntity(userId);

		// (2) 자바 스트림을 이용해 리턴된 엔티티 리스트를 userDTO리스트로 변환한다.
		List<UserDTO> dtos = entities.stream().map(UserDTO::new).collect(Collectors.toList());

		// (3) 변환된 UserDTO리스트를 이용해ResponseDTO를 초기화한다.
		ResponseDTO<UserDTO> response = ResponseDTO.<UserDTO>builder().data(dtos).build();

		// (4) ResponseDTO를 리턴한다.
		return ResponseEntity.ok().body(response);
	}
}