package com.example.demo.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.example.demo.model.ChallengeNotification;

@Controller
public class ChallengeNotificationController {

	@MessageMapping("/challenge-notification")
    @SendTo("/topic/challenge-notifications")
    public ChallengeNotification sendChallengeNotification(ChallengeNotification notification) {
        // 여기서 알림을 처리하고, 선호하는 카테고리를 가진 유저들에게 알림을 전송합니다.
        // 예: notification.getCategory()를 이용해 선호 카테고리와 일치하는 유저들에게 알림 전송
        return notification;
    }
	
}