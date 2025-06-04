package com.camel.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.camel.api.token.service.JwtTokenService;
import com.camel.filter.JwtAuthFilter;

@Configuration
public class FilterConfig {
    @Bean(name = "jwtAuthFilter")
    public FilterRegistrationBean<JwtAuthFilter> jwtAuthFilter(JwtTokenService jwtTokenService) {
        FilterRegistrationBean<JwtAuthFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new JwtAuthFilter(jwtTokenService));

        registrationBean.addUrlPatterns("/api/v1/user/*"); // 필터를 적용할 URL 패턴 설정
        registrationBean.setOrder(1); // 필터 순서 설정 (숫자가 낮을수록 우선순위가 높음)
        return registrationBean;
    }
}
