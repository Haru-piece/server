package com.example.demo.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.model.ParticipantWhoHasBadgeEntity;

@Repository
public interface ParticipantWhoHasBadgeRepository extends JpaRepository<ParticipantWhoHasBadgeEntity, String>{
	
}
