package com.ecommerce.gatewayServer;

import jakarta.persistence.Column;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import reactor.netty.http.server.HttpServerRequest;

@Component
public class ProducerFilter implements GatewayFilter {


    private final JwtUtil jwtUtil;

    ProducerFilter(JwtUtil jwtUtil)
    {
        this.jwtUtil = jwtUtil;
    }
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        ServerHttpRequest request = exchange.getRequest();
        String header = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        header = header.substring(7);
        if(!jwtUtil.validateRole(header, "PRODUCER"))
        {
            return this.onError(exchange, "Forbidden access", 403);
        }


        return chain.filter(exchange);
    }

    private Mono<Void> onError(ServerWebExchange exchange, String message, int statusCode) {
        exchange.getResponse().setStatusCode(org.springframework.http.HttpStatus.valueOf(statusCode));
        byte[] bytes = message.getBytes(java.nio.charset.StandardCharsets.UTF_8);
        org.springframework.core.io.buffer.DataBuffer buffer = exchange.getResponse()
                .bufferFactory()
                .wrap(bytes);

        exchange.getResponse().getHeaders().add("Content-Type", "text/plain;charset=UTF-8");
        return exchange.getResponse().writeWith(Mono.just(buffer));
    }
}
