package com.oldguy.example.modules.controllers;

import com.oldguy.example.modules.test1.dao.jpas.TestEntity1Mapper;
import com.oldguy.example.modules.test2.dao.jpas.TestEntity2Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on 2018/12/20 0020.
 */
@RestController
public class TestController {


    @Autowired
    private TestEntity2Mapper testEntity2Mapper;
    @Autowired
    private TestEntity1Mapper testEntity1Mapper;

    @GetMapping("test1")
    public Object test1(){
        return testEntity1Mapper.findAllByStatus(null);
    }

    @GetMapping("test2")
    public Object test2(){
        return testEntity2Mapper.findAllByStatus(null);
    }
}
