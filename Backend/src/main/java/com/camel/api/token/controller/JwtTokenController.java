package com.camel.api.token.controller;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

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

import com.camel.api.token.service.JwtTokenService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/token")
@CrossOrigin
public class JwtTokenController {
    
    @Autowired
    JwtTokenService jwtTokenService;


    /* ****************************************************************************************
     * Title            :   Refresh Token으로 Access Token 발급 API
     * Scope            :   public
     * Method           :   Post
     * Function Name    :   refreshToken                                                    
     * ----------------------------------------------------------------------------------------
     * Description      :   access_token이 만료된 경우, refresh_token이 있다면 access_token을 갱신
     * 
     ******************************************************************************************/
    @PostMapping("/refresh")
    public ResponseEntity<Void> refreshToken(@CookieValue(value = "refresh_token", required = false) String refreshToken, HttpServletRequest request) {
        try {
            // Refresh Token이 없으면 Unauthorized 응답
            if (refreshToken == null) {
                return ResponseEntity.status(401).build();
            }

            // Refresh Token이 유효하지 않으면 Unauthorized 응답
            if (!jwtTokenService.verifyToken(refreshToken)){
                return ResponseEntity.status(401).build();
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

            // _xjd 값은, Frontend에서 로그인 유무를 판단하는 값으로, refresh_token / access_token에 id claim이 있다면, 회원가입된 유저로 판단하여 _xjd 쿠키를 반환한다.
            if(jwtTokenService.getIdFromToken(refreshToken) != null && jwtTokenService.getIdFromToken(refreshToken) > 0) {
                // Refresh Token이 유효한 경우, 사용자 정보를 가져와 쿠키에 저장
                String username = jwtTokenService.tokenParser(refreshToken).getString("userName");
                String encodedUsername = Base64.getEncoder().encodeToString(username.getBytes(StandardCharsets.UTF_8));

                ResponseCookie userNameCookie = ResponseCookie.from("_xjd", encodedUsername)
                    .httpOnly(true)
                    .secure(request.isSecure())
                    .path("/")
                    .maxAge(jwtTokenService.REFRESH_TOKEN_TIME)
                    .sameSite("Lax")
                    .build();

                headers.add(HttpHeaders.SET_COOKIE, userNameCookie.toString());
            }

            return new ResponseEntity<>(null, headers, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build(); // Internal Server Error
        }
    }
}
