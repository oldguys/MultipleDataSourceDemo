package com.oldguy.example.common;

import javax.validation.constraints.NotNull;

/**
 * @Date: 2019/1/8 0008
 * @Author: ren
 * @Description:
 */
public class AbstractForm {


    @NotNull(message = "测试继承 id")
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
