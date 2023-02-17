package com.wx.exception;

/**
 * @Author:Mr.Wang
 * @Date: 2023/2/12  13:42
 * @Version 1.0
 */
public class CrmException extends Exception{
    public CrmException() {
    }

    public CrmException(String message) {
        super(message);
    }

    public CrmException(String message, Throwable cause) {
        super(message, cause);
    }

    public CrmException(Throwable cause) {
        super(cause);
    }

    public CrmException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
