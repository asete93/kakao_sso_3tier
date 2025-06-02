package com.camel.api.controller;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

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




    /* ****************************************************************************************
     * Title            :   카카오 SSO 로그인 후, 쿠키에 JWT 토큰 저장
     * Method           :   POST
     * Function Name    :   KakaoOauthLogin                                                    
     * ----------------------------------------------------------------------------------------
     * Description      :   Kakao OAuth 인증을 통해 로그인 처리 프로세스,
     * 
     ******************************************************************************************/
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

            
            if(jwtTokenService.getIdFromToken(access_token) != null && jwtTokenService.getIdFromToken(access_token) > 0) {
                String username = jwtTokenService.tokenParser(refresh_token).getString("userName");
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

            

            

            rtnMap.remove("access_token");
            rtnMap.remove("refresh_token");

            return new ResponseEntity<>(rtnMap, headers, HttpStatus.OK);

        } catch(ThrowCustomMapException e) {
            return new ResponseEntity<>(e.getResponse(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>(rtnMap, HttpStatus.OK);
    }

}