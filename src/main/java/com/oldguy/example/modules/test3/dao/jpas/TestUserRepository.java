package com.oldguy.example.modules.test3.dao.jpas;

import com.oldguy.example.modules.test3.dao.entities.TestUser;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Date: 2019/1/7 0007
 * @Author: ren
 * @Description:
 */
public interface TestUserRepository extends JpaRepository<TestUser, Long> {
}
