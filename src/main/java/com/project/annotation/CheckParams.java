package com.project.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author: 20113
 * @Date: 2018/5/21 上午10:40
 * @description
 */
@Target({ ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckParams {

    /**
     * 是否为空
     */
    public boolean notNull() default false;

    /**
     * 是否为空
     */
    public boolean notBlank() default false;

    /**
     * 是否为数值
     */
    public boolean numeric() default false;

    /**
     * 最大长度
     */
    public int maxLen() default -1;

    /**
     * 最小长度
     */
    public int minLen() default -1;

    /**
     * 最小数值
     */
    public long minNum() default -999999;

    /**
     * 校验值是否合法,传递枚举值，如：UserTypeEnum.class
     */
    public Class enumsValue();

}
