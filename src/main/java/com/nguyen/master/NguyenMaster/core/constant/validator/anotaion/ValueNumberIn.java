package com.nguyen.master.NguyenMaster.core.constant.validator.anotaion;

import com.nguyen.master.NguyenMaster.core.constant.validator.impl.ValueInValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
@Constraint(validatedBy = {ValueInValidator.class})
public @interface ValueNumberIn {
    String message() default "{valid.value.number.in}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    int[] value();
}
