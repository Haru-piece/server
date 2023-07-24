package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

import com.example.demo.model.ChallengeEntity;
import com.example.demo.persistence.ChallengeRepository;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ChallengeService {
	
	
	@Autowired
	private ChallengeRepository repository;
	
	// Create
	public List<ChallengeEntity> create(final ChallengeEntity entity) {
		// Validations
		validate(entity);

		repository.save(entity);
		log.info("Entity Id : {} is saved.", entity.getId());
		return repository.findByUserId(entity.getUserId());
	}
	
	// Retrieve
	public List<ChallengeEntity> retrieve(final String userId) {
		return repository.findByUserId(userId);
	}
	
	// Update
	 
	// Delete
	
	// Retrieve All Challenges
	public List<ChallengeEntity> retrieveAll() {
		return repository.findAll();
	}
	
	// Validate
	public void validate(final ChallengeEntity entity) {
		if(entity == null) {
			log.warn("Entity cannot be null.");
			throw new RuntimeException("Entity cannot be null.");
		}

		if(entity.getUserId() == null) {
			log.warn("Unknown user.");
			throw new RuntimeException("Unknown user.");
		}
	}

}
