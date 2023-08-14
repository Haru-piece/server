package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.example.demo.model.ChallengeNotification;

@Service
public class ChallengeNotificationService {

    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    public ChallengeNotificationService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void sendNotification(ChallengeNotification notification) {
        // 알림을 처리하고, 적절한 경로로 메시지 전송
        messagingTemplate.convertAndSend("/topic/challenge-notifications", notification);
    }
}