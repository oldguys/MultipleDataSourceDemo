package com.oldguy.example.test3.dao;

import com.oldguy.example.modules.test3.dao.entities.TestUser;
import com.oldguy.example.modules.test3.dao.jpas.TestUserRepository;
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
public class TestUserRepositoryTest {

    @Autowired
    private TestUserRepository testUserRepository;

    @Test
    public void testSave() {

        List<TestUser> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(getInstance("test-" + i, "psw-" + (i + 1)));
        }

        testUserRepository.save(list);
    }

    public static TestUser getInstance(String username, String password) {
        TestUser instance = new TestUser();
        instance.setUsername(username);
        instance.setPassword(password);
        return instance;
    }
}
