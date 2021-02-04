package com.project.param;

import com.project.annotation.CheckParams;
import com.project.annotation.EnumValid;
import com.project.enums.UserTypeEnum;

/**
 * @Author: magic.wang
 * @Date: 2018/5/21 下午1:39
 * @description 用户信息参数实体
 */
public class UserParam2 {

    private String id;

    @CheckParams(notNull = true,groups = UserParam2.class)
    private String userName;

    @CheckParams(notNull = true)
    private String password;

    @CheckParams(enumsValue= UserTypeEnum.class,groups = UserParam2.class)
    @EnumValid(enumClass = UserTypeEnum.class,message = "用户类型参数不合法")
    private String userType;

    private String address;

    @CheckParams(notNull = true,minLen = 1,maxLen = 11)
    private String phone;

    @CheckParams(numeric = true)
    private String age;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
