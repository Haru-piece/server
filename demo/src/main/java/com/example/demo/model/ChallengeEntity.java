package com.example.demo.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


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
	private String category;
	private Integer participantCount;
	
	//ç������ ������ �������� ������ ����
	@OneToMany(mappedBy = "challenge")
	private List<ParticipatingChallengeEntity> participatingChallengeEntities = new ArrayList<ParticipatingChallengeEntity>();
}

