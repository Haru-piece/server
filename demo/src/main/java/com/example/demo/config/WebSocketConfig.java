package com.example.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

  // �޽��� ���Ŀ ������ ���� �޼ҵ�
  @Override
  public void configureMessageBroker(MessageBrokerRegistry config) {
	// Ŭ���̾�Ʈ�κ��� �޽����� �����ϴ� �� ����� �޽��� ���Ŀ�� Ȱ��ȭ
	config.enableSimpleBroker("/topic");
    
	// Ŭ���̾�Ʈ���� �޽����� �۽��� �� ����� ���ø����̼� ��� ���λ� ����
	config.setApplicationDestinationPrefixes("/app");
  }

  // STOMP ��������Ʈ�� ����ϴ� �޼ҵ�
  @Override
  public void registerStompEndpoints(StompEndpointRegistry registry) {
	// Ŭ���̾�Ʈ�� ������ ������ ���� �� �ִ� ��������Ʈ ���
	registry.addEndpoint("/gs-guide-websocket").withSockJS();
  }

}