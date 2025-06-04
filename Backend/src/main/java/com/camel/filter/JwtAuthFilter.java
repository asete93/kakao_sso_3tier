package com.camel.filter;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

import com.camel.api.token.service.JwtTokenService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAuthFilter extends OncePerRequestFilter{

    private final JwtTokenService jwtTokenService;

    public JwtAuthFilter(JwtTokenService jwtTokenService) {
        this.jwtTokenService = jwtTokenService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = null;

        String path = request.getRequestURI();

        // logout은 필터에서 제외
        if (path.equals("/api/v1/user/logout")) {
            filterChain.doFilter(request, response);
            return;
        }
        
        // 1. access_token 쿠키에서 추출
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("access_token".equals(cookie.getName())) {
                    token = cookie.getValue();
                    break;
                }
            }
        }

        // 2. 검증 실패 시 바로 401 응답
        if (token == null || !jwtTokenService.verifyToken(token)) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().write("Invalid access token");
            return;
        }


        // 3. 검증 성공 시, 요청에 사용자 정보 추가
        try {
            Integer id = jwtTokenService.getIdFromToken(token);
            if (id != null && id > 0) {
                request.setAttribute("id", id);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 3. 성공 시 다음 필터로
        filterChain.doFilter(request, response);
    }

}
