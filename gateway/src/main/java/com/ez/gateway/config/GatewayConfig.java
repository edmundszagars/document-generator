package com.ez.gateway.config;

import com.ez.gateway.filter.AuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableHystrix
public class GatewayConfig {

    private AuthenticationFilter authenticationFilter;

    @Autowired
    public GatewayConfig(AuthenticationFilter authenticationFilter) {
        this.authenticationFilter = authenticationFilter;
    }

    @Bean
    public RouteLocator routes(RouteLocatorBuilder locatorBuilder) {
        return locatorBuilder.routes()
                .route("users", r -> r.path("/users/**")
                        .filters(f -> f.filter(authenticationFilter))
                        .uri("lb://users"))
                .build();
    }
}
