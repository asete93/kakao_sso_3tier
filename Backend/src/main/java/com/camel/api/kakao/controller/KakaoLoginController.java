package com.camel.api.kakao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.camel.api.kakao.service.KakaoLoginService;
import com.camel.api.token.service.JwtTokenService;
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
     * Title            :   카카오 SSO 로그인 후, 쿠키에 JWT 토큰 저장 API
     * Scope            :   public
     * Method           :   POST
     * Function Name    :   KakaoOauthLogin                                                    
     * ----------------------------------------------------------------------------------------
     * Description      :   Kakao OAuth 인증을 통해 로그인 처리 프로세스,
     *                      회원가입되어있지 않은 사용자의 경우, needSignup 값을 true로 반환한다.
     * 
     ******************************************************************************************/
    @PostMapping("/kakao")
    public ResponseEntity<CustomMap> KakaoOauthLogin(@RequestBody CustomMap param, HttpServletRequest request) {
        CustomMap rtnMap = new CustomMap();
        
        try {
            // 1. kakao oauth 인증을 통해 유저정보를 식별하고, access_token, refresh_token을 가져온다.
            rtnMap = kakaoService.kakaoOauthLogin(param.getString("code"));

            // 2. Token Cookie Header 생성
            HttpHeaders headers = jwtTokenService.getCookieHeader(rtnMap, request);

            // Return Body에 token값을 포함하지 않도록하기 위함.
            rtnMap.remove("access_token");
            rtnMap.remove("refresh_token");

            return new ResponseEntity<>(rtnMap, headers, HttpStatus.OK);

        } catch(ThrowCustomMapException e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getResponse(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>(rtnMap, HttpStatus.OK);
    }

}