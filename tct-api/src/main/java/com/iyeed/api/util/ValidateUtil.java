package com.iyeed.api.util;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;

/**
 * @author ZhangJunhua
 * @version 0.1 2017-06-06 20:38
 */
public interface ValidateUtil {

    static String getErrorMsg(BindingResult... results){
        StringBuilder builder = new StringBuilder("参数错误：");
        for (BindingResult result : results) {
            if (!result.hasErrors()) {
                continue;
            }
            List<FieldError> errors = result.getFieldErrors();
            for (FieldError error : errors) {
                builder.append(error.getField()).append(error.getDefaultMessage()).append("，");
            }
        }
        return builder.substring(0, builder.length() - 1);
    }
}
