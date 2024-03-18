package com.example.encypher.conf;

import com.example.encypher.entity.AppUser;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.function.Function;

import org.slf4j.Logger;

@Component
public class JwtService {

    private static final long EXPIRATION_DURATION = 24 * 60 * 60 * 1000; // 24 hours
    private static final String SECRET_KEY = "384f4d384e6a30704d3477786e58615253617251792f436b32434b59373931446c70586b6f7255304e4c72437570316530396f773153695463564f30304f634f";
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtService.class);

    public String generateAccessToken(AppUser user) {
        return Jwts.builder()
                .setSubject(String.format("%s,%s", user.getId(), user.getEmail()))
                .setIssuer("PrivateJava")
                .claim("roles", user.getRoles().toString())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_DURATION))
                .signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()))
                .compact();

    }

    public boolean validateAccessToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(SECRET_KEY.getBytes());
            return true;
        } catch (ExpiredJwtException exception) {
            LOGGER.error("JWT expired {}", exception.getMessage());
        } catch (IllegalArgumentException exception) {
            LOGGER.error("Token is null, empty or only whitespace {}", exception.getMessage());
        } catch (MalformedJwtException exception) {
            LOGGER.error("JWT is invalid", exception);
        } catch (UnsupportedJwtException exception) {
            LOGGER.error("JWT is not supported", exception);
        } catch (SecurityException exception) {
            LOGGER.error("Signature validation failed");
        }
        return false;

    }

    public Claims parseClaims(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY.getBytes())
                .parseClaimsJws(token)
                .getBody();
    }
}
