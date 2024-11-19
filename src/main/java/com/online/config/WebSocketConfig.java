package com.online.config;

import com.online.websocket.TicketPoolWebSocketHandler;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
@ComponentScan(basePackages = "com.online.websocket")  // Ensure this is scanning the correct package
public class WebSocketConfig implements WebSocketConfigurer {

    private final TicketPoolWebSocketHandler webSocketHandler;

    public WebSocketConfig(TicketPoolWebSocketHandler webSocketHandler) {
        this.webSocketHandler = webSocketHandler;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(webSocketHandler, "/websocket/ticketpool").setAllowedOrigins("*");
    }
}
