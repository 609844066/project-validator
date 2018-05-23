package com.project.validate;

import java.lang.annotation.*;

/**
 * @Author: 20113
 * @Date: 2018/5/21 下午1:35
 * @description
 */
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Valid {//用户扫描注解进行校验
    Class<?>[] value() default {};//分组校验
}
