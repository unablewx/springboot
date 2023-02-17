package com.wx.resp;

import lombok.Data;

import java.util.List;

/**
 * @Author:Mr.Wang
 * @Date: 2023/2/10  15:06
 * @Version 1.0
 */
@Data
public class TreeResp<T> extends Result {
    private T data;
    private List list;
}
