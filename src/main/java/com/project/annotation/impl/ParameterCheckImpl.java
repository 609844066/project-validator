package com.project.annotation.impl;

import com.project.annotation.CheckParams;
import com.project.validate.Valid;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;

/**
 * @Author:magic.wang
 * @Date: 2018/5/21 上午10:28
 * @description:@CheckParams校验实现类
 */
@Service
public class ParameterCheckImpl {

    /**
     *@description 描述 校验总入口
     *@param  joinPoint 切点
     *@author magic.wang
     *@date   2018/5/21
     */
    public void check(ProceedingJoinPoint joinPoint) throws Exception {
        //这个throw出异常，便于jar包共其他工程引入sdk，可以统一异常处理
        String methodName = joinPoint.getSignature().getName();
        Object target = joinPoint.getTarget();
        Method method = getMethodByClassAndName(target.getClass(), methodName);
        Annotation[][] annotations = method.getParameterAnnotations();
        Object[] args = joinPoint.getArgs();
        if (annotations != null) {
            for (int i = 0; i < annotations.length; i++) {
                Annotation[] anno = annotations[i];
                for (int j = 0; j < anno.length; j++) {
                    if (anno[j].annotationType().equals(Valid.class)) {//判断是否有注解
                        Valid valid = AnnotationUtils.getAnnotation(anno[j], Valid.class);
                        Class<?>[] value = valid.value();
                        String str = checkParam(args[i],value);
                        if (StringUtils.hasText(str)) {
                            throw new RuntimeException(str);
                        }
                    }
                }
            }
        }
    }


    private String checkParam(Object args,Class<?>[] t) throws Exception {
        Field[] field = args.getClass().getDeclaredFields();
        for (int j = 0; j < field.length; j++) {
            // 获取方法参数或者（实体）的field上的注解
            CheckParams check = field[j].getAnnotation(CheckParams.class);
            if (check != null) {
                //开始验证注解字段
                String str = validateFiled(check, field[j], args,t);
                if (StringUtils.hasText(str)) {
                    return str;
                }
            }
        }
        return "";
    }

    /**
     *@description 描述
     *@param  check 校验注解 Field 带校验字典 args 所有参数
     *@return "":校验通过，其他：校验失败并返回失败原因：str
     *@author wangbb/20113
     *@date   2018/5/21
     */
    public String validateFiled(CheckParams check, Field field, Object args,Class<?>[] t) throws Exception {
        field.setAccessible(true);
        int length = 0;
        Object fieldValue = field.get(args);//获取值
        String fieldValueStr = String.valueOf(fieldValue);//获取值str类型

        //是否进行校验分组，如是则匹配字段上的校验分组
        if(check.groups().length==0||!(check.groups()[0].getSimpleName().equals(t[0].getSimpleName()))){
            return "";
        }
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
        if (!check.enumsValue().getName().equals(CheckParams.class.getName())) {
            boolean flag = false;
            Method values = check.enumsValue().getMethod("getCode");
            Object[] enumConstants = check.enumsValue().getEnumConstants();//获取枚举数据
            for (Object enumConstant : enumConstants) {
                Object invokeValue = values.invoke(enumConstant);
                if(fieldValue.equals(invokeValue)){
                    flag = true;
                }
            }
            if (!flag) {
                return field.getName() + "不存在值"+fieldValue;
            }
        }

        return "";
    }

    //获取方法Method
    private Method getMethodByClassAndName(Class c, String methodName) {
        Method[] methods = c.getDeclaredMethods();
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                return method;
            }
        }
        return null;
    }


}
