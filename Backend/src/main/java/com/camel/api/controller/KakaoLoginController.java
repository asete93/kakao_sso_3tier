package com.camel.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.camel.api.services.kakao.service.KakaoLoginService;
import com.camel.common.CustomMap;
import com.camel.common.cusotmException.ThrowCustomMapException;


@RestController
@RequestMapping("/api/v1/login")
@CrossOrigin
public class KakaoLoginController {

    @Autowired
    KakaoLoginService kakaoService;

    @GetMapping("/")
    public CustomMap KakaoOauthLogin(@RequestParam String code){
        CustomMap rtnMap = new CustomMap();

        try {
            rtnMap = kakaoService.kakaoOauthLogin(code);
        } catch(ThrowCustomMapException e) {
            return e.getResponse();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return rtnMap;
    }
}