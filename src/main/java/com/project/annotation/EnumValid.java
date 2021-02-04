package com.project.annotation;

import org.springframework.util.ObjectUtils;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * @Author: magic.wang
 * @Date: 2018/11/16 上午11:00
 * @description 校验枚举值有效性
 * 下面例子注解解释：调用validate方法校验Test枚举值是否匹配（是否进行跳过空值校验）,如果不匹配则提示"不符合枚举值"
 * message默认提示"不正确" enumMethod 默认会校验枚举值中的code和当前值匹配
 * eg:@EnumValid(isNotNull=true,message="不符合枚举值",enumClass=Test.class,enumMethod="validate")
 */
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EnumValid.Validator.class)
public @interface EnumValid {

    String message() default "不正确";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    Class<? extends Enum<?>> enumClass();

    String enumMethod() default "default";

    class Validator implements ConstraintValidator<EnumValid, Object> {

        private Class<? extends Enum<?>> enumClass;
        private String enumMethod;

        @Override
        public void initialize(EnumValid EnumValid) {
            enumMethod = EnumValid.enumMethod();
            enumClass = EnumValid.enumClass();
        }

        @Override
        public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
            if (enumClass == null || enumMethod == null) {
                return Boolean.FALSE;
            }

            if (ObjectUtils.isEmpty(value)) {
                return Boolean.TRUE;
            }

            Class<?> valueClass = value.getClass();

            try {
                if ("default".equals(enumMethod)) {
                    for (Enum<?> enums : enumClass.getEnumConstants()) {
                        Method method = enumClass.getMethod("getCode");
                        Object code = method.invoke(enums);
                        if (value.equals(code)) {
                            return true;
                        }
                    }
                    return false;
                }
                Method method = enumClass.getMethod(enumMethod, valueClass);
                if (!Boolean.TYPE.equals(method.getReturnType()) && !Boolean.class.equals(method.getReturnType())) {
                    throw new RuntimeException(String.format("%s method return is not boolean type in the %s class", enumMethod, enumClass));
                }

                if (!Modifier.isStatic(method.getModifiers())) {
                    throw new RuntimeException(String.format("%s method is not static method in the %s class", enumMethod, enumClass));
                }

                Boolean result = (Boolean) method.invoke(null, value);
                return result == null ? false : result;
            } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                throw new RuntimeException(e);
            } catch (NoSuchMethodException | SecurityException e) {
                throw new RuntimeException(String.format("This %s(%s) method does not exist in the %s", enumMethod, valueClass, enumClass), e);
            }
        }

    }
}
