package com.realtimechat.realtimechatapp.service;

import com.realtimechat.realtimechatapp.Model.Chat;
import com.realtimechat.realtimechatapp.Model.User;
import com.realtimechat.realtimechatapp.exception.ChatException;
import com.realtimechat.realtimechatapp.exception.UserException;
import com.realtimechat.realtimechatapp.repository.ChatRepository;
import com.realtimechat.realtimechatapp.request.GroupChatReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ChatServiceImpl implements ChatService {

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private UserService userService;

    @Override
    public Chat createChat(User reqUser, Integer userId2) throws UserException {
        User user = userService.findUserById(userId2);

        Chat isChatExist=chatRepository.findSingleChatByUserId(user,reqUser);
        if(isChatExist!=null){
            return isChatExist;
        }

        Chat newChat=new Chat();
        newChat.setCreatedBy(reqUser);
        newChat.getUsers().add(user);
        newChat.getUsers().add(reqUser);
        return newChat;
    }

    @Override
    public Chat findChatById(Integer chatId) throws ChatException {
        Optional<Chat> chat=chatRepository.findById(chatId);
        if(chat.isPresent()){
            return chat.get();
        }
        throw new ChatException("Chat not found");
    }

    @Override
    public List<Chat> findAllChatByUserId(Integer userId) throws ChatException, UserException {
        User user=userService.findUserById(userId);
        List<Chat> chats=chatRepository.findChatByUserId(user.getId());
        return chats;
    }

    @Override
    public Chat createGroup(GroupChatReq req, User user) throws UserException {
        Chat groupChat=new Chat();
        groupChat.setGroup(true);
        groupChat.setChatName(req.getChatName());
        groupChat.setChatImage(req.getChatImage());
        groupChat.getAdmins().add(user);

        groupChat.setCreatedBy(user);

        for(Integer userId:req.getUserId()){
            User user1=userService.findUserById(userId);
            groupChat.getUsers().add(user1);
        }
        return groupChat;
    }

    @Override
    public Chat addUserToGroup(Integer userId, Integer chatId,User reqUser) throws ChatException, UserException {
        Optional<Chat> opt=chatRepository.findById(chatId);
        User user=userService.findUserById(userId);

        if(opt.isPresent()){
            Chat chat=opt.get();
            if(chat.getAdmins().contains(reqUser)){
                chat.getUsers().add(user);
                return chatRepository.save(chat);
            } else {
                throw new UserException("You are not Admin");
            }
        }

        throw new ChatException("Chat not found with Id"+chatId);
    }

    @Override
    public void deleteChat(Integer chatId, Integer userId) throws ChatException, UserException {

      Optional<Chat> opt=chatRepository.findById(chatId);

      if(opt.isPresent()){
          Chat chat=opt.get();
          chatRepository.deleteById(chat.getId());
      }
    }

    @Override
    public Chat renameGroup(Integer chatId, String newName, User reqUser) throws ChatException, UserException {
        Optional<Chat> opt=chatRepository.findById(chatId);

        if(opt.isPresent()){
            Chat chat=opt.get();
            if(chat.getAdmins().contains(reqUser)){
                chat.setChatName(newName);
                return chatRepository.save(chat);
            }
            else{
                throw new UserException("You are not Member of this chat");
            }
        }

        throw new ChatException("Chat not found with Id"+chatId);
    }

    @Override
    public Chat removeUserFromGroup(Integer userId, Integer chatId, User reqUser) throws ChatException, UserException {
        Optional<Chat> opt=chatRepository.findById(chatId);
        User user=userService.findUserById(userId);

        if(opt.isPresent()){
            Chat chat=opt.get();
            if(chat.getAdmins().contains(reqUser)){
                chat.getUsers().remove(user);
                return chatRepository.save(chat);
            } else if (chat.getUsers().contains(reqUser)) {
                if(chat.getUsers().contains(reqUser)){
                    chat.getUsers().remove(user);
                }

            } else{
                throw new UserException("You can't remove another user");
            }
        }

        throw new ChatException("Chat not found with Id"+chatId);
    }
}
