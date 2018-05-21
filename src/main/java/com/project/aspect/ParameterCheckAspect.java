package com.project.aspect;

import com.project.annotation.impl.ParameterCheckImpl;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author: 20113
 * @Date: 2018/5/21 上午10:26
 * @description
 */
@Aspect
@Component
public class ParameterCheckAspect {

    @Autowired
    private ParameterCheckImpl parameterCheck;

    @Pointcut("within(com.project.controllers.*)")
    public void check(){}


    @Around(value = "check()",argNames = "Valid")
    public Object checkValid(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        parameterCheck.check(proceedingJoinPoint);
        Object proceed = proceedingJoinPoint.proceed();
        return proceed;
    }

}
