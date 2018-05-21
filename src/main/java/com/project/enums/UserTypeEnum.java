package com.project.enums;

/**
 * @Author: 20113
 * @Date: 2018/5/21 下午1:37
 * @description
 */

public enum UserTypeEnum {

    admin("01","系统管理员"),
    common("02","普通用户"),
    enterprise("03","企业用户");

    private String code;

    private String description;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    UserTypeEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }
}
