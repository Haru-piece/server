package com.example.demo.service;

import com.example.demo.model.UserEntity;
import com.example.demo.persistence.UserRepository;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Slf4j
@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public UserEntity create(final UserEntity userEntity) {
		validate(userEntity);
		
		// auth/all할 때 ChallengeEntity가 null인 userEntity를 받아오지 못해서
		// 임시로 할당해줌
		
		//ChallengeEntity mockChallengeEntity = ChallengeEntity.builder().title("가짜 챌린지").build();
		//userEntity.setChallenge(mockChallengeEntity);
		
		return userRepository.save(userEntity);
	}

	public UserEntity getByCredentials(final String email, final String password, final PasswordEncoder encoder) {
		final UserEntity originalUser = userRepository.findByEmail(email);
		
		if(originalUser != null && 
		   encoder.matches(password, originalUser.getPassword())) {
			return originalUser;
		}
		
		return null;
	}
	
	// Retrieve All Users
	public List<UserEntity> retrieveAll() {
		return userRepository.findAll();
	}
	
	// Retrieve My information
	public List<UserEntity> retrieveMyEntity(String id) {
		Optional<UserEntity> target = userRepository.findById(id);
		
		if(target == null) throw new RuntimeException("id is not valid.");
		
		List<UserEntity> list = List.of(userRepository.findById(id).get());
		return list;
	}
	
	public void validate(final UserEntity userEntity) {
		if(userEntity == null || userEntity.getEmail() == null ) {
			throw new RuntimeException("Invalid arguments");
		}
		final String email = userEntity.getEmail();
		if(userRepository.existsByEmail(email)) {
			log.warn("Email already exists {}", email);
			throw new RuntimeException("Email already exists");
		}
	}
}