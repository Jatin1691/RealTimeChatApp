package com.realtimechat.realtimechatapp.repository;

import com.realtimechat.realtimechatapp.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Integer> {

    public User findByEmail(String email);

    @Query("select u from User u where u.fullName LIKE %:query% or u.email LIKE %:query%")
    public List<User> search(@Param("query") String query);
}
