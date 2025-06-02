package com.camel.api.services.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.camel.api.services.user.dao.User;
import com.camel.api.services.user.repository.UserRepository;
import com.camel.common.CustomMap;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public User getActiveUserByUserIdAndProvider(String userId, String provider) throws Exception {
        return userRepository.findByUserIdAndProviderAndDeletedAtIsNull(userId, provider);
    }

    public User saveUser(CustomMap user) throws Exception {
        User newUser = new User();
        newUser.setUserName(user.getString("userName"));
        newUser.setUserId(user.getString("userId"));
        newUser.setProvider(user.getString("provider"));
        newUser.setCreatedAt(new java.util.Date());

        return userRepository.save(newUser);
    }

    public User getUserByIdAndDeletedAtIsNull(int id) throws Exception {
        return userRepository.findByIdAndDeletedAtIsNull(id);
    }
}
