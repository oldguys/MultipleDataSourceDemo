package com.oldguy.example.common;

import com.oldguy.example.modules.common.utils.FormValidateUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @Date: 2019/1/8 0008
 * @Author: ren
 * @Description:
 */
public class FormValidateTest {

    @Test
    public void testList(){

        List<FormEntity> list = new ArrayList<>();
        list.add(new FormEntity());
        FormValidateUtils.validateCollection(list);

    }

    @Test
    public void testNoCascade(){

        FormEntity entity = new FormEntity();

        FormEntity.FormItem item = new FormEntity.FormItem();
        entity.setItem1(item);

        List<FormEntity.FormItem> list = new ArrayList<>();
        list.add(item);
        entity.setItemList(list);

        FormValidateUtils.validate(entity);

    }

    @Test
    public void testCascade(){

        FormEntity entity = new FormEntity();

        FormEntity.FormItem item = new FormEntity.FormItem();
        entity.setItem1(item);

        List<FormEntity.FormItem> list = new ArrayList<>();
        list.add(item);
        entity.setItemList(list);

        FormValidateUtils.validate(entity,true);

    }
}
