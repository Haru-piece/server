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
	private String id; // �������� �����ϰ� �ο��Ǵ� id.

	@Column(nullable = false)
	private String username; // ������ �̸�

	@Column(nullable = false)
	private String email; // ������ email, ���̵�� ���� ����� �Ѵ�.

	@Column(nullable = false)
	private String password; // �н�����
	

	/*     Dictionary�� �ڷᱸ�� ���� ����        */
	@ElementCollection
	private List<String> recentViewChallengeId; //������ �ֱٿ� ��ȸ�� ç�������� Id�� ����
		
	@OneToMany(mappedBy = "user")
	private List<ParticipatingChallengeEntity> participatingChallengeEntities = new ArrayList<ParticipatingChallengeEntity>();
	
	@OneToMany(mappedBy = "user")
	private List<ParticipantWhoHasBadgeEntity> participantWhoHasBadge = new ArrayList<ParticipantWhoHasBadgeEntity>();

	private Integer createCount = 0;
	private Integer participateCount = 0;
}