package org.zerock.j2.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.zerock.j2.controller.interceptor.JWTInterceptor;
// servlet context 설정을 java로 한다.
@Configuration
@EnableWebMvc
@Log4j2
@RequiredArgsConstructor
public class CustomServletConfig implements WebMvcConfigurer {

    private final JWTInterceptor jwtInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor)
                .addPathPatterns("/api/**")
                .excludePathPatterns("/api/member/login", "/api/member/refresh");
    }
}
