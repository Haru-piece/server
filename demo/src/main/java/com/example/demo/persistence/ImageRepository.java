package com.example.demo.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.ImageEntity;

@Repository
public interface ImageRepository extends JpaRepository<ImageEntity, String>{
	
}

