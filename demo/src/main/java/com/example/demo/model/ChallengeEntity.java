package com.example.demo.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;  

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
	private String id;
	private String userId;
	private String title;
	private boolean done;
	private LocalDateTime addedDate;
	private Integer participantCount;
}

