package com.realtimechat.realtimechatapp.service;

import com.realtimechat.realtimechatapp.Model.Message;
import com.realtimechat.realtimechatapp.Model.User;
import com.realtimechat.realtimechatapp.exception.ChatException;
import com.realtimechat.realtimechatapp.exception.MessageException;
import com.realtimechat.realtimechatapp.exception.UserException;
import com.realtimechat.realtimechatapp.request.SendMessageReq;

import java.util.List;

public interface MessageService {

    public Message sendMessage(SendMessageReq  sendMessageReq) throws UserException, ChatException;

    public List<Message> getChatMessages(Integer chatId, User reqUser) throws ChatException, UserException;

    public Message getMessageById(Integer messageId) throws MessageException;

    public void deleteMessage(Integer messageId, User reqUser) throws MessageException;
}
