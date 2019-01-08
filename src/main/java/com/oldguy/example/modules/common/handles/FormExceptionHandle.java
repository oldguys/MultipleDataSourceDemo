package com.oldguy.example.modules.common.handles;

import com.oldguy.example.modules.common.dto.HttpResult;
import com.oldguy.example.modules.common.exceptions.FormValidException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Date: 2019/1/8 0008
 * @Author: ren
 * @Description:
 */
@RestControllerAdvice
public class FormExceptionHandle {

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(FormValidException.class)
    public Object FormException(FormValidException e){
        return new HttpResult(HttpStatus.BAD_REQUEST.value(),e.getMessage(),null);
    }
}
