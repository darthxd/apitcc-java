package com.ds3c.tcc.ApiTcc.websocket;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class DeviceSessionManager {
    private final Map<String, WebSocketSession> devices = new ConcurrentHashMap<>();
    private final Map<String, WebSocketSession> frontends = new ConcurrentHashMap<>();

    public void registerSession(String clientType, WebSocketSession session) {
        if ("esp32".equals(clientType)) {
            devices.put(session.getId(), session);
        } else {
            frontends.put(session.getId(), session);
        }
    }

    public void unregisterSession(WebSocketSession session) {
        devices.remove(session.getId());
        frontends.remove(session.getId());
    }

    public Collection<WebSocketSession> getEsp32Sessions() {
        return devices.values();
    }

    public Collection<WebSocketSession> getFrontendSessions() {
        return frontends.values();
    }

    public Optional<WebSocketSession> getFirstEsp32() {
        return devices.values().stream().findFirst();
    }
}
