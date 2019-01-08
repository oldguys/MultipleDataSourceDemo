package com.oldguy.example.modules.test4.controllers;

import com.oldguy.example.modules.test4.dao.jpas.RemarkRepository;
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
@RequestMapping("Test4")
public class Test4Controller {


    @Autowired
    private RemarkRepository remarkRepository;

    @GetMapping("message")
    public Object getMessage(String message){
        return remarkRepository.findByMessage(message);
    }

    @GetMapping("list")
    public Object getList(){
        return remarkRepository.findAll();
    }
}
