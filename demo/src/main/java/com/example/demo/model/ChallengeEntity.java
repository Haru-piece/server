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
import javax.persistence.JoinColumn;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "Challenge")
@Entity
public class ChallengeEntity {
	@Id
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid", strategy = "uuid")
	@Column(name = "CHALLENGE_ID")
	private String id;
	private String userId;
	private String title;
	private boolean done;
	private LocalDateTime addedDate;
	private Integer participantCount;
	
	//챌린지에 참여한 유저들의 정보를 저장
	@OneToMany(mappedBy = "challenge")
	private List<UserEntity> challengers = new ArrayList<UserEntity>();

}

