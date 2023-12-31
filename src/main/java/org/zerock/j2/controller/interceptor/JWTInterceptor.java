package org.zerock.j2.controller.interceptor;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.zerock.j2.util.JWTUtil;

import java.util.Map;

@Log4j2
@RequiredArgsConstructor
@Component
public class JWTInterceptor implements HandlerInterceptor {

    private final JWTUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        if (request.getMethod().equals("OPTIONS")) {
            return true;
        }
        try {
            String headerStr =request.getHeader("Authorization");
            if(headerStr == null ||headerStr.isEmpty() || headerStr.length()<7){
                throw new JWTUtil.CustomJWTException("NullToken");
            }

            // Bearer 엑세스토큰
            String accessToken = headerStr.substring(7);

            Map<String, Object> claims = jwtUtil.validateToken(accessToken);

            log.info("result : " + claims);

             }catch (Exception e){

                response.setContentType("application/json");

                // {"key" : "value"}
                //String str = "{\"error\": \"\" }";
                // 401 상태코드 주기
                // response.setStatus(HttpStatus.UNAUTHORIZED.value());
                Gson gson = new Gson();

                String str = gson.toJson(Map.of("error",e.getMessage()));

                response.getOutputStream().write(str.getBytes());


                return false;
            }

        log.info("---------------------------------------------");
        log.info(handler);
        log.info("---------------------------------------------");
        log.info(jwtUtil);
        log.info("---------------------------------------------");
        log.info("---------------------------------------------");

        return true;
    }
}
