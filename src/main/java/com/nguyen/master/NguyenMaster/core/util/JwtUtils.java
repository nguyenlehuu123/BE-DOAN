package com.nguyen.master.NguyenMaster.core.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
@Slf4j
public class JwtUtils {
    @Value("${bezkoder.app.jwtSecret}")
    private String jwtSecret;

    @Value("${bezkoder.app.accessTokenExpirationMs}")
    private long accessTokenExpirationMs;


    @Value("${bezkoder.app.refreshTokenExpirationMs}")
    private long refreshTokenExpirationMs;

    public String generatorToken(String userId) {
        return generatorJwt(userId, accessTokenExpirationMs);
    }

    public String generatorRefreshTokenToken(String userId) {
        return generatorJwt(userId, refreshTokenExpirationMs);
    }

    public String generatorJwt(String userId, long jwtExpirationMs) {
        return Jwts.builder()
                .setSubject(userId)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public Claims getClaimsFromJwt(String token) {
        Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
        return claims;
    }

    public String getUserIdFromJwt(String token) {
        return getClaimsFromJwt(token).getSubject();
    }

    public boolean isValidateToken(String authToken) {
        try {
            Claims claims = getClaimsFromJwt(authToken);
            Date now = new Date();
            return now.before(claims.getExpiration());
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty.");
        }
        return false;
    }

}
