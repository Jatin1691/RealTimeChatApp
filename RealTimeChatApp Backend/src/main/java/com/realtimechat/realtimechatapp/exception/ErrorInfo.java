package com.realtimechat.realtimechatapp.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorInfo {

    private String error;
    private String message;
    private LocalDateTime timestamp;
}
