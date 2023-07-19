package org.zerock.j2.util;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@Log4j2
public class JWTUtil {

    @Value("${org.zerock.jwt.secret}")
    private String key;
    
    public String generate(Map<String, Object> claimMap, int min){
        
        // header 생성
        Map<String, Object> hearders = new HashMap<>();
        hearders.put("typ","JWT");

        //claims 생성
        Map<String, Object>claims = new HashMap<>();
        claims.putAll(claimMap);

        SecretKey key = null;
        try {
            key = Keys.hmacShaKeyFor(this.key.getBytes(StandardCharsets.UTF_8));
        }catch(Exception e){

        }
        String jwtStr = Jwts.builder()
                .setHeader(hearders)
                .setClaims(claims)
                .setIssuedAt(Date.from(ZonedDateTime.now().toInstant()))
                .setExpiration(
                        Date.from(ZonedDateTime.now().plusMinutes(min).toInstant()))
                .signWith(key)
                .compact();
        return  jwtStr;
    }
}
