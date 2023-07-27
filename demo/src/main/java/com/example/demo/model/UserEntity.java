package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;

import javax.persistence.CascadeType;

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
	private String id; // 유저에게 고유하게 부여되는 id.

	@Column(nullable = false)
	private String username; // 유저의 이름

	@Column(nullable = false)
	private String email; // 유저의 email, 아이디와 같은 기능을 한다.

	@Column(nullable = false)
	private String password; // 패스워드
	
	//초기에 임시 Challenge를 할당해줄 때 뜨는
	//"java.lang.IllegalStateException: org.hibernate.TransientPropertyValueException: 
	//object references an unsaved transient instance - save the transient instance before flushing"
	//에러를 해결하기 위해 cascade 옵션을 붙였다.
	//auth/all을 하기 위한 옵션이니
	//후에 auth/all을 사용하지 않아도 될 경우 없애도 됨.
	
	@ManyToOne(cascade = {CascadeType.ALL}) 
	@JoinColumn(name = "CHALLENGE_ID")
	private ChallengeEntity challenge;
}