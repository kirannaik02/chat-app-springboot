package com.chat.config;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.security.Principal;
import java.util.Map;

public class CustomHandshakeHandler extends DefaultHandshakeHandler {

    @Override
    protected Principal determineUser(ServerHttpRequest request,
                                      WebSocketHandler wsHandler,
                                      Map<String, Object> attributes) {
        // Extract userId from query param (e.g., ?userId=abc123)
        String query = request.getURI().getQuery();
        final String userId;

        if (query != null && query.startsWith("userId=")) {
            userId = query.substring("userId=".length());
        } else {
            userId = "anonymous-" + System.currentTimeMillis(); // fallback
        }

        return () -> userId; // Lambda OK now because userId is final
    }
}
