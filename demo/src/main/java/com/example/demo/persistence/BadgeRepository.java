package com.example.demo.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.model.BadgeEntity;


@Repository
public interface BadgeRepository extends JpaRepository<BadgeEntity, String>{
	BadgeEntity findByName(String name);
}
