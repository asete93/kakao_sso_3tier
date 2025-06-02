package com.camel.api.controller;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.camel.api.services.token.service.JwtTokenService;
import com.camel.api.services.user.dao.User;
import com.camel.api.services.user.service.UserService;
import com.camel.common.CustomMap;
import com.camel.common.cusotmException.ThrowCustomMapException;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/user")
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenService jwtTokenService;

    @PostMapping("/logout")
    public ResponseEntity<CustomMap> logout(HttpServletRequest request) {
        ResponseCookie accessCookie = ResponseCookie.from("access_token", "")
                .httpOnly(true)
                .secure(request.isSecure())
                .path("/")
                .maxAge(0)
                .sameSite("Lax")
                .build();

        ResponseCookie refreshCookie = ResponseCookie.from("refresh_token", "")
                .httpOnly(true)
                .secure(request.isSecure())
                .path("/")
                .maxAge(0)
                .sameSite("Lax")
                .build();

         ResponseCookie userNameCookie = ResponseCookie.from("_xjd", "")
                .httpOnly(true)
                .secure(request.isSecure())
                .path("/")
                .maxAge(0)
                .sameSite("Lax")
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.SET_COOKIE, accessCookie.toString());
        headers.add(HttpHeaders.SET_COOKIE, refreshCookie.toString());
        headers.add(HttpHeaders.SET_COOKIE, userNameCookie.toString());

        return new ResponseEntity<>(null, headers, HttpStatus.NO_CONTENT);
    }

    @PostMapping("/signup")
    public ResponseEntity<CustomMap> signup(HttpServletRequest request) {
        CustomMap updatedTokenMap = null;
        try {
            CustomMap tokenMap = null;
            String token = "";

            // 1. access_token 쿠키에서 추출
            if (request.getCookies() != null) {
                for (Cookie cookie : request.getCookies()) {
                    if ("access_token".equals(cookie.getName())) {
                        token = cookie.getValue();
                        break;
                    }
                }
            }

            // 2. access_token 쿠키값 기준으로 User 정보 획득하고, 해당 값으로 회원등록
            tokenMap = jwtTokenService.tokenParser(token);
            User savedUser = userService.saveUser(tokenMap);

            // 3. token 재할당
            tokenMap.put("id", savedUser.getId());
            updatedTokenMap = jwtTokenService.createJwtTokenMap(tokenMap);

            String access_token = updatedTokenMap.getString("access_token");
            String refresh_token = updatedTokenMap.getString("refresh_token");

            ResponseCookie accessCookie = ResponseCookie.from("access_token", access_token)
                    .httpOnly(true)
                    .secure(request.isSecure())
                    .path("/")
                    .maxAge(jwtTokenService.ACCESS_TOKEN_TIME)
                    .sameSite("Lax")
                    .build();

            ResponseCookie refreshCookie = ResponseCookie.from("refresh_token", refresh_token)
                    .httpOnly(true)
                    .secure(request.isSecure())
                    .path("/")
                    .maxAge(jwtTokenService.REFRESH_TOKEN_TIME)
                    .sameSite("Lax")
                    .build();

            String username = jwtTokenService.tokenParser(refresh_token).getString("userName");
            String encodedUsername = Base64.getEncoder().encodeToString(username.getBytes(StandardCharsets.UTF_8));

            ResponseCookie userNameCookie = ResponseCookie.from("_xjd", encodedUsername)
                .httpOnly(true)
                .secure(request.isSecure())
                .path("/")
                .maxAge(jwtTokenService.REFRESH_TOKEN_TIME)
                .sameSite("Lax")
                .build();

            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.SET_COOKIE, accessCookie.toString());
            headers.add(HttpHeaders.SET_COOKIE, refreshCookie.toString());
            headers.add(HttpHeaders.SET_COOKIE, userNameCookie.toString());

            updatedTokenMap.remove("access_token");
            updatedTokenMap.remove("refresh_token");

            updatedTokenMap.put("userName", savedUser.getUserName());

            return new ResponseEntity<>(updatedTokenMap, headers, HttpStatus.OK);

        } catch(ThrowCustomMapException e) {
            return new ResponseEntity<>(e.getResponse(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            e.printStackTrace();
            CustomMap errMap = new CustomMap();
            errMap.put("error_message",e.getMessage());
            errMap.put("status",500);
            return new ResponseEntity<>(errMap, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/me")
    public ResponseEntity<CustomMap> getCurrentUser(HttpServletRequest request) {
        CustomMap userMap = new CustomMap();

        try {
            // 사용자 ID를 요청 속성에서 가져옴
            int userId = (int) request.getAttribute("id");

            System.out.println("User ID from request: " + userId);
            if (userId <= 0) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); // Unauthorized
            }

            // 사용자 정보를 가져옴
            var user = userService.getUserByIdAndDeletedAtIsNull(userId);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Not Found
            }

            // 사용자 정보를 CustomMap에 추가
            userMap.put("userName", user.getUserName());
            userMap.put("id", user.getId());

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // Internal Server Error
        }

        return new ResponseEntity<>(userMap, HttpStatus.OK);
    }

}
