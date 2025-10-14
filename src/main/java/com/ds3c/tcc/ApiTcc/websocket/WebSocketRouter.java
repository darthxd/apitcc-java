package com.ds3c.tcc.ApiTcc.websocket;

import com.ds3c.tcc.ApiTcc.dto.WebSocket.WebSocketMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;

@Component
public class WebSocketRouter extends TextWebSocketHandler {
    private final DeviceSessionManager sessionManager;
    private final ObjectMapper objectMapper;

    @Autowired
    public WebSocketRouter(DeviceSessionManager sessionManager) {
        this.sessionManager = sessionManager;
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String clientType = (String) session.getAttributes().get("clientType");
        sessionManager.registerSession(clientType, session);
        System.out.println("Client connected: "+clientType+" ["+session.getId()+"]");
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        WebSocketMessage msg = objectMapper.readValue(
                message.getPayload(),
                WebSocketMessage.class
        );

        switch (msg.getType()) {
            case "PING":
                session.sendMessage(new TextMessage("{\"type\":\"PONG\"}"));
                break;
            case "READ_REQUEST":
        }
    }

    private void handleReadRequest(WebSocketSession session, WebSocketMessage msg) throws IOException {
        sessionManager.getFirstEsp32().ifPresentOrElse(esp -> {
            try {
                esp.sendMessage(new TextMessage(new ObjectMapper().writeValueAsString(msg)));
                System.out.println("Message sent to the ESP32: "+msg);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }, () -> System.out.println("There are no devices connected"));
    }

    private void broadcastToFrontend(WebSocketMessage msg) throws IOException {
        for (WebSocketSession frontend : sessionManager.getFrontendSessions()) {
            if (frontend.isOpen()) {
                frontend.sendMessage(new TextMessage(objectMapper.writeValueAsString(msg)));
            }
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        sessionManager.unregisterSession(session);
        System.out.println("Connection closed: "+session.getId());
    }
}
