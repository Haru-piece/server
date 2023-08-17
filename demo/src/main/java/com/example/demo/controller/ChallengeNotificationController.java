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
        // ���⼭ �˸��� ó���ϰ�, ��ȣ�ϴ� ī�װ��� ���� �����鿡�� �˸��� �����մϴ�.
        // ��: notification.getCategory()�� �̿��� ��ȣ ī�װ��� ��ġ�ϴ� �����鿡�� �˸� ����
        return notification;
    }
	
}