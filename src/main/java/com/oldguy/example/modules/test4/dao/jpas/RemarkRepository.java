package com.oldguy.example.modules.test4.dao.jpas;

import com.oldguy.example.modules.test4.dao.entities.Remark;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Date: 2019/1/7 0007
 * @Author: ren
 * @Description:
 */
public interface RemarkRepository extends JpaRepository<Remark,Long>{

    /**
     *  获取列表
     * @param message
     * @return
     */
    List<Remark> findByMessage(String message);
}
