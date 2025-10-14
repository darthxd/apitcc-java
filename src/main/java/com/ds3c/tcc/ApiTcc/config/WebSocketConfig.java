package com.ds3c.tcc.ApiTcc.config;

import com.ds3c.tcc.ApiTcc.websocket.ClientTypeHandshakeInterceptor;
import com.ds3c.tcc.ApiTcc.websocket.WebSocketRouter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final WebSocketRouter webSocketRouter;
    private final ClientTypeHandshakeInterceptor handshakeInterceptor;

    @Autowired
    public WebSocketConfig(
            WebSocketRouter webSocketRouter,
            ClientTypeHandshakeInterceptor handshakeInterceptor) {
        this.webSocketRouter = webSocketRouter;
        this.handshakeInterceptor = handshakeInterceptor;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(webSocketRouter, "/ws")
                .addInterceptors(handshakeInterceptor)
                .setAllowedOrigins("*");
    }
}
