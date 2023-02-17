package com.wx.handler;

import com.wx.exception.CrmAuthException;
import com.wx.resp.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * @Author:Mr.Wang
 * @Date: 2023/2/12  14:10
 * @Version 1.0
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    //出现鉴权异常
    @ExceptionHandler(CrmAuthException.class)
//    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<Result> authException(CrmAuthException e) {
        printErrorInfo(e);
        HttpStatus unauthorized = HttpStatus.UNAUTHORIZED;
        Result result = new Result(false, unauthorized.value(), unauthorized.getReasonPhrase());
        return ResponseEntity.status(unauthorized).body(result);
    }

    public void printErrorInfo(Exception e) {
        log.error("异常:{}", e.getMessage());
    }
}
