package com.realtimechat.realtimechatapp.service;

import com.realtimechat.realtimechatapp.Model.Chat;
import com.realtimechat.realtimechatapp.Model.Message;
import com.realtimechat.realtimechatapp.Model.User;
import com.realtimechat.realtimechatapp.exception.ChatException;
import com.realtimechat.realtimechatapp.exception.MessageException;
import com.realtimechat.realtimechatapp.exception.UserException;
import com.realtimechat.realtimechatapp.repository.MessageRepository;
import com.realtimechat.realtimechatapp.request.SendMessageReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private UserService userService;
    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private ChatService chatService;

    @Override
    public Message sendMessage(SendMessageReq sendMessageReq) throws UserException, ChatException {
        User user=userService.findUserById(sendMessageReq.getUserId());
        Chat chat=chatService.findChatById(sendMessageReq.getChatId());

        Message message=new Message();
        message.setUser(user);
        message.setChat(chat);
        message.setContent(sendMessageReq.getContent());
        message.setTimestamp(LocalDateTime.now());

        messageRepository.save(message);
        return message;
    }

    @Override
    public List<Message> getChatMessages(Integer chatId,User reqUser) throws ChatException, UserException {
        Chat chat=chatService.findChatById(chatId);

        if(!chat.getUsers().contains(reqUser)){
            throw new UserException("You are not in this Chat");
        }

        List<Message> messages=messageRepository.findByChatId(chat.getId());
        return messages;
    }

    @Override
    public Message getMessageById(Integer messageId) throws MessageException {
        Optional<Message> message=messageRepository.findById(messageId);

        if(message.isPresent()){
            return message.get();
        }
        throw new MessageException("Message not found with Id"+message);
    }

    @Override
    public void deleteMessage(Integer messageId,User reqUser) throws MessageException {
       Message message=getMessageById(messageId);
       if(message.getUser().getId().equals(reqUser)){
           messageRepository.deleteById(messageId);
       }

       throw new MessageException("You can't delete other User's message"+reqUser.getFullName());
    }
}
