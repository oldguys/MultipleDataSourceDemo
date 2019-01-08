package com.oldguy.example.modules.test3.controllers;

import com.oldguy.example.modules.test3.dao.jpas.TestUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Date: 2019/1/7 0007
 * @Author: ren
 * @Description:
 */
@RestController
@RequestMapping("TestUser")
public class TestUserController {

    @Autowired
    private TestUserRepository testUserRepository;


    @GetMapping("list")
    public Object getList(){
        return testUserRepository.findAll();
    }
}
