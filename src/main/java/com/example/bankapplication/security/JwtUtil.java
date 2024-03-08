package com.example.bankapplication.security;

import com.example.bankapplication.entity.Client;
import io.jsonwebtoken.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import javax.naming.AuthenticationException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
public class JwtUtil {
    private final String secret_key = "secret";
    private long accessTokenValidity = 24*60*60;
    private final JwtParser jwtParser;
    private final String TOKEN_HEADER = "Authorization";
    private final String TOKEN_PREFIX = "Bearer ";

    public JwtUtil() {
        this.jwtParser = Jwts.parser().setSigningKey(secret_key);
    }

    public String createToken(Client client) {
        Claims claims = Jwts.claims().setSubject(client.getEmail());
        claims.put("firstName", client.getFirstName());
        claims.put("lastName", client.getLastName());
        Date tokenCreationTime = new Date();
        Date tokenValidityTime = new Date(tokenCreationTime.getTime() + TimeUnit.SECONDS.toMillis(accessTokenValidity));
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(tokenValidityTime)
                .signWith(SignatureAlgorithm.HS256, secret_key)
                .compact();
    }

    private Claims parseJwtClaims(String token) {
        return jwtParser.parseClaimsJws(token).getBody();
    }

    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(TOKEN_HEADER);
        if (bearerToken != null && bearerToken.startsWith(TOKEN_PREFIX)) {
            return bearerToken.substring(TOKEN_PREFIX.length());
        }
        return null;
    }

    public Claims resolveClaims(HttpServletRequest request) {
        try {
            String token = resolveToken(request);
            if (token != null) {
                return parseJwtClaims(token);
            }
            return null;
        } catch (ExpiredJwtException ex) {
            request.setAttribute("expired", ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            request.setAttribute("invalid", ex.getMessage());
            throw ex;
        }
    }

    public boolean validateClaims(Claims claims) throws AuthenticationException {
        try {
            return claims.getExpiration().after(new Date());
        } catch (Exception ex) {
            throw ex;
        }
    }
//
//    public String getEmails(Claims claims) {
//        return claims.getSubject();
//    }
//
//    public List<String> getRoles(Claims claims) {
//        return (List<String>) claims.get("roles");
//    }
}
