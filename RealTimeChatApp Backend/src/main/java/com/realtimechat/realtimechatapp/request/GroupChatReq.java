package com.realtimechat.realtimechatapp.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupChatReq {

    private List<Integer> userId;
    private String chatName;
    private String chatImage;
}
