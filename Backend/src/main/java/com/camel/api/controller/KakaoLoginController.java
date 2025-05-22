package com.camel.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.camel.api.services.kakao.service.KakaoLoginService;
import com.camel.common.CustomMap;


@RestController
@RequestMapping("/api/v1/login")
@CrossOrigin
public class KakaoLoginController {

    @Autowired
    KakaoLoginService kakaoService;

    @PostMapping("/Users")
    public CustomMap GetUsers(@RequestBody CustomMap param) {
        try {
            System.out.println("param : "+param);
            return kakaoService.testAPI(param);
        } catch (Exception e){
            e.printStackTrace();
            CustomMap rtnMap = new CustomMap();
            rtnMap.put("status",500);
            rtnMap.put("error_message", e.getMessage());
            return rtnMap;
        }
    }
}