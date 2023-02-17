package com.wx.exception;

import com.wx.resp.Result;

/**
 * @Author:Mr.Wang
 * @Date: 2023/2/12  13:43
 * @Version 1.0
 */
public class CrmAuthException extends CrmException {
    private Result result;

    public CrmAuthException(String message) {
        super(message);
    }

    public CrmAuthException(String message, Throwable cause) {
        super(message, cause);
    }

    public CrmAuthException(Result errorResult) {
        super(errorResult.getMsg());
        this.result = errorResult;
    }

    public CrmAuthException(Throwable cause) {
        super(cause);
    }
}
