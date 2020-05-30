package com.ez.gateway.security;

import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.bind.annotation.RequestHeader;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class AuthorisationFilter extends BasicAuthenticationFilter {
    private Environment environment;

    public AuthorisationFilter(AuthenticationManager authenticationManager, Environment environment) {
        super(authenticationManager);
        this.environment = environment;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {
        String authHeader = getAuthHeader(request);
        if (authHeader == null || !authHeader.startsWith(
                Objects.requireNonNull(environment.getProperty("authorisation.token.header.prefix")))) {
            chain.doFilter(request, response);
            return;
        }
        UsernamePasswordAuthenticationToken authenticationToken = getAuthenticationToken(request);

        String jwtToken = extractJwtFromHeaderValue(authHeader);
        response.addHeader("userId", extractUserIdFromToken(jwtToken));

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthenticationToken(HttpServletRequest request) {
        String authHeader = getAuthHeader(request);
        if (authHeader == null) {
            return null;
        }
        String jwtToken = extractJwtFromHeaderValue(authHeader);
        String userId = extractUserIdFromToken(jwtToken);

        if (userId == null) {
            return null;
        }
        return new UsernamePasswordAuthenticationToken(userId, null, new ArrayList<>());
    }

    private String extractUserIdFromToken(String jwtToken) {
        return Jwts.parser().setSigningKey(environment.getProperty("token.secret"))
                .parseClaimsJws(jwtToken)
                .getBody()
                .getSubject();
    }

    private String extractJwtFromHeaderValue(String authHeader) {
        return authHeader.replace(
                Objects.requireNonNull(environment.getProperty("authorisation.token.header.prefix")), "");
    }

    private String getAuthHeader(HttpServletRequest request) {
        return request.getHeader(environment.getProperty("authorisation.token.header"));
    }
}
