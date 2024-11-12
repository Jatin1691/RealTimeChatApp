package com.realtimechat.realtimechatapp.request;

import com.realtimechat.realtimechatapp.service.ChatService;
import com.realtimechat.realtimechatapp.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class SingleChatRequest {

    private Integer userId;






}
