package com.realtimechat.realtimechatapp.service;

import com.realtimechat.realtimechatapp.Model.User;
import com.realtimechat.realtimechatapp.config.TokenProvider;
import com.realtimechat.realtimechatapp.exception.UserException;
import com.realtimechat.realtimechatapp.repository.UserRepository;
import com.realtimechat.realtimechatapp.request.UpdateUserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenProvider tokenProvider;



    @Override
    public User findUserById(Integer userId) throws UserException {

        Optional<User> user = userRepository.findById(userId);
        if(user.isPresent())
            return user.get();

        throw new UserException("User not found with id"+userId);


    }

    @Override
    public User findUserProfile(String jwt) throws UserException {
        String email=tokenProvider.getEmailFromToken(jwt);

        if(email==null){
            throw new BadCredentialsException("received Invalid Token");

        }

        User user=userRepository.findByEmail(email);
        if(user==null){
            throw new UserException("User not found with email "+email);
        }
        return user;
    }

    @Override
    public User updateUser(Integer userId, UpdateUserRequest req) throws UserException {
          User user=findUserById(userId);
          if(req.getFullName()!=null){
              user.setFullName(req.getFullName());
          }

          if(req.getProfile_picture()!=null){
              user.setProfile_picture(req.getProfile_picture());
          }
        return userRepository.save(user);
    }

    @Override
    public List<User> searchUser(String query) {
        List<User> users=userRepository.search(query);
        return users;
    }
}
