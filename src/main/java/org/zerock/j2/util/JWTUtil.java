package org.zerock.j2.util;

import io.jsonwebtoken.*;
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

    public static class CustomJWTException extends RuntimeException{
        public CustomJWTException(String msg){
            super(msg);
        }
    }

    @Value("${org.zerock.jwt.secret}")
    private String key;
    
    public String generate(Map<String, Object> claimMap, int min){

        // header 생성
        Map<String, Object> headers = new HashMap<>();
        headers.put("typ","JWT");

        //claims 생성
        Map<String, Object>claims = new HashMap<>();
        claims.putAll(claimMap);

        SecretKey key = null;
        try {
            key = Keys.hmacShaKeyFor(this.key.getBytes(StandardCharsets.UTF_8));
        }catch(Exception e){

        }
        String jwtStr = Jwts.builder()
                .setHeader(headers)
                .setClaims(claims)
                .setIssuedAt(Date.from(ZonedDateTime.now().toInstant()))
                .setExpiration(
                        Date.from(ZonedDateTime.now().plusMinutes(min).toInstant()))
                .signWith(key)
                .compact();
        return  jwtStr;
    }

    // claims 를 가져오는
    public Map<String, Object> validateToken(String token){

        Map<String, Object> claims =null;

        if(token == null){
            throw new CustomJWTException("NullToken");
        }

        try{
           SecretKey key = Keys.hmacShaKeyFor(this.key.getBytes(StandardCharsets.UTF_8));

           claims = Jwts.parserBuilder().setSigningKey(key).build()
                   .parseClaimsJws(token).getBody();

         // 문자열 구조, 기간만료, 잘못된 정보, jwt에러의 경우
        }catch(MalformedJwtException e) {
            throw new CustomJWTException("MalFormed");
        }catch(ExpiredJwtException e){
            throw new CustomJWTException("Expired");
        }catch (InvalidClaimException e){
            throw new CustomJWTException("Invalid");
        }catch(JwtException e){
            throw new CustomJWTException(e.getMessage());
        }catch (Exception e){
            throw new CustomJWTException("Error");
        }

        return claims;
    }
}
