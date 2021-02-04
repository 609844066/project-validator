package com.project.param;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: 20113
 * @Date: 2018/5/30 下午2:44
 * @description
 */
public class UserParam3 {

    @JsonProperty("user_name")
    private String userName;

    @JsonProperty("city_name")
    private String cityName;

    private String age;

    private List<String> payIds;

    private List<String> ids;

    public List<String> getPayIds() {
        return payIds;
    }

    public void setPayIds(List<String> payIds) {
        this.payIds = payIds;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public List<String> getIds() {
        return ids;
    }

    public void setIds(List<String> ids) {
        this.ids = ids;
    }

    @Override
    public String toString() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(ids);
        return "UserParam{" +
                "userName='" + userName + '\'' +
                ", cityName='" + cityName + '\'' +
                ", age='" + age + '\'' +
                ", ids=" + ids +
                '}';
    }
}
