package com.hujing.springsecuritytest.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author hj
 * @create 2019-05-01 10:47
 */
@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MyConstrainValid.class)
public @interface MyConstrain {
    String message();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
