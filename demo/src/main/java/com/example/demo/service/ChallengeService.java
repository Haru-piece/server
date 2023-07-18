package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
//import com.example.demo.model.ChallengeEntity;
//import com.example.demo.persistence.ChallengeRepository;

import com.example.demo.model.TodoEntity;
import com.example.demo.persistence.TodoRepository;

@Service
public class ChallengeService {
	
	/*
	@Autowired
	private TodoRepository repository;
	*/
	
	public String testService() {
		
		return "Test Service";
		
		/*
		TodoEntity entity = TodoEntity.builder().title("challenge1").build();
		
		repository.save(entity);
		
		TodoEntity savedEntity = repository.findById(entity.getId()).get();
		
		return savedEntity.getTitle();
		
		*/
	}

}
