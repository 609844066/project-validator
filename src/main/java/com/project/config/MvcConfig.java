package com.project.config;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.project.annotation.impl.UnderlineToHumpHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

/**
 * @Author: 20113
 * @Date: 2018/5/30 下午3:34
 * @description spring托管配置
 */

@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new UnderlineToHumpHandler());
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(new FastJsonHttpMessageConverter());
        super.configureMessageConverters(converters);
    }

}
