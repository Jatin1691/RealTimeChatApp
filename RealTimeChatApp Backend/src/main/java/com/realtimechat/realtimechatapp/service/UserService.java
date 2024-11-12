package com.realtimechat.realtimechatapp.service;

import com.realtimechat.realtimechatapp.Model.User;
import com.realtimechat.realtimechatapp.exception.UserException;
import com.realtimechat.realtimechatapp.request.UpdateUserRequest;

import java.util.List;

public interface UserService {

    public User findUserById(Integer userId) throws UserException;

    public User findUserProfile(String jwt) throws UserException;

    public User updateUser(Integer userId, UpdateUserRequest req) throws UserException;

    public List<User> searchUser(String query);
}
