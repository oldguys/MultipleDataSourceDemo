package com.oldguy.example.common;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @Date: 2019/1/8 0008
 * @Author: ren
 * @Description:
 */
@Data
public class FormEntity extends AbstractForm{

    @NotBlank(message = "测试 message")
    private String message;

    @NotBlank(message = "测试 comment")
    private String comment;

    @NotEmpty(message = "测试 List")
    public List<FormItem> itemList;

    @NotNull(message = "item1 不为空")
    public FormItem item1;

    public FormItem item2;

    @Data
    public static class FormItem{

        @NotNull(message = "测试级联 code")
        private Integer code;

        @NotBlank(message = "测试级联 message")
        private String message;

    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setItemList(List<FormItem> itemList) {
        this.itemList = itemList;
    }

    public void setItem1(FormItem item1) {
        this.item1 = item1;
    }

    public void setItem2(FormItem item2) {
        this.item2 = item2;
    }
}
