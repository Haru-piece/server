package com.example.demo.dto;

import java.util.List;

import com.example.demo.model.UserEntity.UserEntityBuilder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ResponseDTO<T> {
	private String error;
	private String deleteMessage;
	private List<T> data;
}
