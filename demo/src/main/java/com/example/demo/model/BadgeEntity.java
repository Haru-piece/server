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

import javax.persistence.OneToMany;

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
	
	/*     Dictionary로 자료구조 변경 예정        */
	//챌린지에 참여한 유저들의 정보를 저장
	@OneToMany(mappedBy = "badge")
	private List<ParticipantWhoHasBadgeEntity> participantWhoHasBadge = new ArrayList<ParticipantWhoHasBadgeEntity>();
}
