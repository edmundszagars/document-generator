package com.ez.gateway.filter;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@Component
public class RouterValidator {

    private final List<String> openEndpoints = new ArrayList<>();

    public RouterValidator() {
        openEndpoints.add("/users/create");
        openEndpoints.add("/users/login");
    }

    public Predicate<ServerHttpRequest> isSecured =
            request -> openEndpoints
                    .stream()
                    .noneMatch(uri -> request.getURI()
                            .getPath()
                            .contains(uri));
}
