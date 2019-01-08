package com.oldguy.example.modules.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * @Date: 2019/1/8 0008
 * @Author: ren
 * @Description:
 */
@Data
public class HttpResult {

    private Integer code;

    private String message;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Object data;

    public HttpResult(Integer code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

}
