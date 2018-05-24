package com.project.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @Author: 20113
 * @Date: 2018/5/22 下午3:14
 * @description
 */
@Component("errorMessage")
@PropertySource(value = {"classpath:/config/ErrorMessage.properties"})
public class ErrorMessage {

    @Value("${check.notNull}")
    private String notNull;

    @Value("${check.numeric}")
    private String numeric;

    @Value("${check.maxLen}")
    private String maxLen;

    @Value("${check.minLen}")
    private String minLen;

    @Value("${check.minNum}")
    private String  minNum;

    @Value("${check.enumsValue}")
    private String enumsValue;

    public String getNotNull() {
        return notNull;
    }

    public void setNotNull(String notNull) {
        this.notNull = notNull;
    }

    public String getNumeric() {
        return numeric;
    }

    public void setNumeric(String numeric) {
        this.numeric = numeric;
    }

    public String getMaxLen() {
        return maxLen;
    }

    public void setMaxLen(String maxLen) {
        this.maxLen = maxLen;
    }

    public String getMinLen() {
        return minLen;
    }

    public void setMinLen(String minLen) {
        this.minLen = minLen;
    }

    public String getMinNum() {
        return minNum;
    }

    public void setMinNum(String minNum) {
        this.minNum = minNum;
    }

    public String getEnumsValue() {
        return enumsValue;
    }

    public void setEnumsValue(String enumsValue) {
        this.enumsValue = enumsValue;
    }
}
