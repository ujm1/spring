package com.oracle.oBootMybatis01.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import com.oracle.oBootMybatis01.handler.SocketHandler;

@Configuration
@EnableWebSocket	
public class WebSocketConfig implements WebSocketConfigurer {

	//webSocket에서는 SocketHandler가 Server역할
	@Autowired //이거 넣어서 오류 해결
	SocketHandler socketHandler;
	
	
	//누군가 url /chating -> socketHandler 발동
	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(socketHandler, "/chating");

	}

}
