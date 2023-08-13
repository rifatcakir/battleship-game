package com.battleship.apigateway.util;

import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${battleship.jwt.secret}")
    private String jwtSecret;

    public Claims getClaims(final String token) {
        try {
            return Jwts.parser().setSigningKey(jwtSecret)
                    .parseClaimsJws(token).getBody();
        } catch (Exception e) {
            LOGGER.error("Error", e);
        }
        return null;
    }


    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            throw new SignatureException(e.getMessage());
        } catch (MalformedJwtException e) {
            throw new MalformedJwtException(e.getMessage());
        } catch (ExpiredJwtException e) {
            throw new ExpiredJwtException(null, null, e.getMessage());
        } catch (UnsupportedJwtException e) {
            throw new UnsupportedJwtException(e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }

    }
}
