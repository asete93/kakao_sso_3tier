package com.camel.api.services.token.service;

import java.nio.charset.StandardCharsets;
import java.sql.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.camel.common.CustomMap;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtTokenService {
    
    @Value("${jwt.token.secret}")
    private String JWT_TOKEN_SECRET;

    private String JWT_ISSUER = "CAMEL_API";

    private static Integer ONE_SEC = 1000;
    private static Integer ONE_MINUTE = ONE_SEC * 60;
    private static Integer ONE_HOUR = ONE_MINUTE * 60;

    // access_token 유효시간 (30분)
    public static Integer ACCESS_TOKEN_TIME_MILLIS = ONE_MINUTE * 30 ;

    // refresh_token 유효시간 (1 day)
    public static Integer REFRESH_TOKEN_TIME_MILLIS = ONE_HOUR * 24;



    /* ****************************************************************************************
     * Title            :   JWT Token Map 생성
     * Scope            :   public
     * Function Name    :   createJwtTokenMap                                                    
     * ----------------------------------------------------------------------------------------
     * Description      :   access_token, refresh_token 이 담긴 CustomMap 생성
     * 
     ******************************************************************************************/
    public CustomMap createJwtTokenMap(CustomMap claim) throws Exception {
        CustomMap tokenMap = new CustomMap();

        tokenMap.put("access_token",createAccessToken(claim));
        tokenMap.put("refresh_token",createRefreshToken());

        return tokenMap;
    }




    /* ****************************************************************************************
     * Title            :   Access_token 생성 (JWT)
     * Scope            :   private
     * Function Name    :   createAccessToken                                                    
     * ----------------------------------------------------------------------------------------
     * Description      :   access_token 생성
     * 
     ******************************************************************************************/
    private String createAccessToken(CustomMap claim) throws Exception {
        SecretKey key = Keys.hmacShaKeyFor(JWT_TOKEN_SECRET.getBytes(StandardCharsets.UTF_8));

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        Date exp = new Date(nowMillis + ACCESS_TOKEN_TIME_MILLIS);

        return Jwts.builder()
                .setSubject(claim.getString("userId"))
                .setIssuer(JWT_ISSUER)
                .setIssuedAt(now)
                .setExpiration(exp)
                .setClaims(claim)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }




    /* ****************************************************************************************
     * Title            :   Refresh_token 생성 (JWT)
     * Scope            :   private
     * Function Name    :   createRefreshToken                                                    
     * ----------------------------------------------------------------------------------------
     * Description      :   refresh_token 생성
     * 
     ******************************************************************************************/
    private String createRefreshToken() throws Exception {
        SecretKey key = Keys.hmacShaKeyFor(JWT_TOKEN_SECRET.getBytes(StandardCharsets.UTF_8));

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        Date exp = new Date(nowMillis + REFRESH_TOKEN_TIME_MILLIS);

        return Jwts.builder()
                .setIssuer(JWT_ISSUER)
                .setIssuedAt(now)
                .setExpiration(exp)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }





    /* ****************************************************************************************
     * Title            :   토큰 검증
     * Scope            :   public
     * Function Name    :   verifyToken                                                    
     * ----------------------------------------------------------------------------------------
     * Description      :   토큰 유효성 검증
     * 
     ******************************************************************************************/
    public Boolean verifyToken(String token) throws Exception {

        return true;
    }







    /* ****************************************************************************************
     * Title            :   토큰 Parser
     * Scope            :   public
     * Function Name    :   tokenParser                                                    
     * ----------------------------------------------------------------------------------------
     * Description      :   토큰 파서
     * 
     ******************************************************************************************/
    public CustomMap tokenParser(String token) throws Exception {

        return new CustomMap();
    }
}
