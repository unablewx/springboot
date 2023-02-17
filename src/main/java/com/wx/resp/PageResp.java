package com.wx.resp;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author:Mr.Wang
 * @Date: 2023/2/2  16:09
 * @Version 1.0
 * 用于返回用户管理界面的前端信息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageResp<T> extends Result {
    private T data;
    private Integer count;
}
