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

//누가 어떤 뱃지를 가지고 있는지를 저장
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "ParticipantWhoHasBadge")
@Entity
public class ParticipantWhoHasBadgeEntity {
	@Id
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid", strategy = "uuid")
	@Column(name = "PARTICIPANT_WHO_HAS_BADGE_ID")
	private String id;
	
	@ManyToOne
	@JoinColumn(name = "BADGE_ID")
	private BadgeEntity badge;
	
	@ManyToOne
	@JoinColumn(name = "USER_ID")
	private UserEntity user;
	
}