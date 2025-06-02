package com.camel.api.services.token.service;

import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.camel.api.services.user.dao.User;
import com.camel.api.services.user.service.UserService;
import com.camel.common.CommonUtils;
import com.camel.common.CustomMap;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;

@Service
public class JwtTokenService {

    @Autowired
    private UserService userService;
    
    @Value("${jwt.token.secret}")
    private String JWT_TOKEN_SECRET;

    private String JWT_ISSUER = "CAMEL_API";

    private static Integer ONE_SEC = 1;
    private static Integer ONE_MINUTE = ONE_SEC * 60;
    private static Integer ONE_HOUR = ONE_MINUTE * 60;

    // access_token 유효시간 (30분)
    public static Integer ACCESS_TOKEN_TIME = ONE_MINUTE * 30 ;

    // refresh_token 유효시간 (1 day)
    public static Integer REFRESH_TOKEN_TIME = ONE_HOUR * 24;


    /* ****************************************************************************************
     * Title            :   JWT Token Map 생성
     * Scope            :   public
     * Function Name    :   createJwtTokenMap                                                    
     * ----------------------------------------------------------------------------------------
     * Description      :   access_token, refresh_token 이 담긴 CustomMap 생성
     * 
     ******************************************************************************************/
    public CustomMap createJwtTokenMap(CustomMap param) throws Exception {
        CustomMap tokenMap = new CustomMap();
        CustomMap claim = new CustomMap();

        // 기존 회원이 아닌경우
        if( !CommonUtils.checkParam(param, "id") ) {
            claim.put("userId", param.getString("userId"));
            claim.put("provider", param.getString("provider"));
            claim.put("userName", param.getString("userName"));

        // 기존 회원 정보가 있는 경우, 사용자 정보 가져오기
        } else {
            User user = getUserInfo(param.getInt("id"));

            claim.put("userId", user.getUserId());
            claim.put("id", user.getId());
            claim.put("provider", user.getProvider());
            claim.put("userName", user.getUserName());
        }

        tokenMap.put("access_token",createAccessToken(claim));
        tokenMap.put("refresh_token",createRefreshToken(claim));

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

        String userName = "";
        if( claim.containsKey("userName") ) {
            userName = claim.getString("userName");
        }

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        Date exp = new Date(nowMillis + ACCESS_TOKEN_TIME);

        return Jwts.builder()
                .setSubject(userName)
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
    private String createRefreshToken(CustomMap claim) throws Exception {
        SecretKey key = Keys.hmacShaKeyFor(JWT_TOKEN_SECRET.getBytes(StandardCharsets.UTF_8));

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        Date exp = new Date(nowMillis + REFRESH_TOKEN_TIME);

        claim.put("type","refresh");

        return Jwts.builder()
                .setIssuer(JWT_ISSUER)
                .setIssuedAt(now)
                .setExpiration(exp)
                .setClaims(claim)
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
    public Boolean verifyToken(String token) {
        try {
            Jwts.parser().setSigningKey(JWT_TOKEN_SECRET.getBytes(StandardCharsets.UTF_8)).parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
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
        CustomMap map = new CustomMap();
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(JWT_TOKEN_SECRET.getBytes("UTF-8"))
                    .parseClaimsJws(token)
                    .getBody();

            for (Map.Entry<String, Object> entry : claims.entrySet()) {
                map.put(entry.getKey(), entry.getValue());
            }
        } catch (SignatureException e) {
            throw new Exception("Invalid JWT signature", e);
        } catch (Exception e) {
            throw new Exception("JWT 파싱 실패", e);
        }

        return map;
    }




    /* ****************************************************************************************
     * Title            :   토큰에서 ID 추출
     * Scope            :   public
     * Function Name    :   getIdFromToken                                                    
     * ----------------------------------------------------------------------------------------
     * Description      :   토큰에서 사용자 ID 추출
     * 
     ******************************************************************************************/
    public Integer getIdFromToken(String token) throws Exception {
        try {
            return Jwts.parser()
                    .setSigningKey(JWT_TOKEN_SECRET.getBytes(StandardCharsets.UTF_8))
                    .parseClaimsJws(token)
                    .getBody()
                    .get("id", Integer.class);
        } catch (JwtException e) {
            throw new Exception("Invalid token");
        }
    }


    /* ****************************************************************************************
     * Title            :   사용자 ID 기준으로 Access Token 생성
     * Scope            :   public
     * Function Name    :   createAccessTokenById                                                    
     * ----------------------------------------------------------------------------------------
     * Description      :   사용자 ID 기준으로 Access Token 생성
     * 
     ******************************************************************************************/
    public String createAccessTokenByRefreshToken(String refreshToken) throws Exception {

        int id = getIdFromToken(refreshToken);
        if(id == 0) {
            throw new Exception("Invalid token");
        }

        CustomMap claim = new CustomMap();
        claim.put("id", id);

        User user = getUserInfo(id);
        if(user != null) {
            claim.put("userId", user.getUserId());
            claim.put("provider", user.getProvider());
            claim.put("userName", user.getUserName());
            return createAccessToken(claim);
        } else {
            throw new Exception("User not found");
        }
    }



    /* ****************************************************************************************
     * Title            :   사용자 id값 기준으로 사용자 정보 가져오기
     * Scope            :   private
     * Function Name    :   getUserInfo                                                    
     * ----------------------------------------------------------------------------------------
     * Description      :   사용자 id값 기준으로 사용자 정보 가져오기
     * 
     ******************************************************************************************/
    private User getUserInfo(int id) throws Exception {
        return userService.getUserByIdAndDeletedAtIsNull(id);
    }
}
