package com.example.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

  // 메시지 브로커 구성을 위한 메소드
  @Override
  public void configureMessageBroker(MessageBrokerRegistry config) {
	// 클라이언트로부터 메시지를 전달하는 데 사용할 메시지 브로커를 활성화
	config.enableSimpleBroker("/topic");
    
	// 클라이언트에서 메시지를 송신할 때 사용할 애플리케이션 경로 접두사 설정
	config.setApplicationDestinationPrefixes("/app");
  }

  // STOMP 엔드포인트를 등록하는 메소드
  @Override
  public void registerStompEndpoints(StompEndpointRegistry registry) {
	// 클라이언트가 웹소켓 연결을 맺을 수 있는 엔드포인트 등록
	registry.addEndpoint("/gs-guide-websocket").withSockJS();
  }

}