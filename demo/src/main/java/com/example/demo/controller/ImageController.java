package com.example.demo.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import com.example.demo.dto.ChallengeDTO;
import com.example.demo.dto.ImageDTO;
import com.example.demo.dto.ResponseDTO;
import com.example.demo.model.ChallengeEntity;
import com.example.demo.model.ImageEntity;
import com.example.demo.service.ChallengeService;
import com.example.demo.service.ImageService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("image")
public class ImageController {

	//for 로컬 서버
	//String projectPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\image\\";
	
	//for ec2 서버
	String projectPath = System.getProperty("user.dir") + "/image/";
	
	@Autowired
	private ImageService service;

	@PostMapping
	public String write(ImageEntity imageEntity, MultipartFile image) throws Exception {

		String filePath = service.write(imageEntity, image);

		return filePath;
	}

	@GetMapping("/{imageName}")
	public void serveImage(@PathVariable String imageName, HttpServletResponse response) throws IOException {
        if (!StringUtils.hasText(imageName)) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        log.info("well done");
        
        File imageFile = new File(projectPath + imageName);
        if (!imageFile.exists() || !imageFile.isFile()) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        try (InputStream inputStream = new FileInputStream(imageFile)) {
            inputStream.transferTo(response.getOutputStream());
            response.getOutputStream().flush();
        } catch (IOException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

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
