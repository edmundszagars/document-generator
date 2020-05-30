package com.ez.userdata.ui.controllers;

import io.jsonwebtoken.Jwts;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

public class CurrentUser {

    public final String sub;

    public CurrentUser() {
        HttpServletRequest request = ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest();
        String token = request.getHeader("Authorization").split(" ")[1];
        sub = extractUserIdFromToken(token);

    }

    private String extractUserIdFromToken(String jwtToken) {
        return Jwts.parser().setSigningKey("g5j3hg534jhg234ug3kjh34g5kjhg3245jh6h45kj5h6kjh43")
                .parseClaimsJws(jwtToken)
                .getBody()
                .getSubject();
    }
}
