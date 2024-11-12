package com.realtimechat.realtimechatapp.controller;

import com.realtimechat.realtimechatapp.Model.Chat;
import com.realtimechat.realtimechatapp.Model.User;
import com.realtimechat.realtimechatapp.exception.ChatException;
import com.realtimechat.realtimechatapp.exception.UserException;
import com.realtimechat.realtimechatapp.request.GroupChatReq;
import com.realtimechat.realtimechatapp.request.SingleChatRequest;
import com.realtimechat.realtimechatapp.response.ApiResponse;
import com.realtimechat.realtimechatapp.service.ChatService;
import com.realtimechat.realtimechatapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chat")
public class ChatController {
    @Autowired
    private ChatService chatService;
    @Autowired
    private UserService userService;

    @PostMapping("/single")
    public ResponseEntity<Chat> createChatHandler(@RequestBody SingleChatRequest singleChatRequest, @RequestHeader("Authorization") String jwt) throws UserException {

        User reqUser=userService.findUserProfile(jwt);

        Chat chat=chatService.createChat(reqUser,singleChatRequest.getUserId());
        return new ResponseEntity<>(chat, HttpStatus.OK);
    }

    @PostMapping("/group")
    public ResponseEntity<Chat> groupChatHandler(@RequestBody GroupChatReq groupChatReq, @RequestHeader("Authorization") String jwt) throws UserException {

        User reqUser=userService.findUserProfile(jwt);

       Chat chat=chatService.createGroup(groupChatReq,reqUser);
        return new ResponseEntity<>(chat, HttpStatus.OK);
    }

    @GetMapping("/{chatId}")
    public ResponseEntity<Chat> findChatByIdHandler(@PathVariable Integer chatId) throws UserException, ChatException {

       Chat chat=chatService.findChatById(chatId);
        return new ResponseEntity<>(chat, HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<List<Chat>> findAllChatByUserIdHandler(@RequestHeader("Authorization") String jwt) throws UserException, ChatException {

        User reqUser=userService.findUserProfile(jwt);
        List<Chat> chats=chatService.findAllChatByUserId(reqUser.getId());
        return new ResponseEntity<>(chats, HttpStatus.OK);
    }

    @PostMapping("/{chatId}/add/{userId}")
    public ResponseEntity<Chat> addUserToGroupHandler(@PathVariable Integer chatId, @PathVariable Integer userId,@RequestHeader("Authorization") String jwt) throws UserException, ChatException {
        User reqUser=userService.findUserProfile(jwt);

        Chat chat=chatService.addUserToGroup(userId,chatId,reqUser);
        return new ResponseEntity<>(chat, HttpStatus.OK);
    }

    @PutMapping("/{chatId}/add/{userId}")
    public ResponseEntity<Chat> removeUserFromGroupHandler(@PathVariable Integer chatId, @PathVariable Integer userId,@RequestHeader("Authorization") String jwt) throws UserException, ChatException {
        User reqUser=userService.findUserProfile(jwt);

        Chat chat=chatService.removeUserFromGroup(userId,chatId,reqUser);
        return new ResponseEntity<>(chat, HttpStatus.OK);
    }

    @DeleteMapping("delete/{chatId}")
    public ResponseEntity<ApiResponse> deleteChatHandler(@PathVariable Integer chatId,  @RequestHeader("Authorization") String jwt) throws UserException, ChatException {
        User reqUser=userService.findUserProfile(jwt);

        chatService.deleteChat(chatId,reqUser.getId());
        ApiResponse apiResponse=new ApiResponse("Chat is deleted Successfully",true);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }




}
