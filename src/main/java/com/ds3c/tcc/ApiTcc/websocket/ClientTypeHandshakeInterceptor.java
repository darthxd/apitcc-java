package com.ds3c.tcc.ApiTcc.websocket;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

@Component
public class ClientTypeHandshakeInterceptor implements HandshakeInterceptor {

    @Override
    public boolean beforeHandshake(
            ServerHttpRequest request,
            ServerHttpResponse response,
            WebSocketHandler wsHandler,
            Map<String, Object> attributes) {
        System.out.println("Handshake recieved from: "+request.getRemoteAddress());
        System.out.println("URI: "+request.getURI());
        System.out.println("Headers: "+request.getHeaders());
        String uri = request.getURI().toString();
        if (uri.contains("client=esp32")) {
            attributes.put("clientType", "esp32");
        } else {
            attributes.put("clientType", "frontend");
        }
        return true;
    }

    @Override
    public void afterHandshake(
            ServerHttpRequest request,
            ServerHttpResponse response,
            WebSocketHandler wsHandler,
            Exception e) {}
}
