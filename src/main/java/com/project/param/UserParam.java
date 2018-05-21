package com.project.param;

import com.project.annotation.CheckParams;
import com.project.enums.UserTypeEnum;

/**
 * @Author: 20113
 * @Date: 2018/5/21 下午1:39
 * @description
 */
public class UserParam {

    private String id;

    private String userName;

    private String password;

    @CheckParams(enumsValue= UserTypeEnum.class)
    private String userType;

    private String address;

    private String phone;

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
