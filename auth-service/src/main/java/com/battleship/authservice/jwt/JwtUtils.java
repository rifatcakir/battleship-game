package com.battleship.authservice.jwt;

import com.battleship.authservice.security.CustomUserDetails;
import com.battleship.authservice.security.CustomUserDetailsService;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${battleship.jwt.secret}")
    private String jwtSecret;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    public String generateJwtToken(String username) {
        return generateTokenFromUsername(username);
    }

    public String generateTokenFromUsername(String username) {
        CustomUserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
        StringBuilder roles = new StringBuilder();
        userDetails.getAuthorities().forEach(role -> {
            roles.append(role.getAuthority()).append(" ");
        });
        return Jwts.builder()
                .setSubject(username)
                .claim("userId", userDetails.getId().toString())
                .setIssuer(roles.toString())
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            LOGGER.error("JwtUtils | validateJwtToken | Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            LOGGER.error("JwtUtils | validateJwtToken | Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            LOGGER.error("JwtUtils | validateJwtToken | JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            LOGGER.error("JwtUtils | validateJwtToken | JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            LOGGER.error("JwtUtils | validateJwtToken | JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }
}
