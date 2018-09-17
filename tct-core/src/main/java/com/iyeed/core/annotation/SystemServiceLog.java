package com.iyeed.core.annotation;

import java.lang.annotation.*;

@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SystemServiceLog {
    /** 模块 */
    String module() default "";

    /** 业务描述 */
    String businessDesc() default"";
}
