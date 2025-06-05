package com.camel.api.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.camel.api.user.model.User;
import com.camel.api.user.repository.UserRepository;
import com.camel.common.CustomMap;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    /*
     * ****************************************************************************************
     * Title : 사용자 조회 (userId, provider)
     * Scope : public
     * Function Name : getActiveUserByUserIdAndProvider
     * ----------------------------------------------------------------------------------------
     * Description : userId와 provider를 가지고 사용자 조회
     * 
     ******************************************************************************************/
    public User getActiveUserByUserIdAndProvider(String userId, String provider) throws Exception {
        return userRepository.findByUserIdAndProviderAndDeletedAtIsNull(userId, provider);
    }


    /*
     * ****************************************************************************************
     * Title : 사용자 등록
     * Scope : public
     * Function Name : saveUser
     * ----------------------------------------------------------------------------------------
     * Description : 사용자 등록
     * 
     ******************************************************************************************/
    public User saveUser(CustomMap user) throws Exception {
        User newUser = new User();
        newUser.setUserName(user.getString("userName"));
        newUser.setUserId(user.getString("userId"));
        newUser.setProvider(user.getString("provider"));
        newUser.setCreatedAt(new java.util.Date());

        return userRepository.save(newUser);
    }

    /*
     * ****************************************************************************************
     * Title : 사용자 조회(id)
     * Scope : public
     * Function Name : getUserByIdAndDeletedAtIsNull
     * ----------------------------------------------------------------------------------------
     * Description : Id를 가지고 사용자 조회
     * 
     ******************************************************************************************/
    public User getUserByIdAndDeletedAtIsNull(int id) throws Exception {
        return userRepository.findByIdAndDeletedAtIsNull(id);
    }
}
