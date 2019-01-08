package com.oldguy.example.modules.test3.dao.entities;

import com.oldguy.example.modules.common.dao.entities.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @Date: 2019/1/7 0007
 * @Author: ren
 * @Description:
 */
@Entity
public class TestUser extends BaseEntity{

    private String username;

    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
