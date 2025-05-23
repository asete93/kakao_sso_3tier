package com.camel.api.services.kakao.service;

import org.apache.http.Header;
import org.apache.http.client.CookieStore;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.camel.common.ApiClient;
import com.camel.common.CustomMap;

@Service
public class KakaoLoginService {
    private static final Logger logger = LoggerFactory.getLogger(KakaoLoginService.class);

    @Autowired
    ApiClient apiClient;

    public CustomMap testAPI(CustomMap param) throws Exception {
        CustomMap returnMap = new CustomMap();

        CookieStore cookieStore = new BasicCookieStore();
        CloseableHttpClient client = apiClient.createClient(cookieStore);

        // Step1. ID
        CustomMap result = apiClient.postClient(client, "https://pam.kicloud.kisti.re.kr/api/v3/flows/executor/default-authentication-flow/",param);

        if(result.getInt("status") == 302){
            Header[] headers = (Header[]) result.get("headers");
            String newUrl = "";
            for(Header header : headers){
                if(header.getName().equals("Location")){
                    newUrl = "https://pam.kicloud.kisti.re.kr" + header.getValue();
                    break;
                }
            }
            
            for(Cookie cookie: cookieStore.getCookies()){
                System.out.println(cookie.getName() + " = " + cookie.getValue() );
            }
            System.out.println("REDIRECTED 11 /" + newUrl);
            result = apiClient.getClient(client, newUrl);
        }

        returnMap.put("status" ,result.get("status"));
        returnMap.put("body",result.get("body"));

        for(Cookie cookie: cookieStore.getCookies()){
            System.out.println(cookie.getName() + " = " + cookie.getValue() );
        }

        // Step2. PWD
        result = apiClient.postClient(client, "https://pam.kicloud.kisti.re.kr/api/v3/flows/executor/default-authentication-flow/",param);

        if(result.getInt("status") == 302){
            Header[] headers = (Header[]) result.get("headers");
            String newUrl = "";
            for(Header header : headers){
                if(header.getName().equals("Location")){
                    newUrl = "https://pam.kicloud.kisti.re.kr" + header.getValue();
                    break;
                }
            }
            
            System.out.println("REDIRECTED 22/" + newUrl);
            result = apiClient.getClient(client, newUrl);
        }

        for(Cookie cookie: cookieStore.getCookies()){
            System.out.println(cookie.getName() + " = " + cookie.getValue() );
        }

        returnMap.put("status" ,result.get("status"));
        returnMap.put("body",result.get("body"));

        client.close();

        return returnMap;
    }
}