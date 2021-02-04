package com.project.utils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.groups.Default;
import java.text.MessageFormat;
import java.util.Set;

public class ValidateUtil {

    private static Validator validator = Validation.buildDefaultValidatorFactory()
            .getValidator();

    public static <T> void validate(T obj,Class<?>... clazz) {
        Set<ConstraintViolation<T>> set = validator.validate(obj, clazz);
        String property;
        if (set != null && set.size() > 0) {
            for (ConstraintViolation<T> cv : set) {
                //这里循环获取错误信息，可以自定义格式
                property = cv.getPropertyPath().toString();
                throw new RuntimeException(MessageFormat.format("请求报文[{0}]参数有误", property) + ",原因：" + cv.getMessage());
            }
        }
    }

}
