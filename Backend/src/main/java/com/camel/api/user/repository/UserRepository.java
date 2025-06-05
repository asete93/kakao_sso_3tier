package com.camel.api.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.camel.api.user.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    
    public User findByUserIdAndProviderAndDeletedAtIsNull(String userId,String provider);
    
    public User findByIdAndDeletedAtIsNull(int id);
}
