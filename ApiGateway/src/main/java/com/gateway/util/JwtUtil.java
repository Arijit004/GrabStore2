package com.gateway.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Base64;

//this class will be used in filter layer. here we'll generate key and validate token...
@Component
public class JwtUtil {

    //same secret key used in jwtservice inside UserAuth. project...
    public static final String SECRET = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";

    //validate token...
    public void validateToken(final String token) {
        Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token);
    }

    //get key...
    private Key getSignKey() {
        byte[] keyBytes = Base64.getUrlDecoder().decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}

