package com.realtimechat.realtimechatapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalException extends Exception {

    @ExceptionHandler(UserException.class)
    public ResponseEntity<ErrorInfo> UserExceptionHandler(UserException e, WebRequest req){

        ErrorInfo errorInfo = new ErrorInfo(e.getMessage(), req.getDescription(false), LocalDateTime.now() );
        return  new ResponseEntity<ErrorInfo>(errorInfo, HttpStatus.BAD_REQUEST);


    }


    @ExceptionHandler(MessageException.class)
    public ResponseEntity<ErrorInfo> MessageExceptionHandler(MessageException e, WebRequest req){
        ErrorInfo errorInfo=new ErrorInfo(e.getMessage(), req.getDescription(false), LocalDateTime.now() );
        return new ResponseEntity<ErrorInfo>(errorInfo, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ChatException.class)
    public ResponseEntity<ErrorInfo> ChatExceptionHandler(MessageException e, WebRequest req){
        ErrorInfo errorInfo=new ErrorInfo(e.getMessage(), req.getDescription(false), LocalDateTime.now() );
        return new ResponseEntity<ErrorInfo>(errorInfo, HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<ErrorInfo> MethodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e, WebRequest req){
        String error=e.getBindingResult().getFieldError().getDefaultMessage();

        ErrorInfo errorInfo=new ErrorInfo("Validation Error", error, LocalDateTime.now() );
        return new ResponseEntity<>(errorInfo, HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<ErrorInfo> handleNoHandlerException(NoHandlerFoundException e, WebRequest req){
        ErrorInfo errorInfo=new ErrorInfo("Endpoint Not Found", req.getDescription(false), LocalDateTime.now() );
        return new ResponseEntity<>(errorInfo, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorInfo> OtherExceptionHandler(Exception e, WebRequest req){

        ErrorInfo errorInfo = new ErrorInfo(e.getMessage(), req.getDescription(false), LocalDateTime.now() );
        return  new ResponseEntity<ErrorInfo>(errorInfo, HttpStatus.BAD_REQUEST);


    }


}
