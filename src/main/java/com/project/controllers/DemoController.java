package com.project.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.project.param.UserParam;
import com.project.validate.Valid;

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

}
