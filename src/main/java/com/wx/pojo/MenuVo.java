package com.wx.pojo;

import lombok.Data;

/**
 * @Author:Mr.Wang
 * @Date: 2023/2/9  21:01
 * @Version 1.0
 */
@Data
public class MenuVo {
    private Long id;
    private String name;

    public MenuVo(Menu menu) {
        this.id = menu.getId();
        this.name = menu.getMenuName();
    }
}
