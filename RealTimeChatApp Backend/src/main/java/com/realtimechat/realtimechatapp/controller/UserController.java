package com.realtimechat.realtimechatapp.controller;

import com.realtimechat.realtimechatapp.Model.User;
import com.realtimechat.realtimechatapp.exception.UserException;
import com.realtimechat.realtimechatapp.request.UpdateUserRequest;
import com.realtimechat.realtimechatapp.response.ApiResponse;
import com.realtimechat.realtimechatapp.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {


    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/profile")
    public ResponseEntity<User> UserProfileHandler(@RequestHeader("Authorization") String token) throws UserException {
        User user=userService.findUserProfile(token);

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/{query}")
    public ResponseEntity<List<User>> searchProfileHandler(String query) throws UserException {
        List<User> users=userService.searchUser(query);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }


    @PutMapping("/update")
    public ResponseEntity<ApiResponse> updateProfileHandler(@RequestBody UpdateUserRequest req,@RequestHeader("Authorization") String token) throws UserException {
         User user=userService.findUserProfile(token);
         userService.updateUser(user.getId(),req);
         ApiResponse apiResponse=new ApiResponse("user updated successfully",true);
          return new ResponseEntity<>(apiResponse,HttpStatus.ACCEPTED);
    }


}
