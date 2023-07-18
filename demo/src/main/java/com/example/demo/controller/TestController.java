package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import com.example.demo.service.ChallengeService;
import com.example.demo.dto.ResponseDTO;

@RestController
@RequestMapping("todo")
public class TestController {
	
	//@Autowired
	//private ChallengeService service;
	
	@GetMapping("/test")
	public ResponseEntity<?> TestChallenge() {
		//String str = service.testService();
		List<String> list = new ArrayList<>();
		//list.add(str);
		ResponseDTO<String> response = ResponseDTO.<String>builder().data(list).build();
		return ResponseEntity.ok().body(response);
	}
}
