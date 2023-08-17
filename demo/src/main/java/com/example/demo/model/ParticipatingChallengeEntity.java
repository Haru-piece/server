package com.example.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//누가 어떤 챌린지에 참여하고 있는지 저장
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "ParticipatingChallenge")
@Entity
public class ParticipatingChallengeEntity {
	@Id
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid", strategy = "uuid")
	@Column(name = "PARTICIPATING_CHALLENGE_ID")
	private String id;
	
	private boolean success;
	
	@ManyToOne
	@JoinColumn(name = "CHALLENGE_ID")
	private ChallengeEntity challenge;
	
	@ManyToOne
	@JoinColumn(name = "USER_ID")
	private UserEntity user;
	
}
