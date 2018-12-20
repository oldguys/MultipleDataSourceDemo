package com.oldguy.example.modules.test2.dao.entities;

import com.oldguy.example.modules.common.annotation.Entity;
import com.oldguy.example.modules.common.dao.entities.BaseEntity;

import javax.persistence.Column;

/**
 * @author ren
 * @date 2018/12/20
 */
@Entity(pre = "test2_")
public class TestEntity2 extends BaseEntity {

    @Column(columnDefinition = "TEXT")
    private String test2Name;

    @Column(nullable = false)
    private String flag;

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getTest2Name() {
        return test2Name;
    }

    public void setTest2Name(String test2Name) {
        this.test2Name = test2Name;
    }
}
