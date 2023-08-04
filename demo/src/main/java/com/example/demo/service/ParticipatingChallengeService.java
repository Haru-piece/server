package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

import com.example.demo.model.ParticipatingChallengeEntity;
import com.example.demo.persistence.ParticipatingChallengeRepository;

import java.util.List;


@Slf4j
@Service
public class ParticipatingChallengeService {
	@Autowired
	private ParticipatingChallengeRepository participatingChallengeRepository;
	
	// Retrieve All participating Challenge Entity
	public List<ParticipatingChallengeEntity> retrieveAll() {
			return participatingChallengeRepository.findAll();
	}

}
