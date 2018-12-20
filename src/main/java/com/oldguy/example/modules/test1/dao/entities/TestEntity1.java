package com.oldguy.example.modules.test1.dao.entities;

import com.oldguy.example.modules.common.annotation.Entity;
import com.oldguy.example.modules.common.dao.entities.BaseEntity;

import javax.persistence.Column;

/**
 * @author ren
 * @date 2018/12/20
 */
@Entity(pre = "test1_")
public class TestEntity1 extends BaseEntity {

    @Column(unique = true)
    private String test1Name;

    public String getTest1Name() {
        return test1Name;
    }

    public void setTest1Name(String test1Name) {
        this.test1Name = test1Name;
    }
}
