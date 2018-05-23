package com.project.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author: magic.wang
 * @Date: 2018/5/21 上午10:40
 * @description 实体校验注解
 */
@Target({ ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckParams {

    String message() default "";

    /**
     * 是否为空
     */
    boolean notNull() default false;

    /**
     * 是否为数值
     */
    boolean numeric() default false;

    /**
     * 最大长度
     */
    int maxLen() default -1;

    /**
     * 最小长度
     */
    int minLen() default -1;

    /**
     * 最小数值
     */
    long minNum() default -999999;

    /**
     * 校验值是否合法,传递枚举值，如：UserTypeEnum.class，此处默认值为本身只是作为默认标示没有实际作用
     */
    Class enumsValue() default CheckParams.class;

    Class<?>[] groups() default {};

}
