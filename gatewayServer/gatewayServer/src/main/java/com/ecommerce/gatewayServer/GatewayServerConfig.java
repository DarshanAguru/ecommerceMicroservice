//package com.ecommerce.gatewayServer;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cloud.gateway.route.RouteLocator;
//import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class GatewayServerConfig
//{
//    @Autowired
//    private AuthenticationFilter authFilter;
//
//    @Autowired
//    private ProducerFilter producerFilter;
//
//
//    @Bean
//    public RouteLocator routes(RouteLocatorBuilder rb)
//    {
//        return rb.routes()
//                .route("auth-server", r -> r.path("/api/v1/auth/**").filters(f -> f.filter(authFilter,1).filter(producerFilter,2)).uri("lb://auth-server"))
//                .build();
//    }
//
//}
