package com.gateway.filter;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

//in this class, we'll declare all open and secured endpoints...
@Component
public class RouteValidator {

    //open endpoints...
    public static final List<String> openApiEndpoints = List.of(
            "/grabstore2/user/register",
            "/grabstore2/user/login",
            "/grabstore2/user/update",
            "/grabstore2/user/delete",
            "/grabstore2/user/find",
            "/grabstore2/user/findall"
    );

    //all endpoints other than open endpoints are secured...
    public Predicate<ServerHttpRequest> isSecured =
            request -> openApiEndpoints
                    .stream()
                    .noneMatch(uri -> request.getURI().getPath().contains(uri));

}