package com.camel.api.user.controller;

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

import com.camel.api.token.service.JwtTokenService;
import com.camel.api.user.model.User;
import com.camel.api.user.service.UserService;
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

    /* ****************************************************************************************
     * Title            :   로그아웃 API
     * Scope            :   public
     * Method           :   POST
     * Function Name    :   logout
     * ----------------------------------------------------------------------------------------
     * Description      :   모든 쿠키를 만료시킨다.
     * 
     ******************************************************************************************/
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




    /* ****************************************************************************************
     * Title            :   회원가입 API
     * Scope            :   public
     * Method           :   POST
     * Function Name    :   signup
     * ----------------------------------------------------------------------------------------
     * Description      :   신규 회원 가입을 한다.
     * 
     ******************************************************************************************/
    @PostMapping("/signup")
    public ResponseEntity<CustomMap> signup(HttpServletRequest request) {

        // 새로 갱신된 Token이 담긴 객체
        CustomMap updatedTokenMap = null;

        try {

            // 이미 발행된 Token
            CustomMap tokenMap = null;
            String token = "";

            // 1. 쿠키에서 access_token 추출
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

            // 3. 이전의 Token은 id가 없는 Token이었으므로, 다시 id를 담기위해 새로 Token을 생성한다.
            tokenMap.put("id", savedUser.getId());
            updatedTokenMap = jwtTokenService.createJwtTokenMap(tokenMap);

            // 4. 새로 생성된 Token을 쿠키로 생성하여 반환
            HttpHeaders headers = jwtTokenService.getCookieHeader(updatedTokenMap, request);

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


    /* ****************************************************************************************
     * Title            :   회원정보 조회 API
     * Scope            :   public
     * Method           :   GET
     * Function Name    :   getCurrentUser
     * ----------------------------------------------------------------------------------------
     * Description      :   회원가입된 사용자의 정보를 가져온다.
     * 
     ******************************************************************************************/
    @GetMapping("/me")
    public ResponseEntity<CustomMap> getCurrentUser(HttpServletRequest request) {
        CustomMap userMap = new CustomMap();

        try {
            // JwtAuthFilter 에서 API 요청시 JWT 토큰을 검증하는데, 이 때, id가 존재한다면 request값에 id를 셋팅하고있다.
            int userId = (int) request.getAttribute("id");

            // ID가 존재하지 않는 경우.
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
