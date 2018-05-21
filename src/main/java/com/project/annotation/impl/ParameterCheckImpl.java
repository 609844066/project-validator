package com.project.annotation.impl;

import com.project.annotation.CheckParams;
import com.project.validate.Valid;
import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;

/**
 * @Author: 20113
 * @Date: 2018/5/21 上午10:28
 * @description
 */
@Service
public class ParameterCheckImpl {

    public void check(ProceedingJoinPoint joinPoint) throws Exception {
        String methodName = joinPoint.getSignature().getName();
        Object target = joinPoint.getTarget();
        Method method = getMethodByClassAndName(target.getClass(), methodName);
        Annotation[][] annotations = method.getParameterAnnotations();
        Object[] args = joinPoint.getArgs(); // 方法的参数
        if (annotations != null) {
            for (int i = 0; i < annotations.length; i++) {
                Annotation[] anno = annotations[i];
                for (int j = 0; j < anno.length; j++) {
                    if (anno[j].annotationType().equals(Valid.class)) {
                        String str = checkParam(args[i]);
                        if (StringUtils.hasText(str)) {
                            throw new RuntimeException(str);
                        }
                    }
                }
            }
        }
    }

    private String checkParam(Object args) throws Exception {
        Field[] field = args.getClass().getDeclaredFields();// 获取方法参数（实体）的field
        for (int j = 0; j < field.length; j++) {
            CheckParams check = field[j].getAnnotation(CheckParams.class);// 获取方法参数（实体）的field上的注解Check
            if (check != null) {
                String str = validateFiled(check, field[j], args);
                if (StringUtils.hasText(str)) {
                    return str;
                }
            }
        }
        return "";
    }

    public String validateFiled(CheckParams check, Field field, Object args) throws Exception {
        field.setAccessible(true);
        // 获取field长度
        int length = 0;
        Object fieldValue = field.get(args);
        String fieldValueStr = String.valueOf(fieldValue);
        if (fieldValue != null) {
            length = (fieldValueStr).length();
        }
        if (check.notNull()) {
            if (fieldValue == null) {
                return field.getName() + "不能为空";
            }
        }
        if (check.maxLen() > 0 && (length > check.maxLen())) {
            return field.getName() + "长度不能大于" + check.maxLen();
        }

        if (check.minLen() > 0 && (length < check.minLen())) {
            return field.getName() + "长度不能小于" + check.minLen();
        }

        if (check.numeric() && fieldValue != null) {
            try {
                new BigDecimal(fieldValueStr);
            } catch (Exception e) {
                return field.getName() + "必须为数值型";
            }
        }
        if (check.minNum() != -999999) {
            try {
                long fieldValue2 = Long
                        .parseLong(fieldValueStr);
                if (fieldValue2 < check.minNum()) {
                    return field.getName() + "必须不小于" + check.minNum();
                }
            } catch (Exception e) {
                return field.getName() + "必须为数值型，且不小于" + check.minNum();
            }
        }

        //校验值是否合法
        if (check.enumsValue() != null) {
            boolean flag = false;
            Method values = check.enumsValue().getMethod("values");
            Object[] invoke = (Object[]) values.invoke(null);
            for (Object o : invoke) {
                if (fieldValue.equals(o.toString())) {
                    flag = true;
                }
            }
            if (!flag) {
                return field.getName() + "不存在值" + fieldValue;
            }
        }
        return "";
    }

    public Method getMethodByClassAndName(Class c, String methodName) {
        Method[] methods = c.getDeclaredMethods();
        for (Method method : methods) {
            System.out.println(method.getName());
            if (method.getName().equals(methodName)) {
                return method;
            }
        }
        return null;
    }


}
