package com.oldguy.example.modules.test4.dao.entities;


import com.oldguy.example.modules.common.dao.entities.BaseEntity;

import javax.persistence.Entity;

/**
 * @Date: 2019/1/7 0007
 * @Author: ren
 * @Description:
 */
@Entity
public class Remark extends BaseEntity {

    private String message;

    private String value;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
