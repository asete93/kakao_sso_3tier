package com.camel.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.camel.api.services.token.service.JwtTokenService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/token")
@CrossOrigin
public class JwtTokenController {
    
    @Autowired
    JwtTokenService jwtTokenService;


    @PostMapping("/refresh")
    public ResponseEntity<Void> refreshToken(@CookieValue(value = "refresh_token", required = false) String refreshToken, HttpServletRequest request) {
        try {
            // Refresh Token이 없으면 Unauthorized 응답
            if (refreshToken == null) {
                return ResponseEntity.status(401).build(); // Unauthorized
            }

            // Refresh Token이 유효하지 않으면 Unauthorized 응답
            if (!jwtTokenService.verifyToken(refreshToken)){
                return ResponseEntity.status(401).build(); // Unauthorized
            }

            // Refresh Token이 유효하면 새로운 Access Token 생성
            ResponseCookie accessCookie = ResponseCookie.from("access_token", jwtTokenService.createAccessTokenByRefreshToken(refreshToken))
                    .httpOnly(true)
                    .secure(request.isSecure())
                    .path("/")
                    .maxAge(jwtTokenService.ACCESS_TOKEN_TIME)
                    .sameSite("Lax")
                    .build();

            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.SET_COOKIE, accessCookie.toString());

            return new ResponseEntity<>(null, headers, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(500).build(); // Internal Server Error
        }
    }
}
