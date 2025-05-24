package com.camel.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.camel.api.services.user.service.UserService;
import com.camel.common.CustomMap;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/user")
@CrossOrigin
public class UserController {
    

    @Autowired
    private UserService userService;


    @GetMapping("/me")
    public ResponseEntity<CustomMap> getCurrentUser(HttpServletRequest request) {
        CustomMap userMap = new CustomMap();

        try {
            // 사용자 ID를 요청 속성에서 가져옴
            int userId = (int) request.getAttribute("id");

            System.out.println("User ID from request: " + userId);
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
