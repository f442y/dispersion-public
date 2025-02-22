package com.github.f442y.dispersion.orchestrator.state.webapi.websocket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(wsHandler(), "/hello").setAllowedOrigins("*");
//        registry.addHandler(wsHandler(), "/hello").setAllowedOrigins("*").withSockJS();
    }

    @Bean
    public WebSocketHandler wsHandler() {
        return new WSHandler();
    }

}
