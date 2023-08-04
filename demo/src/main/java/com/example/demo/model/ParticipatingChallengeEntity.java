package com.example.demo.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

import javax.persistence.OneToMany;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;

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
	
	@ManyToOne
	@JoinColumn(name = "CHALLENGE_ID")
	private ChallengeEntity challenge;
	
	@ManyToOne
	@JoinColumn(name = "USER_ID")
	private UserEntity user;
	
}
