package com.realtimechat.realtimechatapp.controller;

import com.realtimechat.realtimechatapp.Model.Message;
import com.realtimechat.realtimechatapp.Model.User;
import com.realtimechat.realtimechatapp.exception.ChatException;
import com.realtimechat.realtimechatapp.exception.MessageException;
import com.realtimechat.realtimechatapp.exception.UserException;
import com.realtimechat.realtimechatapp.request.SendMessageReq;
import com.realtimechat.realtimechatapp.response.ApiResponse;
import com.realtimechat.realtimechatapp.service.ChatService;
import com.realtimechat.realtimechatapp.service.MessageService;
import com.realtimechat.realtimechatapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    @Autowired
    private MessageService messageService;
    @Autowired
    private UserService userService;
    @Autowired
    private ChatService chatService;

    @PostMapping("/create")
    public ResponseEntity<Message> sendMessageHandler(@RequestBody SendMessageReq sendMessageReq, @RequestHeader("Authorization") String jwt) throws ChatException, UserException {

        User user=userService.findUserProfile(jwt);

        sendMessageReq.setUserId(user.getId());

        Message message=messageService.sendMessage(sendMessageReq);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @GetMapping("/chat/{chatId}")
    public ResponseEntity<List<Message>> getChatMessageHandler(@PathVariable Integer chatId, @RequestHeader("Authorization") String jwt) throws ChatException, UserException {

        User user=userService.findUserProfile(jwt);

        List<Message> messages=messageService.getChatMessages(chatId,user);
        return new ResponseEntity<>(messages, HttpStatus.OK);
    }

    @GetMapping("/{messageId}")
    public ResponseEntity<ApiResponse> deleteMessageHandler(@PathVariable Integer messageId, @RequestHeader("Authorization") String jwt) throws ChatException, UserException, MessageException {

        User user=userService.findUserProfile(jwt);

        messageService.deleteMessage(messageId,user);
        ApiResponse apiResponse=new ApiResponse("message deleted successfully",true);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }


}
