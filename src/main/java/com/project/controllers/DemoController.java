package com.project.controllers;

import com.project.param.UserParam;
import com.project.validate.Valid;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: 20113
 * @Date: 2018/5/21 下午2:23
 * @description
 */
@RestController
@RequestMapping(value="/demo", produces="application/json; charset=UTF-8")
public class DemoController{


    @RequestMapping(value="/hello",method = RequestMethod.POST)
    public String hello(@Valid UserParam param){
        return "{hello:Tom}";
    }

    /**
     *@description
     *@param userName:123 userType:0156
     *@return password不能为空
     *@author wangbb/20113
     *@date   2018/5/22
     */
    @RequestMapping(value="/test",method = RequestMethod.POST)
    public String test(@Valid(UserParam.class) UserParam param){
        return "{hello:Tom}";
    }

}
