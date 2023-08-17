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

	// for ���� ����
	// String projectPath = System.getProperty("user.dir") +
	// "\\src\\main\\resources\\static\\image\\";

	// for ec2 ����
	String projectPath = System.getProperty("user.dir") + "/image/";

	@Autowired
	private ImageService service;

	// �̹����� ec2 ������ ���丮�� �߰�
	@PostMapping
	public ResponseEntity<?> write(ImageEntity imageEntity, MultipartFile image) throws Exception {

		// �̹��� ���񽺸� ���� ���ο� �̹��� ����Ƽ�� �����ϰ� ����
		ImageEntity entity = service.write(imageEntity, image);

		// ������ �̹��� ����Ƽ�� ������� �̹��� DTO ����
		ImageDTO dto = new ImageDTO(entity);

		// �̹��� DTO�� ���� ����Ʈ ���� �� DTO �߰�
		List<ImageDTO> dtos = new ArrayList<>();
		dtos.add(dto);

		// ���� �����͸� ��� ResponseDTO ����
		ResponseDTO<ImageDTO> response = ResponseDTO.<ImageDTO>builder().data(dtos).build();

		// HTTP ���� ���� �ڵ� OK(200)�� �Բ� ���� �ٵ� ResponseDTO�� ��� ��ȯ
		return ResponseEntity.ok().body(response);
	}

	// �־��� �̹��� �̸��� ���� �̹����� ����
	@GetMapping("/{imageName}")
	public void serveImage(@PathVariable String imageName, HttpServletResponse response) throws IOException {
		// ��û�� �̹��� �̸��� ��ȿ���� ���� ���
		if (!StringUtils.hasText(imageName)) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}

		// ��û�� �̹��� ���� ��� ����
		File imageFile = new File(projectPath + imageName);

		// ��û�� �̹��� ������ �������� �ʰų� ������ �ƴ� ���
		if (!imageFile.exists() || !imageFile.isFile()) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return;
		}

		// ���信 �̹��� ������ Ÿ�� ����
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);

		try (InputStream inputStream = new FileInputStream(imageFile)) {
			// �̹��� ������ ������ ���� ��� ��Ʈ������ ����
			inputStream.transferTo(response.getOutputStream());
			response.getOutputStream().flush(); // ��� ��Ʈ�� ���� ����
		} catch (IOException e) {
			// HTTP ���� ���¸� "���� ���� ����"�� ����
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}

	// �޸𸮿� ����� ��� �̹����� ������ ����
	@GetMapping("/all")
	public ResponseEntity<?> retrieveAllImageInfo(@AuthenticationPrincipal String userId) {
		// (1) ��� �̹��� ����Ʈ�� �����´�
		List<ImageEntity> entities = service.retrieveAllImageInfo();

		// (2) �ڹ� ��Ʈ���� �̿��� ���ϵ� ��ƼƼ ����Ʈ�� ImageDTO����Ʈ�� ��ȯ�Ѵ�.
		List<ImageDTO> dtos = entities.stream().map(ImageDTO::new).collect(Collectors.toList());

		// (3) ��ȯ�� ImageDTO����Ʈ�� �̿���ResponseDTO�� �ʱ�ȭ�Ѵ�.
		ResponseDTO<ImageDTO> response = ResponseDTO.<ImageDTO>builder().data(dtos).build();

		// (4) ImageDTO�� �����Ѵ�.
		return ResponseEntity.ok().body(response);
	}

}
