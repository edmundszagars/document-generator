package com.ez.users.util;

import com.ez.users.shared.UserDto;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.core.env.Environment;

import java.util.Date;
import java.util.Objects;

public class JwtToken {

    Environment environment;

    public JwtToken(Environment environment) {
        this.environment = environment;
    }

    public String generateJwtToken(UserDto userDto) {
        Date expirationDate = getExpirationDate();
        String tokenSecret = getTokenSecret();
        return Jwts.builder()
                .setSubject(userDto.getUserId())
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, tokenSecret)
                .compact();
    }

    private String getTokenSecret() {
        return environment.getProperty("token.secret");
    }

    private Date getExpirationDate() {
        return new Date(System.currentTimeMillis() + Long.parseLong(
                Objects.requireNonNull(
                        environment.getProperty("token.expiration_time"))));
    }
}
