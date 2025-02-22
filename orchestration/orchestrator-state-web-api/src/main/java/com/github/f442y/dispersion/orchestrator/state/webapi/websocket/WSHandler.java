package com.github.f442y.dispersion.orchestrator.state.webapi.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class WSHandler extends TextWebSocketHandler {
    private static final Logger log = LoggerFactory.getLogger(WSHandler.class);
    private final Map<String, WebSocketSession> webSocketSessionMap = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(@NonNull WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
        log.info("Session connected: {}", session);
        webSocketSessionMap.put(session.getId(), session);
        log.info("sessions map: {}", webSocketSessionMap);
    }

    @Override
    public void afterConnectionClosed(@NonNull WebSocketSession session, @NonNull CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
        log.info("Session disconnected: {} status: {}", session, status);
        webSocketSessionMap.remove(session.getId(), session);
        log.info("sessions map: {}", webSocketSessionMap);
    }

    @Override
    public void handleTextMessage(@NonNull WebSocketSession session, @NonNull TextMessage message) throws Exception {
        super.handleTextMessage(session, message);
        log.info("message received: {}", message.getPayload());
        // send to all session
        for (WebSocketSession webSocketSession : webSocketSessionMap.values()) {
            log.info("sending message to session (id: {})", webSocketSession.getId());
            webSocketSession.sendMessage(new TextMessage("Server -> You just sent: (" + message.getPayload() + ")"));
        }
    }

}