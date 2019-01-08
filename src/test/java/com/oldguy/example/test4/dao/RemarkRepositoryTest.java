package com.oldguy.example.test4.dao;

import com.oldguy.example.modules.test4.dao.entities.Remark;
import com.oldguy.example.modules.test4.dao.jpas.RemarkRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * @Date: 2019/1/7 0007
 * @Author: ren
 * @Description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RemarkRepositoryTest {

    @Autowired
    private RemarkRepository remarkRepository;

    @Test
    public void testSave() {

        List<Remark> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add(getInstance("msg-" + i, "value-" + (20 - i)));
        }

        remarkRepository.save(list);
    }

    public static Remark getInstance(String message, String value) {
        Remark instance = new Remark();

        instance.setMessage(message);
        instance.setValue(value);

        return instance;
    }
}
