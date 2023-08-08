package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.Column;

import javax.persistence.OneToMany;
import javax.persistence.ElementCollection;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = "email")})
public class UserEntity {
	@Id
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid", strategy = "uuid")
	@Column(name = "USER_ID")
	private String id; // 유저에게 고유하게 부여되는 id.

	@Column(nullable = false)
	private String username; // 유저의 이름

	@Column(nullable = false)
	private String email; // 유저의 email, 아이디와 같은 기능을 한다.

	@Column(nullable = false)
	private String password; // 패스워드
	

	/*     Dictionary로 자료구조 변경 예정        */
	@ElementCollection
	private List<String> recentViewChallengeId; //유저가 최근에 조회한 챌린지들의 Id를 저장
		
	@OneToMany(mappedBy = "user")
	private List<ParticipatingChallengeEntity> participatingChallengeEntities = new ArrayList<ParticipatingChallengeEntity>();
	
	@OneToMany(mappedBy = "user")
	private List<ParticipantWhoHasBadgeEntity> participantWhoHasBadge = new ArrayList<ParticipantWhoHasBadgeEntity>();

	private Integer createCount = 0;
	private Integer participateCount = 0;
}