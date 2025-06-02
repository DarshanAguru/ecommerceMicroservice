package com.ecommerce.gatewayServer;


import io.jsonwebtoken.security.Keys;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {


    private final JwtUtil jwtUtil;

    public AuthenticationFilter(JwtUtil jwtUtil) {
        super(Config.class);
        this.jwtUtil = jwtUtil;
    }


    private Mono<Void> onError(ServerWebExchange exchange, String message, int statusCode) {
        exchange.getResponse().setStatusCode(org.springframework.http.HttpStatus.valueOf(statusCode));
        exchange.getResponse().getHeaders().add("Content-Type", "application/json");
        String json = "{\"error\": \"" + message + "\"}";
        byte[] bytes = json.getBytes(java.nio.charset.StandardCharsets.UTF_8);
        org.springframework.core.io.buffer.DataBuffer buffer = exchange.getResponse()
                .bufferFactory()
                .wrap(bytes);
        return exchange.getResponse().writeWith(Mono.just(buffer));
    }

    @Override
    public GatewayFilter apply(AuthenticationFilter.Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            String path = request.getURI().getPath();
            if(path.startsWith("/api/v1/auth"))
            {
                // Skip authentication for authentication endpoints
                PasswordEncoder encoder = new BCryptPasswordEncoder();
                String key = encoder.encode("my-secret-key");
                ServerHttpRequest mutableReq = request.mutate().header("X-Gateway-Key", key).build();
                return chain.filter(exchange.mutate().request(mutableReq).build());
            }
            if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                return this.onError(exchange, "Missing Authorization Header", 401);
            }

            String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return this.onError(exchange, "Invalid Authorization Header Format", 401);
            }

            authHeader = authHeader.substring(7);
            try {
                if(!jwtUtil.validate(authHeader))
                {
                    return this.onError(exchange, "Invalid Token", 401);
                }
                if(!jwtUtil.validateRole(authHeader, config.requiredRole))
                {
                    return this.onError(exchange, "Forboidden Access for your Role", 403);
                }
            } catch (Exception ex) {
                System.out.println("Invalid Token: " + ex.getMessage());
                return this.onError(exchange, "Unauthorised Access: " + ex.getMessage(), 401);
            }
            PasswordEncoder encoder = new BCryptPasswordEncoder();
            String userId = jwtUtil.getUserId(authHeader);
            String key = encoder.encode("my-secret-key");
            ServerHttpRequest mutableReq = request.mutate().header("X-Gateway-Key", key)
                    .header("X-User-Id", userId).build();

            return chain.filter(exchange.mutate().request(mutableReq).build());
        };
    }

    public static class Config {
        private String requiredRole;

        public String getRequiredRole() {
            return requiredRole;
        }

        public void setRequiredRole(String requiredRole) {
            this.requiredRole = requiredRole;
        }
    }
}
