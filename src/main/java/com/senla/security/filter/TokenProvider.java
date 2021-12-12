package com.senla.security.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Log4j
@Component
public class TokenProvider {

    private static final String AUTHORITIES_KEY = "auth";
    public static final String DEFAULT_TOKEN_TYPE = "Bearer ";
    @Value("$(jwt.secret)")
    private String jwtSecret;

    public String createToken(String login) {
        log.debug("Method: createToken, входящий: " + login);
        Date date = Date.from(LocalDate.now().plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant());
        String token = Jwts.builder()
                .setSubject(login)
                .setExpiration(date)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
        log.debug("Method: createToken, выходящий: " + token);
        return token;
    }

    public boolean validateToken(String token) {
        log.debug("Method: validateToken, входящий: " + token);
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            log.debug("Method: validateToken, выходящий: " + true);
            return true;
        } catch (ExpiredJwtException expEx) {
            log.error("Method: validateToken, выходящий: " + expEx.toString());
        }
        return false;
    }

    public String getLoginFromToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
        return claims.getSubject();
    }
}
