package com.example.demo.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dto.ImageDTO;
import com.example.demo.dto.ResponseDTO;
import com.example.demo.model.ImageEntity;
import com.example.demo.service.ImageService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("image")
public class ImageController {

	// for 로컬 서버
	// String projectPath = System.getProperty("user.dir") +
	// "\\src\\main\\resources\\static\\image\\";

	// for ec2 서버
	String projectPath = System.getProperty("user.dir") + "/image/";

	@Autowired
	private ImageService service;

	// 이미지를 ec2 서버의 디렉토리에 추가
	@PostMapping
	public ResponseEntity<?> write(ImageEntity imageEntity, MultipartFile image) throws Exception {

		// 이미지 서비스를 통해 새로운 이미지 엔터티를 생성하고 저장
		ImageEntity entity = service.write(imageEntity, image);

		// 생성된 이미지 엔터티를 기반으로 이미지 DTO 생성
		ImageDTO dto = new ImageDTO(entity);

		// 이미지 DTO를 담을 리스트 생성 및 DTO 추가
		List<ImageDTO> dtos = new ArrayList<>();
		dtos.add(dto);

		// 응답 데이터를 담는 ResponseDTO 생성
		ResponseDTO<ImageDTO> response = ResponseDTO.<ImageDTO>builder().data(dtos).build();

		// HTTP 응답 상태 코드 OK(200)와 함께 응답 바디에 ResponseDTO를 담아 반환
		return ResponseEntity.ok().body(response);
	}

	// 주어진 이미지 이름을 가진 이미지를 리턴
	@GetMapping("/{imageName}")
	public void serveImage(@PathVariable String imageName, HttpServletResponse response) throws IOException {
		// 요청된 이미지 이름이 유효하지 않은 경우
		if (!StringUtils.hasText(imageName)) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}

		// 요청된 이미지 파일 경로 생성
		File imageFile = new File(projectPath + imageName);

		// 요청된 이미지 파일이 존재하지 않거나 파일이 아닌 경우
		if (!imageFile.exists() || !imageFile.isFile()) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return;
		}

		// 응답에 이미지 컨텐츠 타입 설정
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);

		try (InputStream inputStream = new FileInputStream(imageFile)) {
			// 이미지 파일의 내용을 응답 출력 스트림으로 전송
			inputStream.transferTo(response.getOutputStream());
			response.getOutputStream().flush(); // 출력 스트림 내용 비우기
		} catch (IOException e) {
			// HTTP 응답 상태를 "내부 서버 오류"로 설정
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}

	// 메모리에 저장된 모든 이미지의 정보를 리턴
	@GetMapping("/all")
	public ResponseEntity<?> retrieveAllImageInfo(@AuthenticationPrincipal String userId) {
		// (1) 모든 이미지 리스트를 가져온다
		List<ImageEntity> entities = service.retrieveAllImageInfo();

		// (2) 자바 스트림을 이용해 리턴된 엔티티 리스트를 ImageDTO리스트로 변환한다.
		List<ImageDTO> dtos = entities.stream().map(ImageDTO::new).collect(Collectors.toList());

		// (3) 변환된 ImageDTO리스트를 이용해ResponseDTO를 초기화한다.
		ResponseDTO<ImageDTO> response = ResponseDTO.<ImageDTO>builder().data(dtos).build();

		// (4) ImageDTO를 리턴한다.
		return ResponseEntity.ok().body(response);
	}

}
