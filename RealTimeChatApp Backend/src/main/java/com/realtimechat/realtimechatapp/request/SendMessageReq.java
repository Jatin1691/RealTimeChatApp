package com.realtimechat.realtimechatapp.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SendMessageReq {

    private Integer userId;
    private Integer chatId;
    private String content;


}
