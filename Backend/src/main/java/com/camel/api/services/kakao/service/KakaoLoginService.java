package com.camel.api.services.kakao.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.camel.api.services.token.service.JwtTokenService;
import com.camel.api.services.user.dao.User;
import com.camel.api.services.user.service.UserService;
import com.camel.common.ApiClient;
import com.camel.common.CommonUtils;
import com.camel.common.CustomMap;
import com.camel.common.cusotmException.ThrowCustomMapException;

@Service
public class KakaoLoginService {
    private static final Logger logger = LoggerFactory.getLogger(KakaoLoginService.class);

    @Autowired
    ApiClient apiClient;

    @Autowired
    UserService userService;

    @Autowired
    JwtTokenService jwtTokenService;

    @Value("${kakao.oauth-url}")
    private String KAKAO_OAUTH_URL;

    @Value("${kakao.grant-type}")
    private String KAKAO_OAUTH_GRANT_TYPE;

    @Value("${kakao.client-id}")
    private String KAKAO_OAUTH_CLIENT_ID;

    @Value("${kakao.kakao-user-info-url}")
    private String GET_KAKAO_USER_INFO_URL;


    /* ****************************************************************************************
     * Title            :   카카오 SSO 로그인 처리
     * Scope            :   public
     * Function Name    :   kakaoOauthLogin                                                    
     * ----------------------------------------------------------------------------------------
     * Description      :   Kakao OAuth 인증을 통해 로그인 처리 프로세스,                        
     *                          1. DB에 계정 존재 확인 후, 없다면 회원 가입 유도.
     *                          2. DB에 계정 존재 확인 후, 존재한다면 로그인 완료 처리.
     * 
     ******************************************************************************************/
    public CustomMap kakaoOauthLogin(String code) throws Exception {
        CustomMap resultMap = new CustomMap();

        // Step 1. Get Kakao Access Token
        String kakaoAccessToken = getKakaoAccessToken(code);

        // Step 2. Get Kakao User Info
        CustomMap kakaoUserInfo = getKakaoUserInfo(kakaoAccessToken);

        String userId = kakaoUserInfo.getString("id");
        String userName = kakaoUserInfo.getString("nickname");

        // Step 3. Check User Exists
        User existUser = userService.getActiveUserByUserIdAndProvider(userId, "KAKAO");

        CustomMap tokenMap = new CustomMap();
        tokenMap.put("userId",userId);
        tokenMap.put("userName",userName);
        tokenMap.put("provider","KAKAO");

        // Step 4. 토큰 발급
        if(existUser != null){
            tokenMap.put("id",existUser.getId());
        }
        
        resultMap = jwtTokenService.createJwtTokenMap(tokenMap);

        // Step 5. User 정보 반환
        if(existUser != null){
            resultMap.put("userName",existUser.getUserName());
        } else {
            resultMap.put("needSignup",true);
            resultMap.put("userName",userName);
        }

        return resultMap;
    }







    /* ****************************************************************************************
     * Title            :   카카오 정보 가져오기
     * Scope            :   private
     * Function Name    :   getKakaoUserInfo
     * ----------------------------------------------------------------------------------------
     * Description      :   카카오 로그인 회원 정보 가져오기
     * 
     ******************************************************************************************/
    private CustomMap getKakaoUserInfo(String accessToken) throws Exception {
        CustomMap headers = new CustomMap();
        headers.put("Authorization",CommonUtils.makeBearerTokenFormat(accessToken));

        // User Info 조회
        CustomMap apiResult = apiClient.getClient(GET_KAKAO_USER_INFO_URL, headers);
        int status = apiResult.getInt("status");

        if(status==200){
            apiResult = new CustomMap(apiResult.getString("body"));
            CustomMap userInfo = new CustomMap();
            userInfo.put("id", apiResult.getLong("id")+"");
            userInfo.put("nickname", apiResult.getMap("properties").getString("nickname"));

            return userInfo;
        } else {
            throw new ThrowCustomMapException("카카오 계정 정보를 가져오는 과정에서 오류가 발생하였습니다.", new CustomMap(), 401);
        }
    }





    
    /* ****************************************************************************************
     * Title            :   카카오 Access Token 발급
     * Scope            :   private
     * Function Name    :   getKakaoAccessToken                                                    
     * ----------------------------------------------------------------------------------------
     * Description      :   Kakao OAuth 인증을 통해 AccessToken 가져오기
     * 
     ******************************************************************************************/
    private String getKakaoAccessToken(String code) throws Exception {
        // Header
        CustomMap oauthHeader = new CustomMap();
        oauthHeader.put("Content-Type","application/x-www-form-urlencoded");

        // Call Kakao Oauth API
        CustomMap apiResult = apiClient.postClient(makeKakaoOauthUrl(code), null, oauthHeader);
        
        int status = apiResult.getInt("status");

        if(status == 200){
            return new CustomMap(apiResult.getString("body")).getString("access_token");
        } else {
            throw new ThrowCustomMapException("카카오 접속 정보가 올바르지 않습니다.", new CustomMap(), 401);
        }
    }





    /* ****************************************************************************************
     * Title            :   카카오 인증 URL 생성
     * Scope            :   private
     * Function Name    :   makeKakaoOauthUrl                                                    
     * ----------------------------------------------------------------------------------------
     * Description      :   Kakao OAuth 인증 URL Format으로 변환
     * 
     ******************************************************************************************/
    private String makeKakaoOauthUrl(String code){
        return String.format("%s?%s&%s&code=%s", 
                                    KAKAO_OAUTH_URL
                                    ,KAKAO_OAUTH_GRANT_TYPE
                                    ,KAKAO_OAUTH_CLIENT_ID,code);
    }
}