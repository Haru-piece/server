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
	private String id; // �������� �����ϰ� �ο��Ǵ� id.

	@Column(nullable = false)
	private String username; // ������ �̸�

	@Column(nullable = false)
	private String email; // ������ email, ���̵�� ���� ����� �Ѵ�.

	@Column(nullable = false)
	private String password; // �н�����
	
	//�ʱ⿡ �ӽ� Challenge�� �Ҵ����� �� �ߴ�
	//"java.lang.IllegalStateException: org.hibernate.TransientPropertyValueException: 
	//object references an unsaved transient instance - save the transient instance before flushing"
	//������ �ذ��ϱ� ���� cascade �ɼ��� �ٿ���.
	//auth/all�� �ϱ� ���� �ɼ��̴�
	//�Ŀ� auth/all�� ������� �ʾƵ� �� ��� ���ֵ� ��.
	
	@ManyToOne(cascade = {CascadeType.ALL}) 
	@JoinColumn(name = "CHALLENGE_ID")
	private ChallengeEntity challenge;
}