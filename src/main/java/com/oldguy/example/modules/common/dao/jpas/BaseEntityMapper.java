package com.oldguy.example.modules.common.dao.jpas;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author ren
 * @date 2018/12/20
 */
public interface BaseEntityMapper<T> extends BaseMapper<T,Long>{

    /**
     *  获取 List<T> 列表
     *  status :
     *      1 - 有效
     *      0 - 无效
     *      null -> 所有
     * @param status
     * @return
     */
    List<T> findAllByStatus(@Param("status") Integer status);

    /**
     * 	修改 T 状态
     * @param id
     * @param status
     * @return
     */
    int updateStatus(@Param("id") Long id, @Param("status") Integer status);
}
