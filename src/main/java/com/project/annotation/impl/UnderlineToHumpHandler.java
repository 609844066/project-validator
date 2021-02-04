package com.project.annotation.impl;

import com.project.annotation.UnderLineConvertHump;
import com.project.utils.ValidateUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author: magic.wang
 * @Date: 2018/5/30 下午2:48
 * @description
 */
public class UnderlineToHumpHandler implements HandlerMethodArgumentResolver{


    private static MappingJackson2HttpMessageConverter converter;

    static {
        converter = new MappingJackson2HttpMessageConverter();
    }

    /**
     *@description 指定处理的注解
     *@author magic.wang
     *@date   2018/5/30
     */
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(UnderLineConvertHump.class);
    }

    public static void main(String[] args) {
        String s = Base64Utils.encodeToString("{\"user_name\":\"wang\",\"city_name\":\"china\",\"ids\":[1,2,3]}".getBytes());
        System.out.println(s);
    }

    @Override
    public Object resolveArgument (
            MethodParameter methodParameter,
            ModelAndViewContainer modelAndViewContainer,
            NativeWebRequest nativeWebRequest,
            WebDataBinderFactory webDataBinderFactory
    )
            throws Exception
    {

        HttpServletRequest servletRequest = nativeWebRequest.getNativeRequest(HttpServletRequest.class);
        ServletServerHttpRequest inputMessage = new ServletServerHttpRequest(servletRequest);
        Object result =null;
        try {
            Type genericParameterType = methodParameter.getGenericParameterType();
            String contentType = servletRequest.getContentType();
            if(MediaType.APPLICATION_JSON_VALUE.equals(contentType)){
                result = converter.read(Class.forName(genericParameterType.getTypeName()), inputMessage);
            }else{
                Object obj = BeanUtils.instantiate(methodParameter.getParameterType());
                BeanWrapper wrapper = PropertyAccessorFactory.forBeanPropertyAccess(obj);
                Map<String, String[]> parameterMap = nativeWebRequest.getParameterMap();
                for(Map.Entry<String, String[]> map : parameterMap.entrySet()){
                    String paramName = map.getKey();
                    String[] paramValue = map.getValue();
                    Field[] declaredFields = obj.getClass().getDeclaredFields();
                    for (Field declaredField : declaredFields) {
                        String underLineParamName = underLineToCamel(paramName);
                        if(declaredField.getName().equals(underLineParamName)){
                            wrapper.setPropertyValue(underLineParamName, paramValue);
                            break;
                        }
                    }
                }
                result = obj;
                ValidateUtil.validate(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     *@description 下划线转驼峰
     *@param  str 待转换下划线属性名
     *@return sb 处理后驼峰属性名
     *@date   2018/5/30
     */
    private String underLineToCamel(String str) {
        Matcher matcher = Pattern.compile("_(\\w)").matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

}
