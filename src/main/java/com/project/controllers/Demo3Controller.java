package com.project.controllers;

import com.project.annotation.UnderLineConvertHump;
import com.project.param.UserParam3;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @Author: 20113
 * @Date: 2018/5/31 下午6:00
 * @description
 */
public class Demo3Controller {

    @GetMapping(value = "/hello")
    public String hello(@UnderLineConvertHump UserParam3 param){
        return param.getUserName();
    }

    @PostMapping(value = "/testList")
    public UserParam3 testList(@UnderLineConvertHump UserParam3 param){
        return param;
    }

    @PostMapping(value = "/testList2")
    public UserParam3 testList2(@RequestBody UserParam3 param){

        return param;
    }
}
