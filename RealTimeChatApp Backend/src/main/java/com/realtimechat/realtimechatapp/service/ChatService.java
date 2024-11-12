package com.realtimechat.realtimechatapp.service;

import com.realtimechat.realtimechatapp.Model.Chat;
import com.realtimechat.realtimechatapp.Model.User;
import com.realtimechat.realtimechatapp.exception.ChatException;
import com.realtimechat.realtimechatapp.exception.UserException;
import com.realtimechat.realtimechatapp.request.GroupChatReq;

import java.util.List;

public interface ChatService {

    public Chat createChat(User reqUser, Integer userId2) throws UserException;

    public Chat findChatById(Integer chatId) throws ChatException;

    public List<Chat> findAllChatByUserId(Integer userId) throws ChatException, UserException;

    public Chat createGroup(GroupChatReq re, User userId ) throws UserException;

    public Chat addUserToGroup(Integer userId,Integer chatId,User reqUser) throws ChatException, UserException;

    public Chat renameGroup(Integer chatId,String newName,User reqUser) throws ChatException, UserException;

    public void deleteChat(Integer chatId,Integer userId) throws ChatException, UserException;

    public Chat removeUserFromGroup(Integer userId,Integer chatId,User reqUser) throws ChatException, UserException;


}
