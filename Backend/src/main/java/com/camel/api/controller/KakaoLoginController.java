package com.camel.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.camel.api.services.kakao.service.KakaoLoginService;
import com.camel.api.services.token.service.JwtTokenService;
import com.camel.common.CustomMap;
import com.camel.common.cusotmException.ThrowCustomMapException;

import jakarta.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/api/v1/login")
@CrossOrigin
public class KakaoLoginController {

    @Autowired
    KakaoLoginService kakaoService;

    @Autowired
    JwtTokenService jwtTokenService;

    @PostMapping("/kakao")
    public ResponseEntity<CustomMap> KakaoOauthLogin(@RequestBody CustomMap param, HttpServletRequest request) {
        CustomMap rtnMap = new CustomMap();
        
        try {
            rtnMap = kakaoService.kakaoOauthLogin(param.getString("code"));

            String access_token = rtnMap.getString("access_token");
            String refresh_token = rtnMap.getString("refresh_token");

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

            HttpHeaders headers = new HttpHeaders();
                    headers.add(HttpHeaders.SET_COOKIE, accessCookie.toString());
                    headers.add(HttpHeaders.SET_COOKIE, refreshCookie.toString());

            // 4️⃣ Redirect 설정
            // headers.add(HttpHeaders.LOCATION, "http://localhost:3002"); // 리디렉션할 URL
            System.out.println("headers : "+headers);
            return new ResponseEntity<>(null, headers, HttpStatus.OK);

        } catch(ThrowCustomMapException e) {
            return new ResponseEntity<>(e.getResponse(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>(rtnMap, HttpStatus.OK);
    }
}