package com.example.demo.model;

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
@Table(name = "Badge")
@Entity
public class BadgeEntity {
	@Id
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid", strategy = "uuid")
	@Column(name = "BADGE_ID")
	private String id;
	private String name;
	
	//뱃지의 이미지 경로
	private String ImagePath;
	
	//챌린지에 참여한 유저들의 정보를 저장
	@OneToMany(mappedBy = "badge")
	private List<ParticipantWhoHasBadgeEntity> participantWhoHasBadge = new ArrayList<ParticipantWhoHasBadgeEntity>();
}
