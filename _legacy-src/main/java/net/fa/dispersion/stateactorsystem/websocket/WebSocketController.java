//package net.fa.dispersion.stateactorsystem.websocket;
//
//import org.springframework.stereotype.Controller;
//import org.springframework.web.socket.CloseStatus;
//import org.springframework.web.socket.TextMessage;
//import org.springframework.web.socket.WebSocketMessage;
//import org.springframework.web.socket.WebSocketSession;
//import org.springframework.web.socket.handler.TextWebSocketHandler;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//
//@Controller
//public class WebSocketController extends TextWebSocketHandler {
//
//    List<WebSocketSession> webSocketSessions = Collections.synchronizedList(new ArrayList<>());
//
//    @Override
//    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
//        super.afterConnectionEstablished(session);
//        System.out.printf("Session connected: %s%n", session);
//        webSocketSessions.add(session);
//    }
//
//    @Override
//    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
//        super.afterConnectionClosed(session, status);
//        System.out.printf("Session disconnected: %s%n status: %s%n", session, status);
//        webSocketSessions.remove(session);
//    }
//
//    @Override
//    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
//
//        super.handleMessage(session, message);
//        for (WebSocketSession webSocketSession : webSocketSessions) {
//            webSocketSession.sendMessage(new TextMessage("You just sent: " + message.getPayload()));
//        }
//    }
//
//}