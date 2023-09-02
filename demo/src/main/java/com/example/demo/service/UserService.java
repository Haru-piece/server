package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.model.UserEntity;
import com.example.demo.persistence.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	//사용자 만들기
	public UserEntity create(final UserEntity userEntity) {
		validate(userEntity);
		return userRepository.save(userEntity);
	}
	
	// 사용자 업데이트
    public UserEntity update(final UserEntity userEntity) {
        //validate(userEntity);

        // 유저 있는지 확인
    	System.out.println("hi");
    	final Optional<UserEntity>original=userRepository.findById(userEntity.getId());
    	System.out.println("hi2");
    	if(original.isPresent()) {
    		final UserEntity tmp=original.get();
    		tmp.setUsername(userEntity.getUsername());
    		userRepository.save(tmp);
    	}
//        if (!userRepository.existsById(userEntity.getId())) {
//        	System.out.println("alsdkfjlsakdjflksadf");
//            throw new RuntimeException("User not found with ID: " + userEntity.getId());
//        }

        return null;
    }
	
	//사용자 삭제
	public void delete(final UserEntity userEntity) {
		validate(userEntity);
		
		try {
			userRepository.delete(userEntity);
		}catch(Exception e) {
			log.error("error deleting entity",userEntity.getId(),e);
			
			throw new RuntimeException("Error Deleting Entity " + userEntity.getId());
		}
	}
	
	//입력받은 정보 맞는지 확인
	public UserEntity getByCredentials(final String email, final String password, final PasswordEncoder encoder) {
		final UserEntity originalUser = userRepository.findByEmail(email);
		
		if(originalUser != null && 
		   encoder.matches(password, originalUser.getPassword())) {
			return originalUser;
		}
		
		return null;
	}
	
	// 모든 유저들의 정보 리턴
	public List<UserEntity> retrieveAll() {
		return userRepository.findAll();
	}
	
	// 나의 정보 리턴
	public List<UserEntity> retrieveMyEntity(String id) {
		Optional<UserEntity> target = userRepository.findById(id);
		
		if(target == null) throw new RuntimeException("id is not valid.");
		
		List<UserEntity> list = List.of(userRepository.findById(id).get());
		return list;
	}
	
	//검증
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